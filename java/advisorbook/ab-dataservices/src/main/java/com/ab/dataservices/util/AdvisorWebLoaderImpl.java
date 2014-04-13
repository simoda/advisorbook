package com.ab.dataservices.util;

import java.net.URL;

import org.apache.log4j.Logger;

import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.Source;

/**
 * This class is the entry point for the loader that scrapes the website for advisor information and then saves it to the database
 * 
 * @author danski
 *
 */
public class AdvisorWebLoaderImpl implements IAdvisorLoader {

	private Logger log = Logger.getLogger(this.getClass());
	
	private String scrapeUrl; 
	
	@Override
	public void loadAdvisors() 
	{
		
		advisorSearch("?command=getresults&SearchLocation=New%20South%20Wales&SearchLocationType=State&LocationRadius=1000");
		
	}
	
	private void advisorSearch(String searchStr)
	{
		String fullUrl=scrapeUrl+"?command=getresults&SearchLocation=New%20South%20Wales&SearchLocationType=State&LocationRadius=1000";
		
		log.debug("Advisor Search Started with url ["+fullUrl+"]");
		
		try
		{
			MicrosoftConditionalCommentTagTypes.register();
			MasonTagTypes.register();
			Source source=new Source(new URL(fullUrl));
			
		} catch (Throwable t)
		{
			String errMessage="Failed advisorSearch for Url ["+fullUrl+"]";
			log.error(errMessage,t);
			throw new RuntimeException(errMessage,t);
		}
		
		log.debug("Advisor Search Complete with url ["+fullUrl+"]");
		
	}

	public void setScrapeUrl(String scrapeUrl) {
		this.scrapeUrl = scrapeUrl;
	}
		

}
