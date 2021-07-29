package com.weblab.springaop.aspectj;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcuts {

	@Pointcut("execution(* com.weblab.springaop..*Calculator.factorial(long))")
	public void factorialPointcut() {
	}
	
}
