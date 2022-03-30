package com.example.supernotes.designpattern.decorator;

import com.example.supernotes.designpattern.decorator.base.IMilkTea;

public abstract class MilkTeaDecorator implements IMilkTea {
    private IMilkTea iMilkTea;

    protected MilkTeaDecorator(IMilkTea inner) {
        this.iMilkTea = inner;
    }

    @Override
    public double cost() {
        return iMilkTea.cost();
    }
}
