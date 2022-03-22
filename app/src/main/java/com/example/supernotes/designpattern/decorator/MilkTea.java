package com.example.supernotes.designpattern.decorator;

import com.example.supernotes.designpattern.decorator.base.IMilkTea;

public class MilkTea implements IMilkTea {
    @Override
    public double cost() {
        return 5d;
    }
}
