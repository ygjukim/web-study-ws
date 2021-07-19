package com.weblab.springex.di;

import java.io.IOException;
import java.util.Properties;

public class TVBeanFactory {
	private final String resource = "config.properties";
	private Properties config = new Properties();	
	
	public TVBeanFactory() throws IOException {
		config.load(this.getClass().getResourceAsStream(resource));
	}
	
	public TV getTV() {
		TV tv = null;
		Speaker speaker = null;
		
		String brand = config.getProperty("brand");
		String spkBrand = config.getProperty("speaker");
		
		if (brand.equals("samsung")) {
			tv = new SamsungTV();
		}
		else if (brand.equals("lg")) {
			tv = new LgTV();
		}
		
		if (spkBrand.equals("apple")) {
			speaker = new AppleSpeaker();
		}
		else if (spkBrand.equals("sony")) {
			speaker = new SonySpeaker();
		}
		tv.setSpeaker(speaker);
			
		return tv;
	}
	
}