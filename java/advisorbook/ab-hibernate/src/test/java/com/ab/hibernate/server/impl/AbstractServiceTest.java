package com.ab.hibernate.server.impl;

import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-spring-hibernate-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class AbstractServiceTest {

    @Autowired
    protected EntityManagerFactory emf;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init() {
        //TODO
    }

}
