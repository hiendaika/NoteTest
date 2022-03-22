package com.example.supernotes.designpattern.strategy;

public class QuarterDiscountStrategy implements PromoteStrategy{
    @Override
    public double doDiscount(double price) {
        return price * 0.75;
    }
}
