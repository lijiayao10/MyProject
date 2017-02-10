package com.cjy.code.nio.actor;

import akka.actor.UntypedActor;

public class PrintSumActor extends UntypedActor {

    int sumCount = 0;
    int sum      = 0;

    @Override
    public void onReceive(Object arg0) throws Exception {
        PrintSum printSumAndShutdown = (PrintSum) arg0;

        sumCount++;

        sum += printSumAndShutdown.getResult();

        if (sumCount >= printSumAndShutdown.getCount()) {

            System.out.println("sum:" + sum);
            System.out.println("time:" + (System.currentTimeMillis() - getContext().system().startTime()));

            getContext().system().shutdown();
        }

    }

}
