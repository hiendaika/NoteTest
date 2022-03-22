package com.example.supernotes.designpattern.factorymethod.factory;

import com.example.supernotes.designpattern.factorymethod.animal.Cat;
import com.example.supernotes.designpattern.factorymethod.animal.Dog;
import com.example.supernotes.designpattern.factorymethod.animal.Duck;
import com.example.supernotes.designpattern.factorymethod.animal.IAnimal;

import java.util.Random;

public class RandomAnimalFactory implements IAnimalFactory{
    @Override
    public IAnimal createAnimal() {
        Random random = new Random();
        int value = random.nextInt(3);//Return value 0->3
        if (value == 0){
            return new Dog();
        }else if (value == 1){
           return new Cat();
        }else {
            return new Duck();
        }
    }
}
