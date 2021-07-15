package com.manduScheduler.main.thread;

public class ShoppingThread extends Thread {
	
	private String threadName;

	public ShoppingThread(String threadName) {
		super(threadName);
	}
	
	
	public void run(){
		for(int i=0;i<100;i++){
			System.out.println(threadName+":"+i);
		}
	}
}
 