package com.weblab.springaop;

import org.springframework.aop.ThrowsAdvice;

public class LogAfterThrowingAdvice implements ThrowsAdvice {

	public void afterThrowing(IllegalArgumentException ex) {
		
		System.out.printf("처리 중에 오류가 발생하였습니다: %s\n", ex.getMessage());
		
	}
}
