package com.weblab.springex.di;

import org.springframework.stereotype.Component;

@Component("apple")
public class AppleSpeaker implements Speaker {

	@Override
	public void volumnUp() {
		System.out.println("Apple Speaker - 소리를 높이다");
	}

	@Override
	public void volumnDown() {
		System.out.println("Apple Speaker - 소리를 낮추다");
	}

	@Override
	public String toString() {
		return "AppleSpeaker";
	}

}
