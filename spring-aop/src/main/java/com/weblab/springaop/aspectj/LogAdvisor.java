package com.weblab.springaop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class LogAdvisor {
	
	@Pointcut("execution(* com.weblab.springaop..*Calculator.*(..))")
	private void allPointcut() {
	}
	
	@Before("allPointcut()")
	public void before(JoinPoint jp) {
		System.out.printf("%s.%s(%s) 실행하기 전 전처리...\n", 
			jp.getTarget().getClass().getCanonicalName(), 
			jp.getSignature().getName(), jp.getArgs()[0]);
	}
	
	@Around("CommonPointcuts.factorialPointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		
		long start = System.nanoTime();

		result = pjp.proceed();

		long end = System.nanoTime();
		System.out.println(pjp.getSignature().getName() 
			+ " 실행 시간 = " + (end - start) + " nsec");
		
		return result;
	}
	
	@AfterReturning(pointcut="allPointcut()", returning="returning")
	public void afterReturning(JoinPoint jp, Object returning) {
		
		System.out.printf("반환값 = %s\n",returning);
		
	}
	
	@AfterThrowing(pointcut="allPointcut()", throwing="throwing")
	public void afterThrowing(JoinPoint jp, Exception throwing) {
		
		System.out.printf("예외 발생: ", throwing.getMessage());
		
	}
	
	@After("allPointcut()")
	public void after(JoinPoint jp) {

		System.out.printf("실행 후 후처리(finally clean-up)...\n");
		
	}
	
}
