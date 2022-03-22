package com.example.supernotes.designpattern.factorymethod;

import com.example.supernotes.designpattern.factorymethod.animal.IAnimal;
import com.example.supernotes.designpattern.factorymethod.abstractfactory.FourLegAnimalFactory;
import com.example.supernotes.designpattern.factorymethod.factory.IAnimalFactory;
import com.example.supernotes.designpattern.factorymethod.abstractfactory.TwoLegAnimalFactory;

import java.util.Random;

public class Program {
    public static void main(String[] args) {
        IAnimalFactory factory;
        Random random = new Random();
        int value = random.nextInt(2); //Return 0,1

        if (value == 0) {
            factory = new TwoLegAnimalFactory();
//            factory = new BasicAnimalFactory();
        } else {
//            factory = new RandomAnimalFactory();
            factory = new FourLegAnimalFactory();
        }

        IAnimal animal = factory.createAnimal();

    }
}
