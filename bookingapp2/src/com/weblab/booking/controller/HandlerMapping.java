package com.weblab.booking.controller;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
	private Map<String, Controller> handlerMapper = null;
	
	public HandlerMapping() {
		handlerMapper = new HashMap<String, Controller>();
		
		handlerMapper.put("/index", new IndexController());
		handlerMapper.put("/register", new RegisterController());
		handlerMapper.put("/confirm", new ConfirmController());
		handlerMapper.put("/delete", new DeleteController());
		handlerMapper.put("/update", new UpdateController());
		handlerMapper.put("/update_proc", new UpdateProcController());
	}
	
	public Controller getHandler(String path) {
		return handlerMapper.get(path);
	}
}
