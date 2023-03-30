package com.fieryteacher.chapter06;

import com.fieryteacher.chapter05.ApplicationContext;

public class Test01 {
	public static void main(String[] args) {
		ApplicationContext ctx = new ApplicationContext();
		FileManager manager = ctx.getInstance(FileManager.class);
		System.out.println(manager);
	}
}
