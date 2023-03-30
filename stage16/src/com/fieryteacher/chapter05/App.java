package com.fieryteacher.chapter05;

public class App {
	public static void main(String[] args) {
		ApplicationContext context = new ApplicationContext();
		BoardService boardService = context.getInstance(BoardService.class);
		ReviewService reviewService = context.getInstance(ReviewService.class);
		PurchaseService purchaseService = context.getInstance(PurchaseService.class);
		BookmarkService bookmarkService = context.getInstance(BookmarkService.class);
		AdvertiseService advertiseService = context.getInstance(AdvertiseService.class);
		MemberManager memberManager = context.getInstance(MemberManager.class);
		System.out.println("boardService의 manager와 비교 = " + (boardService.getManager() == memberManager));
		System.out.println("reviewService의 manager와 비교 = " + (reviewService.getManager() == memberManager));
		System.out.println("purchaseService의 manager와 비교 = " + (purchaseService.getManager() == memberManager));
		System.out.println("bookmarkService의 manager와 비교 = " + (bookmarkService.getManager() == memberManager));
		System.out.println("advertiseService의 manager와 비교 = " + (advertiseService.getManager() == memberManager));
	}
}
