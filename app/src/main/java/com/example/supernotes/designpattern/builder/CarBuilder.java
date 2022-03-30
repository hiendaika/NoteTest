package com.example.supernotes.designpattern.builder;

import com.example.supernotes.designpattern.builder.carpart.Engine;
import com.example.supernotes.designpattern.builder.carpart.SeatBelt;
import com.example.supernotes.designpattern.builder.carpart.WindScreen;

public class CarBuilder implements ICarBuilder{
    private int numberOfWheels;
    private SeatBelt seatBelt;
    private String color;
    private WindScreen windScreen;
    private Engine engine;

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public SeatBelt getSeatBelt() {
        return seatBelt;
    }

    public void setSeatBelt(SeatBelt seatBelt) {
        this.seatBelt = seatBelt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public WindScreen getWindScreen() {
        return windScreen;
    }

    public void setWindScreen(WindScreen windScreen) {
        this.windScreen = windScreen;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }


    @Override
    public CarBuilder addWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
        return this;
    }

    @Override
    public CarBuilder addSeatBelt(SeatBelt seatBelt) {
        this.seatBelt = seatBelt;
        return this;
    }

    @Override
    public CarBuilder addColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public CarBuilder addWindScreen(WindScreen windScreen) {
        this.windScreen = windScreen;
        return this;
    }

    @Override
    public CarBuilder addEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    @Override
    public Car build() {
        return new Car(numberOfWheels,seatBelt,color,windScreen,engine);
    }
}
