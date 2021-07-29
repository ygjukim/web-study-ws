package com.weblab.springaop;

import org.springframework.stereotype.Component;

@Component("calc1")
public class ImpeCalculator implements Calculator {

	public long factorial(long num) {
		
		if (num > 10) {
			throw new IllegalArgumentException("매개변수값이 10이 넘었습니다.");
		}
		
		long result = 1;		
		for (int i=1;i<=num; i++) {
			result *= i;
		}
		return result;
	}

}
