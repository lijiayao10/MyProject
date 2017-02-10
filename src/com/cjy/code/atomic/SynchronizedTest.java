package com.cjy.code.atomic;

public class SynchronizedTest {
	
	static Integer ii = 0;
	
//	static Object lock = new Object();
	

	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[20];
		
		 final Object lock = new Object();
		 
		
		for (int j = 0; j < threads.length; j++) {
			threads[j] = new Thread(new Runnable() {
				public void run() {
					for (int i = 0; i < 1000000; i++) {
						synchronized (lock) {
							ii++;
						}
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
