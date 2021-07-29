package com.weblab.springaop;

import org.springframework.stereotype.Component;

@Component("calc2")
public class RecuCalculator implements Calculator {

	public long factorial(long num) {
		if (num == 1) 
			return num;
		else 
			return num * factorial(num-1);
	}

}
