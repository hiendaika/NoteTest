package com.example.supernotes.designpattern.observer;

public class PhoneNotifier extends Observer{
    private Subject subject;
    public PhoneNotifier(Subject subject){
        this.subject = subject;
        this.subject.attachObservers(this);
    }
    @Override
    public void notify(Subject subject, Object arg) {
        if (subject instanceof VideoData) {
            System.out.println("Notify to phone Title: " + ((VideoData) subject).getTitle()
                    + "\nDescription: " + ((VideoData) subject).getDescription()
                    + "\nFileName: " + ((VideoData) subject).getFileName());
        }
    }
}
