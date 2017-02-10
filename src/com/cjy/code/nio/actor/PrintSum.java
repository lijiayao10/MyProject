package com.cjy.code.nio.actor;

public class PrintSum {

    private int count;

    private int result;

    public PrintSum(int result, int count) {
        this.result = result;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
