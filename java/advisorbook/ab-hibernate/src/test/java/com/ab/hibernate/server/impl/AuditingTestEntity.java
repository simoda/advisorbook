package com.ab.hibernate.server.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ab.hibernate.model.AuditedBaseEntity;

/**
 * Test Entity used to test the HibernateAuditableListener stuff
 * 
 * @author simonsd
 * 
 */
@Entity
@Table(name = AuditingTestEntity.TABLE_NAME)
public class AuditingTestEntity extends AuditedBaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "TEST_AUDIT_TABLE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "KEY")
    private String key;

    public AuditingTestEntity() {
        super();
    }

    @Override
    public String getTableName() {

        return TABLE_NAME;
    }

    @Override
    public Long getId() {

        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
