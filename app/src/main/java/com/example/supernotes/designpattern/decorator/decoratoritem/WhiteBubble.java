package com.example.supernotes.designpattern.decorator.decoratoritem;

import com.example.supernotes.designpattern.decorator.MilkTeaDecorator;
import com.example.supernotes.designpattern.decorator.base.IMilkTea;

public class WhiteBubble extends MilkTeaDecorator {
    public WhiteBubble(IMilkTea inner) {
        super(inner);
    }

    @Override
    public double cost() {
        return 1.5d + super.cost();
    }
}
