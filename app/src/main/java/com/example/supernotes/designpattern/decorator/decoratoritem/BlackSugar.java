package com.example.supernotes.designpattern.decorator.decoratoritem;

import com.example.supernotes.designpattern.decorator.MilkTeaDecorator;
import com.example.supernotes.designpattern.decorator.base.IMilkTea;

public class BlackSugar extends MilkTeaDecorator {
    public BlackSugar(IMilkTea inner) {
        super(inner);
    }

    @Override
    public double cost() {
        return 2d + super.cost();
    }
}
