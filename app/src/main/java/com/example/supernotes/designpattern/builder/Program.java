package com.example.supernotes.designpattern.builder;

import com.example.supernotes.designpattern.builder.carpart.Engine;
import com.example.supernotes.designpattern.builder.carpart.SeatBelt;
import com.example.supernotes.designpattern.builder.carpart.WindScreen;

public class Program {
    public static void main(String[] args) {
        Car car = new CarBuilder()
                .addWheels(4)
                .addSeatBelt(new SeatBelt("brand"))
                .addColor("Green")
                .addWindScreen(new WindScreen("WindScreen"))
                .addEngine(new Engine("V8"))
                .build();

        System.out.println(car.toString());
    }
}
