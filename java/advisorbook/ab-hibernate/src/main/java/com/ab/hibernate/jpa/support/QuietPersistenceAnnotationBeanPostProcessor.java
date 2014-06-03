package com.ab.hibernate.jpa.support;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;

/**
 * This is required to avoid the ERRORs like this appearing in our logs when we shutdown tomcat.
 * Seems no otherway to stop the errors.
 * 
 * 2013-11-18 17:22:53,301 ERROR [Thread-2] (DefaultSingletonBeanRegistry.java:490) - Destroy method
 * on bean with name 'userGridColumnSpecFactory' threw an exception java.lang.ClassCastException:
 * java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
 * 
 * @author murrays8
 * 
 */
public class QuietPersistenceAnnotationBeanPostProcessor extends PersistenceAnnotationBeanPostProcessor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 7746607651986941123L;

    private static final AtomicInteger instanceCount = new AtomicInteger(0);
    private final int myInstanceCount;

    public QuietPersistenceAnnotationBeanPostProcessor() {
        super();
        myInstanceCount = instanceCount.getAndAdd(1);
        log.info(myInstanceCount + " Created");
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        try {
            log.trace("{} postProcessBeforeDestruction {}", myInstanceCount, beanName);
            super.postProcessBeforeDestruction(bean, beanName);
        }
        catch (Exception e) {
            log.info("{} exception '{}' in postProcessBeforeDestruction for {}",
                    new Object[] { myInstanceCount, e.getMessage(), beanName });
        }
    }

}
