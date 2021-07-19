package com.weblab.springex.legacy;

public class TVUser {

	public static void main(String[] args) {
//		SamsungTV tv = new SamsungTV();
//		
//		tv.powerOn();
//		tv.volumnUp();
//		tv.volumnDown();
//		tv.powerOff();
		
		LgTV tv = new LgTV();
		
		tv.turnOn();
		tv.soundUp();
		tv.soundDown();
		tv.turnOff();
		
	}

}
