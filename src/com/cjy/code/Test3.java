package com.cjy.code;
public class Test3 {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        final Test3 test3 = new Test3();

        int i = 0;

        while (i++ < 1000) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    test3.add();
                }
            }).start();
        }

        Thread.sleep(5000);

        System.out.println("result :::::::::========>" + test3.i);

    }

    private Integer i = 0;

    private void add() {
        i++;
    }

}
