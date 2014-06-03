package com.ab.hibernate.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 
 * @author simonsd
 * 
 */

@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class VersionedBaseEntity implements Serializable {

    public static final long serialVersionUID = 1L;

    @Version
    public Long version;

    public VersionedBaseEntity() {
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public abstract String getTableName();

    public abstract Object getId();

    public boolean isPersistent() {

        Long id = (Long) getId(); // Let it throw a class cast if it wants 

        return (id != null && id > -1);
    }

    @Override
    public String toString() {
        return (new StringBuilder(" version[").append(null == version ? "0" : version.toString()).append("]"))
                .toString();
    }

}
