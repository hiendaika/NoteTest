package com.example.supernotes.designpattern.observer;

public class YoutubeNotifier extends Observer {
    private Subject subject;

    public YoutubeNotifier(Subject subject) {
        this.subject = subject;
        this.subject.attachObservers(this);
    }

    @Override
    public void notify(Subject subject, Object arg) {
        if (subject instanceof VideoData) {
            System.out.println("Youtube to email Title: " + ((VideoData) subject).getTitle()
                    + "\nDescription: " + ((VideoData) subject).getDescription()
                    + "\nFileName: " + ((VideoData) subject).getFileName());
        }
    }
}
