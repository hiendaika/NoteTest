package com.example.supernotes.designpattern.strategy;

public class Ticket {
    private double price;
    private PromoteStrategy promoteStrategy;
    private String name;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PromoteStrategy getPromoteStrategy() {
        return promoteStrategy;
    }

    public void setPromoteStrategy(PromoteStrategy promoteStrategy) {
        this.promoteStrategy = promoteStrategy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Tao constructor truyen vao PromoteStrategy
    public Ticket(PromoteStrategy promoteStrategy) {
        this.promoteStrategy = promoteStrategy;
    }

    public Ticket() {

    }

    //Lay gia tri giam bang cach goi ham doDiscount
    public double getPromotedPrice() {
        return promoteStrategy.doDiscount(price);
    }
}
