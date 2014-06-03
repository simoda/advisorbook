package com.ab.hibernate.server.impl;

import com.ab.hibernate.server.AuditingTestDAO;
import com.ab.hibernate.server.impl.GenericDaoImpl;

/**
 * 
 * @author simonsd
 * 
 */
public class AuditingTestDAOImpl extends GenericDaoImpl<AuditingTestEntity, Long> implements AuditingTestDAO {
    public AuditingTestDAOImpl() {
        super(AuditingTestEntity.class);
    }
}
