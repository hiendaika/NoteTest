package com.example.supernotes.designpattern.decorator.decoratoritem;

import com.example.supernotes.designpattern.decorator.MilkTeaDecorator;
import com.example.supernotes.designpattern.decorator.base.IMilkTea;

public class Bubble extends MilkTeaDecorator {
    public Bubble(IMilkTea inner) {
        super(inner);
    }

    @Override
    public double cost() {
        return 1d + super.cost();
    }
}
