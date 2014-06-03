package com.ab.hibernate.model;

public final class UserContext 
{
	private static ThreadLocal<String> username;
	
	public static String get()
	{
		return (username==null?"":username.get());
	}
	
	public static void set(String u)
	{
		username=new ThreadLocal<String>();
		username.set(u);
	}
	
	public static void unset()
	{
		username=null;
	}
	
	
}
