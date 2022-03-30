package com.example.supernotes.designpattern.decorator;

import com.example.supernotes.designpattern.decorator.decoratoritem.BlackSugar;
import com.example.supernotes.designpattern.decorator.decoratoritem.Bubble;
import com.example.supernotes.designpattern.decorator.decoratoritem.EggPudding;
import com.example.supernotes.designpattern.decorator.decoratoritem.FruitPudding;
import com.example.supernotes.designpattern.decorator.decoratoritem.WhiteBubble;

public class Program {
    public static void main(String[] args) {
        EggPudding ourMilkTea = new EggPudding(
                new FruitPudding(
                        new BlackSugar(
                                new Bubble(
                                        new MilkTea()
                                )
                        )
                )
        );

        System.out.println("Cost: " + ourMilkTea.cost());

        EggPudding secondMilkTea = new EggPudding(
                new BlackSugar(
                        new WhiteBubble(
                                new MilkTea()
                        )
                )
        );

        System.out.println("Cost Second MilkTea: " + secondMilkTea.cost());
    }
}
