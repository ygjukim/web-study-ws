package com.weblab.springex.di;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

public class TVUser {

	public static void main(String[] args) throws IOException {
//		TV tv = new SamsungTV();
//		TV tv = new LgTV();
		
//		TV tv = (new TVBeanFactory()).getTV();
		
		ApplicationContext container =
//			new ClassPathXmlApplicationContext("com/weblab/springex/di/AppContext.xml");
			new AnnotationConfigApplicationContext(AppConfig.class);
		
		Environment env = container.getBean(Environment.class);
		
		TV tv = container.getBean(env.getProperty("brand"), TV.class);
		System.out.println(tv);
		
		tv.powerOn();
		tv.volumnUp();
		tv.volumnDown();
		tv.powerOff();
	}

}
