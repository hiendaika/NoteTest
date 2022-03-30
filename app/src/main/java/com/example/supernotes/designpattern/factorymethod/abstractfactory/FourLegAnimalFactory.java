package com.example.supernotes.designpattern.factorymethod.abstractfactory;

import com.example.supernotes.designpattern.factorymethod.abstractfactory.AbstractAnimalFactory;
import com.example.supernotes.designpattern.factorymethod.animal.Cat;
import com.example.supernotes.designpattern.factorymethod.animal.Dog;
import com.example.supernotes.designpattern.factorymethod.animal.IAnimal;

import java.util.Random;

public class FourLegAnimalFactory extends AbstractAnimalFactory {
    @Override
    public IAnimal createAnimal() {
        Random random = new Random();
        int type = random.nextInt(2);
        if (type == 0) {
            return new Cat();
        } else {
            return new Dog();
        }
    }
}
