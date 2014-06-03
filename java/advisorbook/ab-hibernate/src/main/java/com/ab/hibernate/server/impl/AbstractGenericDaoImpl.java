package com.ab.hibernate.server.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ab.hibernate.server.GenericDao;

/**
 * This class serves as the Base class for all other DAOs and contains common operations needed by
 * the other Daos.an inheriting class must provide the EntityManager vi getEntityManager
 * 
 * @author simonsd
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public abstract class AbstractGenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected String selectAllQuery = "";
    

    protected Class<T> persistentClass;

    /**
     * Constructor that takes in a class to see which type of entity to persist. Use this
     * constructor when subclassing or using dependency injection.
     * 
     * @param persistentClass
     *            the class type you'd like to persist
     */
    public AbstractGenericDaoImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        selectAllQuery = "FROM " + this.persistentClass.getName();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T save(T object) {
        return getEntityManager().merge(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(T object) {
       getEntityManager().persist(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T get(PK id) {
        T entity =getEntityManager().find(this.persistentClass, id);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(PK id) {
        remove(this.get(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(T object) {
       getEntityManager().remove(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Query query =getEntityManager().createQuery(selectAllQuery);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public T update(T object, PK id) {
        T obj =getEntityManager().merge(object);
        return obj;
    }

   
    
    public abstract EntityManager getEntityManager();
    
}
