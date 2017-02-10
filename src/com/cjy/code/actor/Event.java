package com.cjy.code.actor;

public class Event {

    private String data;

    private String uUID;

    public Event(String data, String uUID) {
        this.data = data;
        this.uUID = uUID;
    }

    @Override
    public String toString() {
        return "Data:" + data + ",UUID:" + uUID;
    }

}
