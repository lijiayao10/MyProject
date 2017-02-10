package com.cjy.code.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.commons.lang3.event.EventListenerSupport;

public class MyActionEventSource {
    private EventListenerSupport<ActionListener> actionListeners = EventListenerSupport
        .create(ActionListener.class); //创建针对于特定监听器的对象。

    public void someMethodThatFiresAction() {//创建一个Event对象。
        ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "somethingCool");
        actionListeners.fire().actionPerformed(e);//触发事件通知所有的listeners
    }
}
