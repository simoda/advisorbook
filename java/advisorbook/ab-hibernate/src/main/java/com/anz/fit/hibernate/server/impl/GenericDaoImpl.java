package com.anz.fit.hibernate.server.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Generic DAO for all fit datasources
 * 
 * @author simonsd
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public class GenericDaoImpl<T, PK extends Serializable> extends AbstractGenericDaoImpl<T, PK> {

    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */
    @PersistenceContext(unitName = "fit")
    protected EntityManager entityManager;

    /**
     * Constructor that takes in a class to see which type of entity to persist. Use this
     * constructor when subclassing or using dependency injection.
     * 
     * @param persistentClass
     *            the class type you'd like to persist
     */
    public GenericDaoImpl(final Class<T> persistentClass) {
        super(persistentClass);
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist. Use this
     * constructor when subclassing or using dependency injection.
     * 
     * @param persistentClass
     *            the class type you'd like to persist
     * @param entityManager
     *            the configured EntityManager for JPA implementation.
     */
    public GenericDaoImpl(final Class<T> persistentClass, EntityManager entityManager) {
        this(persistentClass);
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
