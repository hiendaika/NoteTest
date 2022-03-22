package com.example.supernotes.designpattern.observer;

public class VideoData extends Subject{
    private String title;
    private String description;
    private String fileName;

    private EmailNotifier emailNotifier;
    private YoutubeNotifier youtubeNotifier;
    private PhoneNotifier phoneNotifier;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        videoDataChanged();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        videoDataChanged();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        videoDataChanged();
    }

    public EmailNotifier getEmailNotifier() {
        return emailNotifier;
    }

    public void setEmailNotifier(EmailNotifier emailNotifier) {
        this.emailNotifier = emailNotifier;
    }

    public YoutubeNotifier getYoutubeNotifier() {
        return youtubeNotifier;
    }

    public void setYoutubeNotifier(YoutubeNotifier youtubeNotifier) {
        this.youtubeNotifier = youtubeNotifier;
    }

    public PhoneNotifier getPhoneNotifier() {
        return phoneNotifier;
    }

    public void setPhoneNotifier(PhoneNotifier phoneNotifier) {
        this.phoneNotifier = phoneNotifier;
    }

    //Function gọi các thông báo
    void videoDataChanged() {
        notifyObservers(this,null);

    }
}
