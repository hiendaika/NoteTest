package com.example.supernotes.designpattern.observer;

public class Program {
    public static void main(String[] args) {
        VideoData data = new VideoData();
        EmailNotifier emailNotifier = new EmailNotifier(data);
        YoutubeNotifier youtubeNotifier = new YoutubeNotifier(data);
        PhoneNotifier phoneNotifier = new PhoneNotifier(data);

        //Set cac thông tin cho VideoData
        //Khi này sẽ có 3 notify cho các observer
        data.setTitle("Observer Design Pattern");

        //Detach các observer
        data.detachObservers(youtubeNotifier);
        System.out.println("====================");
        //Khi cập nhật description chỉ có 2 notify cho Email và Phone
        data.setDescription("My description video");

        //Thêm facebook notifier
        FacebookNotifier facebookNotifier = new FacebookNotifier(data);
        System.out.println("====================");
        data.setFileName("New File Name");
    }
}
