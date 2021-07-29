package com.weblab.booking.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("mapper")
public class HandlerMapping implements ApplicationContextAware {
	private ApplicationContext context = null;
	
	public Controller getHandler(String path) {
		Controller controller = null;
		
		if (context != null) {
			try {
				controller = (Controller)context.getBean(path);
			} catch(BeansException ex) {
				ex.printStackTrace();
			}
		}
		
		return controller;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;		
	}
}
