package com.fieryteacher.chapter05;

@Registration
public class AdvertiseService {
	@Required
	private MemberManager manager;
	public MemberManager getManager() {
		return manager;
	}
	//코드생략
}
