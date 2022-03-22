package com.example.supernotes.designpattern.singleton;

import java.util.Random;

public class MySingleton {
    private static MySingleton uniqueInstance;

    private MySingleton() {

    }

    public static MySingleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (MySingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MySingleton();
                }
            }
        }
        return uniqueInstance;
    }

    public void sayHello() {
        //Random số
        Random random = new Random();
        int number = random.nextInt(4);
        System.out.println("Say Hello: " + number);
    }
}
