package com.cjy.code.atomic;

public class LockintTest {
	
	static int ii = 0;

	public static void main(String[] args) {
		Thread[] threads = new Thread[20];
		
		for (int j = 0; j < threads.length; j++) {
			threads[j] = new Thread(new Runnable() {
				public void run() {
					for (int i = 0; i < 1000000; i++) {
							ii=ii+1;
					}
				}
			});
		}
		
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		
		while(Thread.activeCount() > 1){
			Thread.yield();
			System.out.println(ii);
		}
		
	}

}
