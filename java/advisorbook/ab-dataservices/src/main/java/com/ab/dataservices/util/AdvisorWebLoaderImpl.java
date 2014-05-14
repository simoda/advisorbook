package com.ab.dataservices.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.Source;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ad.dataservices.entity.Professional;

/**
 * This class is the entry point for the loader that scrapes the website for
 * advisor information and then saves it to the database
 * 
 * @author danski
 * 
 */
public class AdvisorWebLoaderImpl implements IAdvisorLoader {

	private Logger log = Logger.getLogger(this.getClass());

	private String baseUrl;

	@Override
	public void loadAdvisors() {
		List<Professional> pList=advisorSearch(
				"/findanadviser/search/?command=getresults&SearchLocation=New%20South%20Wales&SearchLocationType=State&LocationRadius=1000",
				1);
		log.debug("Advisor Search Complete found ["+pList.size()+"] advisors"); 
	}

	/**
	 * Recursive - On the first page it gets all the search urls from the page
	 * number ribbon and calls recursively
	 * 
	 * @param searchStr
	 * @param pageNo
	 */
	private List<Professional> advisorSearch(String searchStr, int pageNo) {
		String fullUrl = baseUrl + searchStr;

		log.debug("Advisor Search Started with url [" + fullUrl + "] page ["+pageNo+"]");

		try {
			MicrosoftConditionalCommentTagTypes.register();
			MasonTagTypes.register();
			Source source = new Source(new URL(fullUrl));
			List<String> advisorUrlsList = null;
			if (1 == pageNo) {		
				Element element = source.getElementById("alphabetRibbonList");
				List<Element> anchors = element.getAllElements("a");
				advisorUrlsList = new ArrayList<String>();

				for (Element e : anchors) {
					// Ignore the first page as we are on it
					if ("1".equals(e.getContent().toString().trim())) {
						continue;
					}
					advisorUrlsList.add(e.getAttributeValue("href"));
				}
			}

			Element element = source.getElementById("directoryResults");
			List<Professional> pList = new ArrayList<Professional>();
			for (Element e : element.getAllElements("tr")) {
				Professional p = processAdvisorRowElement(e);
				if (null != p) {
					pList.add(p);
				}

			}

			// TODO
			if (!CollectionUtils.isEmpty(advisorUrlsList)) {
				for (String searchUrl : advisorUrlsList) {
					pList.addAll(advisorSearch(searchUrl, ++pageNo));
				}
			}
			log.debug("Advisor Search Complete with url [" + fullUrl + "] found ["+pList.size()+"] advisors");
			return pList;
			
		} catch (Throwable t) {
			String errMessage = "Failed advisorSearch for Url [" + fullUrl
					+ "]";
			log.error(errMessage, t);
			throw new RuntimeException(errMessage, t);
		}

		

	}

	/**
	 * First element is name Second Element is Company
	 * 
	 * @param element
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private Professional processAdvisorRowElement(Element element) throws MalformedURLException, IOException {
		Professional p = null;

		int elementCount = 0;
		for (Element e : element.getAllElements("td")) {
			elementCount++;
			if (elementCount == 1) {
				
				String fullName = e.getContent().toString().trim();
				if (StringUtils.isBlank(fullName)) {
					break;
				}
				p=new Professional();
				String[] names = fullName.split(" ");

				p.setFirstName(names[0]);
				if (names.length > 1) {
					String lastName = "";
					for (int loop = 1; loop < names.length; loop++) {
						lastName += names[loop];
					}
					p.setLastName(lastName);
				}
			} else if (elementCount == 2) {
				p.setCompanyName(e.getContent().toString().trim());
			} else if (elementCount == 3) {
				List<Element> anchors=e.getAllElements("a");
				String detailsHref = null;
				if(null!=anchors && anchors.size()>0)
				{
					detailsHref=anchors.get(0).getAttributeValue("href");
				}
				
				if(!StringUtils.isBlank(detailsHref))
				{
					populateAdvisorDetails(p,detailsHref);	
				}
				
			}
		}
		
		if(null == p)
		{
			log.warn("No advisor name found in "
				+ element.getContent().toString());
		}
		
		return p;
	}

	private void populateAdvisorDetails(Professional p, String detailsHref) throws MalformedURLException, IOException {
		Source source = new Source(new URL(detailsHref));
		// TODO
		
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
