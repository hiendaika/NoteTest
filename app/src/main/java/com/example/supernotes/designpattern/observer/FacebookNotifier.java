package com.example.supernotes.designpattern.observer;

public class FacebookNotifier extends Observer{
    private Subject subject;
    public FacebookNotifier(Subject subject){
        this.subject = subject;
        this.subject.attachObservers(this);
    }
    @Override
    void notify(Subject subject, Object arg) {
        if (subject instanceof VideoData) {
            System.out.println("Notify to facebook Title: " + ((VideoData) subject).getTitle()
                    + "\nDescription: " + ((VideoData) subject).getDescription()
                    + "\nFileName: " + ((VideoData) subject).getFileName());
        }
    }
}
