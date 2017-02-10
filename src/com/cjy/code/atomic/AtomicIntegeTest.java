package com.cjy.code.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegeTest {
	
	static AtomicInteger integer = new AtomicInteger(0);
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					integer.incrementAndGet();
				}
			}
		}).start();
		
		
		
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10000; i++) {
					integer.incrementAndGet();
				}
			}
		}).start();
		
		
		while(Thread.activeCount() > 1){
			Thread.yield();
			System.out.println(integer);
		}
		
		
	}

}
