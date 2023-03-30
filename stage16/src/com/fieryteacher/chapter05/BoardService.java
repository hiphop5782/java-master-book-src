package com.fieryteacher.chapter05;

@Registration
public class BoardService {
	@Required
	private MemberManager manager;
	public MemberManager getManager() {
		return manager;
	}
	//코드생략
}
