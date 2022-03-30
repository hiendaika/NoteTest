package com.example.supernotes.designpattern.decorator.decoratoritem;

import com.example.supernotes.designpattern.decorator.MilkTeaDecorator;
import com.example.supernotes.designpattern.decorator.base.IMilkTea;

public class EggPudding extends MilkTeaDecorator {
    public EggPudding(IMilkTea inner) {
        super(inner);
    }

    @Override
    public double cost() {
        return 2.5d +  super.cost();
    }
}
