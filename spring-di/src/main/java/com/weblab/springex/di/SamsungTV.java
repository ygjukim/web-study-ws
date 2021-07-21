package com.weblab.springex.di;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("samsung")
public class SamsungTV implements TV {
	
	@Autowired(required=false)
	@Qualifier("apple")
	private Speaker speaker;
	@Value("${samsung.model}")
	private String model;
	@Value("${samsung.purchaseDate}")
	private Date purchaseDate;

	public SamsungTV() {
	}
	
	public SamsungTV(Speaker speaker, String model, Date purchaseDate) {
		this.speaker = speaker;
		this.model = model;
		this.purchaseDate = purchaseDate;
	}

	@Override
	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	@Override
	public void powerOn() {
		System.out.println("SamsunTV - 전원을 켜다");
	}

	@Override
	public void powerOff() {
		System.out.println("SamsunTV - 전원을 끄다");
	}

	@Override
	public void volumnUp() {
		if (speaker != null)
			speaker.volumnUp();
		else 
			System.out.println("내장 스피커 - 소리 높이다");
	}

	@Override
	public void volumnDown() {
		if (speaker != null)
			speaker.volumnDown();
		else 
			System.out.println("내장 스피커 - 소리 낮추다");
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	@Override
	public String toString() {
		return "SamsungTV [speaker=" + speaker + ", model=" + model + ", purchaseDate=" + purchaseDate + "]";
	}
	
}
