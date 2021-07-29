package com.weblab.springaop.aspectj;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class CacheAspect {
	private Map<Long, Object> cache = new HashMap<>();

	@Around("CommonPointcuts.factorialPointcut()")
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		Long num = (Long)pjp.getArgs()[0];
		if (cache.containsKey(num)) {
			System.out.printf("CacheAspect: cache에서 값을 구함(%d)\n", num);
			return cache.get(num);
		}
		
		Object result = pjp.proceed();
		
		cache.put(num, result);
		System.out.printf("CacheAspect: cache에 (%d, %s) 추가\n", num, result);
		return result;
	}
}
