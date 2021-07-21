package com.weblab.springex.di;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("lg")
public class LgTV implements TV {
	
	@Autowired(required=false)
	@Qualifier("sony")
	private Speaker speaker = null;
	@Value("${lg.model}")
	private String model;
	@Value("${lg.purchaseDate}")
	private Date purchaseDate;

	public LgTV() {
	}
	
	public LgTV(Speaker speaker, String model, Date purchaseDate) {
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
		System.out.println("LG-TV - 전원을 켜다");
	}

	@Override
	public void powerOff() {
		System.out.println("LG-TV - 전원을 끄다");
	}

	@Override
	public void volumnUp() {
		if (speaker != null)
			speaker.volumnUp();
		else
			System.out.println("LG-TV - 소리를 높이다");
	}

	@Override
	public void volumnDown() {
		if (speaker != null)
			speaker.volumnDown();
		else
			System.out.println("LG-TV - 소리를 낮추다");
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
		return "LgTV [speaker=" + speaker + ", model=" + model + ", purchaseDate=" + purchaseDate + "]";
	}

}
