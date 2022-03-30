package com.example.supernotes.designpattern.builder.carpart;

public class SeatBelt {
    private String brand;

    public SeatBelt(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
