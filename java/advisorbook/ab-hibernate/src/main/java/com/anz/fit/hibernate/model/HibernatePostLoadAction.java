package com.anz.fit.hibernate.model;

/**
 * Implement this in your enitity if you want to perform some specific action during PostLoad
 * 
 * @author simonsd
 * 
 */
public interface HibernatePostLoadAction {
    public void postLoadAction();
}
