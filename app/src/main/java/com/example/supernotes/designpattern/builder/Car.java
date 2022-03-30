package com.example.supernotes.designpattern.builder;

import com.example.supernotes.designpattern.builder.carpart.Engine;
import com.example.supernotes.designpattern.builder.carpart.SeatBelt;
import com.example.supernotes.designpattern.builder.carpart.WindScreen;

public class Car {
    private int numberOfWheels;
    private SeatBelt seatBelt;
    private String color;
    private WindScreen windScreen;
    private Engine engine;

    public Car(int numberOfWheels, SeatBelt seatBelt, String color, WindScreen windScreen, Engine engine) {
        this.numberOfWheels = numberOfWheels;
        this.seatBelt = seatBelt;
        this.color = color;
        this.windScreen = windScreen;
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Car{" +
                "numberOfWheels=" + numberOfWheels +
                ", seatBelt=" + seatBelt +
                ", color='" + color + '\'' +
                ", windScreen=" + windScreen +
                ", engine=" + engine +
                '}';
    }
}
