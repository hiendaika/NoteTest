package com.example.supernotes.designpattern.observer;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void attachObservers(Observer observer){
        observers.add(observer);
    }

    public void detachObservers(Observer observer){
        observers.remove(observer);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void notifyObservers(Subject subject, Object obj){
        observers.forEach(observer -> {observer.notify(subject, obj);});
    }
}
