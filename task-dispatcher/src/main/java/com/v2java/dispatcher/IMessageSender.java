package com.v2java.dispatcher;

public interface IMessageSender<T> {

    void sendToMq(T msg);
}
