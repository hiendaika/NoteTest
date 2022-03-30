package com.example.supernotes.designpattern.observer;

public abstract class Observer {
    protected Subject subject;
    abstract void notify(Subject subject, Object arg);
}
