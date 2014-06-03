package com.anz.fit.hibernate.server.impl;

import com.anz.fit.hibernate.server.AuditingTestDAO;

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
