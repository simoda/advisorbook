package com.ab.dataservices.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AdvisorLoaderMain {
	
	private ApplicationContext appCtxt;
	
	public AdvisorLoaderMain()
	{
		appCtxt= new ClassPathXmlApplicationContext("dataservices-context.xml");
	}
	
	private void doLoad()
	{
		IAdvisorLoader loader=(IAdvisorLoader)appCtxt.getBean("advisorLoader");
		loader.loadAdvisors();
	}
	
	public static void main(String args[])
	{
		
		AdvisorLoaderMain main=new AdvisorLoaderMain();
		main.doLoad();
		
	}

}
