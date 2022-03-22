package com.example.supernotes.designpattern.singleton;

public class Program {
    public static void main(String[] args) {

        //Tạo 2 thread để test multi-thread vs Singleton
        Thread thread1 = new Thread(){
            @Override
            public void run() {
//                super.run();
                MySingleton.getInstance().sayHello();
            }
        };
        thread1.start();

        Thread thread2 = new Thread(){
            @Override
            public void run() {
//                super.run();
                MySingleton.getInstance().sayHello();
            }
        };
        thread2.start();

    }


}
