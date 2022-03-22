package com.example.supernotes.designpattern.strategy;

public class NoDiscountStrategy implements PromoteStrategy{
    @Override
    public double doDiscount(double price) {
        return price;
    }
}
