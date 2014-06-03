package com.ab.hibernate.server.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ab.hibernate.model.UserContext;
import com.ab.hibernate.server.AuditingTestDAO;

@ContextConfiguration(locations = { "classpath:hibernateAuditableListener-spring-context.xml" })
public class HibernateAuditableListenerTest extends AbstractServiceTest {

    @Autowired
    private AuditingTestDAO auditingTestDAO;

    private static final String TEST_USER = "TESTER";

    @Test
    public void testAudit() {
        AuditingTestEntity testEntity = new AuditingTestEntity();

        testEntity.setKey("TEST");

        auditingTestDAO.add(testEntity);

        AuditingTestEntity saveEntity = auditingTestDAO.get(testEntity.getId());

        Assert.assertTrue("Created by not set correctly!", saveEntity != null
                && HibernateAuditableListener.DEFAULT_USER.equals(saveEntity.getCreatedBy()));

        // TODO Assert.assertTrue("Created date not set correctly!",
                //TODO DateUtils.cleanDateOfTime(new Date()).equals(DateUtils.cleanDateOfTime(saveEntity.getCreatedDate())));

        Assert.assertTrue("Updated details should be null!",
                null == saveEntity.getUpdatedBy() && null == saveEntity.getUpdatedDate());

        Date created = saveEntity.getCreatedDate();

        UserContext.set(TEST_USER);

        saveEntity.setKey("UPDATED");

        auditingTestDAO.save(saveEntity);

        saveEntity = auditingTestDAO.get(testEntity.getId());

        Assert.assertTrue("Created by should not be updated!", saveEntity != null
                && HibernateAuditableListener.DEFAULT_USER.equals(saveEntity.getCreatedBy()));

        Assert.assertEquals("Created Date should not be updated!", created, saveEntity.getCreatedDate());

        Assert.assertTrue("Updated by not set correctly!", TEST_USER.equals(saveEntity.getUpdatedBy()));

        Assert.assertTrue("Updated Date not set correctly!", created.before(saveEntity.getUpdatedDate()));

        Date updated = saveEntity.getUpdatedDate();

        UserContext.unset();

        saveEntity.setKey("UPDATED AGAIN");

        auditingTestDAO.save(saveEntity);

        saveEntity = auditingTestDAO.get(saveEntity.getId());

        Assert.assertEquals("Created Date should not be updated!", created, saveEntity.getCreatedDate());

        Assert.assertTrue("Updated by not set correctly!",
                HibernateAuditableListener.DEFAULT_USER.equals(saveEntity.getUpdatedBy()));

        Assert.assertTrue("Updated Date not set correctly!", updated.before(saveEntity.getUpdatedDate()));

    }
}
