package com.anz.fit.hibernate.server.impl;

import java.util.Date;

import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.tuple.StandardProperty;

import com.anz.fit.core.utils.UserContext;
import com.anz.fit.hibernate.model.HibernatePostLoadAction;
import com.anz.fit.hibernate.model.IAuditedBaseEntity;

/**
 * This class intercepts hibernate save and update events then checks if it is a VersionedBaseEntity
 * if so it adds the date and user auditing
 * 
 * @author simonsd
 * 
 */
public final class HibernateAuditableListener implements PostLoadEventListener, PreInsertEventListener,
        PreUpdateEventListener {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_USER = "UNKNOWN";

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        Object entity = event.getEntity();

        if (entity instanceof IAuditedBaseEntity) {

            StandardProperty[] properties = event.getPersister().getEntityMetamodel().getProperties();
            Object[] state = event.getState();

            //Redo inserts a "nice" feature of hibernate is that on first insert it shares its state with update
            //then tries to update the already inserted values with nulls
            if (((IAuditedBaseEntity) entity).getCreatedBy() == null
                    || ((IAuditedBaseEntity) entity).getCreatedDate() == null) {
                setCreatedAttributes(entity, properties, state);
        
            }
            else {
                // Always set the update date
                setUpdatedAttributes(entity, properties, state);

            }

        }

        return false;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof IAuditedBaseEntity
                && (((IAuditedBaseEntity) entity).getCreatedBy() == null || ((IAuditedBaseEntity) entity)
                        .getCreatedDate() == null)) {

            StandardProperty[] properties = event.getPersister().getEntityMetamodel().getProperties();
            Object[] state = event.getState();
            
            setCreatedAttributes(entity, properties, state);
        }

        return false;
    }

    private void setCreatedAttributes(Object entity, StandardProperty[] properties, Object[] state) {
        Date createdDate=new Date();
        String createdUser=getContextUser();
        setValue(state, properties, "createdDate", createdDate);
        setValue(state, properties, "createdBy", createdUser);
        ((IAuditedBaseEntity)entity).setCreatedBy(createdUser);
        ((IAuditedBaseEntity)entity).setCreatedDate(createdDate);
    }
    
    private void setUpdatedAttributes(Object entity, StandardProperty[] properties, Object[] state) {
        Date updateDate=new Date();
        String updateUser=getContextUser();
        setValue(state, properties, "updatedDate", updateDate);
        setValue(state, properties, "updatedBy", updateUser);
        ((IAuditedBaseEntity)entity).setUpdatedBy(updateUser);
        ((IAuditedBaseEntity)entity).setUpdatedDate(updateDate);
    }

    private void setValue(Object[] state, StandardProperty[] properties, String fieldName, Object defaultValue) {
        for (int i = 0; i < properties.length; i++) {
            if (properties[i].getName().equals(fieldName)) {

                state[i] = defaultValue;

                break;
            }
        }
    }

    /**
     * We need to convert the returned datatype to a java Date as the SybaseDate type is not
     * serialized by GWT RPC.
     */
    @Override
    public void onPostLoad(PostLoadEvent event) {
        Object entity = event.getEntity();

        if (entity instanceof IAuditedBaseEntity
                && (((IAuditedBaseEntity) entity).getCreatedBy() != null || ((IAuditedBaseEntity) entity)
                        .getCreatedDate() != null)) {
            IAuditedBaseEntity baseEntity = (IAuditedBaseEntity) entity;

            if (null != baseEntity.getCreatedDate()) {
                baseEntity.setCreatedDate(new Date(baseEntity.getCreatedDate().getTime()));
            }
            if (null != baseEntity.getUpdatedDate()) {
                baseEntity.setUpdatedDate(new Date(baseEntity.getUpdatedDate().getTime()));
            }

        }

        if (entity instanceof HibernatePostLoadAction) {
            ((HibernatePostLoadAction) entity).postLoadAction();
        }

    }

    private String getContextUser() {
        return null == UserContext.get() || UserContext.get().trim().length() == 0 ? DEFAULT_USER : UserContext.get();
    }

}
