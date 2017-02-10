package com.cjy.code.timer;

import java.util.Timer;
import java.util.TimerTask;

public class OutFtime {

    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(1000l);
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(5000l);
    }

    static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("÷¥––¡À!!!");
            throw new RuntimeException();
        }
    }

}
