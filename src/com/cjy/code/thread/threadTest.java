package com.cjy.code.thread;

public class threadTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    System.out.println("����ִ��!!!!");
                }
            }
        });

        thread.setDaemon(false);

        thread.start();

        System.out.println("���߳�ִ�����!");
    }

}
