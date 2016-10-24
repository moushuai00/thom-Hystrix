package com.thom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by thom on 16-9-25.
 */
@Component
@Aspect
public class CommunErrorAop implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(CommunErrorAop.class);


    @Around("@annotation(com.thom.annotion.IgnoreError)")
    public Object ignoreError(ProceedingJoinPoint joinPoint) {

        logger.info(joinPoint.getClass() + "xxxxxxxxxxxxxxx");

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        return null;

    }


    @Before("@annotation(com.thom.annotion.IgnoreError)")
    public Object ignoreError(JoinPoint joinPoint) {
        logger.info("beforexxxxxxxxxxxxxxx");

        return null;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
       logger.info("xxxxxxxxafterPropertiesSetxxxxxxx");
    }
}
