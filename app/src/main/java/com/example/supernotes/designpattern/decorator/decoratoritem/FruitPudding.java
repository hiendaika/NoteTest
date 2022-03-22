package com.example.supernotes.designpattern.decorator.decoratoritem;

import com.example.supernotes.designpattern.decorator.MilkTeaDecorator;
import com.example.supernotes.designpattern.decorator.base.IMilkTea;

public class FruitPudding extends MilkTeaDecorator {
    public FruitPudding(IMilkTea inner) {
        super(inner);
    }

    @Override
    public double cost() {
        return 3d + super.cost();
    }
}
