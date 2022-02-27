package com.m2s.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
private static final Logger LOGGER= LoggerFactory.getLogger(LoggingAspect.class);
	
	@Before("execution(public * com.m2s.controller..*(..)) || execution(public * com.m2s.service.impl..*(..))")
	private void logBefore(JoinPoint jp) {
		String packageName = jp.getSignature().getDeclaringTypeName();
	    String methodName = jp.getSignature().getName();
	    LOGGER.info("Entering method [" + packageName + "." + methodName +  "]");
	}
	
	@AfterReturning("execution(public * com.m2s.controller..*(..)) || execution(public * com.m2s.service.impl..*(..))")
	private void logAfter(JoinPoint jp) {
		String packageName = jp.getSignature().getDeclaringTypeName();
	    String methodName = jp.getSignature().getName();
		LOGGER.info("Exiting method [" + packageName + "." + methodName + "]; ");
		
	}
	
	@AfterThrowing("execution(public * com.m2s.controller..*(..)) || execution(public * com.m2s.service.impl..*(..))")
	private void logException(JoinPoint jp) {
		String packageName = jp.getSignature().getDeclaringTypeName();
	    String methodName = jp.getSignature().getName();
	    LOGGER.info("Exeception occur in method [" + packageName + "." + methodName + "]; ");
	}
	
}

