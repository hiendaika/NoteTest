package com.example.supernotes.designpattern.builder;

import com.example.supernotes.designpattern.builder.carpart.Engine;
import com.example.supernotes.designpattern.builder.carpart.SeatBelt;
import com.example.supernotes.designpattern.builder.carpart.WindScreen;

public interface ICarBuilder {
    CarBuilder addWheels(int numberOfWheels);
    CarBuilder addSeatBelt(SeatBelt seatBelt);
    CarBuilder addColor(String color);
    CarBuilder addWindScreen(WindScreen windScreen);
    CarBuilder addEngine(Engine engine);
    Car build();
}
