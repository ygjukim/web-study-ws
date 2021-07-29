package com.weblab.booking.advice;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
	@Pointcut("execution(* com.weblab.booking.service.*Impl.*(..))")
	public void servicePointcut() {}
	
	@Around("servicePointcut()")
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		Logger logger = LoggerFactory.getLogger(LogAspect.class);
		
		long start = System.currentTimeMillis();
		
		Object result = pjp.proceed();
		
		long end = System.currentTimeMillis();
		String log = String.format("%s.%s(%s) 실행시간 : %d msec", 
				pjp.getTarget().getClass().getSimpleName(),
				pjp.getSignature().getName(),
				Arrays.toString(pjp.getArgs()),
				(end - start));
		logger.info(log);
		
		return result;
	}

}
