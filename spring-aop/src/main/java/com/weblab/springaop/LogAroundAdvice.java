package com.weblab.springaop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogAroundAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.nanoTime();

		Object result = invocation.proceed();

		long end = System.nanoTime();
		System.out.println(invocation.getMethod().getName() 
			+ " 실행 시간 = " + (end - start) + " nsec");

		return result;
	}

}
