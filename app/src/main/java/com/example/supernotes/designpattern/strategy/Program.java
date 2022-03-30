package com.example.supernotes.designpattern.strategy;

import java.util.Arrays;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        System.out.println("=== Start run Strategy! ===");
        //Random cac object Ticket(co 3 loai Ticket)
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            int value = random.nextInt(3);
            System.out.println("Value: " + value);
            Ticket ticket = new Ticket();
            ticket.setName("Ticket " + i);
            ticket.setPrice(50d * i);
            switch (value) {
                case 0:
                    ticket.setPromoteStrategy(new NoDiscountStrategy());
                    break;
                case 1:
                    ticket.setPromoteStrategy(new QuarterDiscountStrategy());
                    break;
                default:
                    ticket.setPromoteStrategy(new HalfDiscountStrategy());
                    break;
            }
            //Get promoted price
            double price = ticket.getPromotedPrice();
            System.out.println("Name = " + ticket.getName() + " - Price: " + price);


        }

    }
}
