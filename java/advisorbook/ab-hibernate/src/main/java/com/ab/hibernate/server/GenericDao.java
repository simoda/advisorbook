package com.ab.hibernate.server;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author simonsd
 * 
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */

public interface GenericDao<T, PK extends Serializable> {

    /**
     * Generic method to save an object - handles both update and insert.
     * 
     * @param object
     *            the object to save
     * @return the persisted object
     */
    public T save(T object);

    /**
     * Generic method to insert an object.
     * 
     * @param object
     *            the object to insert
     */
    public void add(T object);

    /**
     * Generic method to get an object based on class and identifier.
     * 
     * @param id
     *            the identifier (primary key) of the object to get
     * @return a populated object
     */
    public T get(PK id);

    /**
     * Generic method to delete an object.
     * 
     * @param long1
     *            the object to delete
     */
    public void remove(PK id);

    /**
     * Generic method for select *
     * 
     * @return
     */
    public List<T> getAll();

    void remove(T object);
}