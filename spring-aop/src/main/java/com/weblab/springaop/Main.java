package com.weblab.springaop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {

	public static void main(String[] args) {
/*		
		final Calculator calc1 = new ImpeCalculator();
		final Calculator calc2 = new RecuCalculator();
		
		Calculator calc1Proxy = (Calculator) Proxy.newProxyInstance(
				ImpeCalculator.class.getClassLoader(), 
				new Class[] { Calculator.class }, 
				new InvocationHandler() {

					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						long start = System.nanoTime();
												
						Object result = method.invoke(calc1, args);
						
						long end = System.nanoTime();
						System.out.println(calc1.getClass().getName() + "." + method.getName() 
							+ " 실행 시간 = " + (end - start) + " nsec");
						
						return result;
					}
					
				}
		);
				
		Calculator calc2Proxy = (Calculator) Proxy.newProxyInstance(
				RecuCalculator.class.getClassLoader(), 
				new Class[] { Calculator.class }, 
				new InvocationHandler() {

					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						long start = System.nanoTime();
												
						Object result = method.invoke(calc2, args);
						
						long end = System.nanoTime();
						System.out.println(calc2.getClass().getName() + "." + method.getName() 
							+ " 실행 시간 = " + (end - start) + " nsec");
						
						return result;
					}
					
				}
		);
*/
		
		AbstractApplicationContext context = 
//			new ClassPathXmlApplicationContext("classpath:com/weblab/springaop/appContext.xml");
			new AnnotationConfigApplicationContext(AopAppConfig.class);
		
		Calculator calc = (Calculator)context.getBean("calc1");
		Calculator calcr = (Calculator)context.getBean("calc2");
		
		System.out.println("Factorial of 10 by ImpeCalculator = " + calc.factorial(10));
		System.out.println("Factorial of 10 by ImpeCalculator = " + calc.factorial(10));
		System.out.println(calc.getClass().getCanonicalName());
		System.out.println("Factorial of 10 by RecuCalculator = " + calcr.factorial(10));
		System.out.println(calcr.getClass().getCanonicalName());

	}

}
