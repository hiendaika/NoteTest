package com.example.supernotes.designpattern.factorymethod.abstractfactory;

import com.example.supernotes.designpattern.factorymethod.abstractfactory.AbstractAnimalFactory;
import com.example.supernotes.designpattern.factorymethod.animal.Chicken;
import com.example.supernotes.designpattern.factorymethod.animal.Duck;
import com.example.supernotes.designpattern.factorymethod.animal.IAnimal;

import java.util.Random;

public class TwoLegAnimalFactory extends AbstractAnimalFactory {
    @Override
    public IAnimal createAnimal() {
        Random random = new Random();
        int type = random.nextInt(2);
        if (type == 0) {
            return new Duck();
        } else {
            return new Chicken();
        }
    }
}
