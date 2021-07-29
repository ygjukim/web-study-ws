package com.weblab.springaop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class LogBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		
		System.out.printf("%s.%s(%s) 실행 전 공통 처리...\n", 
				target.getClass().getCanonicalName(),
				method.getName(), args[0].toString());

	}

}
