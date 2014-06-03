package com.anz.fit.hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 
 * @author simonsd
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AuditedBaseEntity extends VersionedBaseEntity implements IAuditedBaseEntity {

    @Column(name = "CREATED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Column(name = "CREATED_BY", nullable = false, length = 30)
    protected String createdBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedDate;

    @Column(name = "UPDATED_BY", nullable = true, length = 30)
    protected String updatedBy;

    public AuditedBaseEntity() {
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(" createdBy [").append(createdBy).append("] ").append(", createdDate [").append(createdDate)
                .append("], updatedBy [").append(updatedBy).append("], updatedDate [").append(updatedDate).append("]");

        return str.toString();

    }

}
