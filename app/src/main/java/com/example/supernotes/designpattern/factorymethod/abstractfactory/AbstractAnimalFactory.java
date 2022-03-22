package com.example.supernotes.designpattern.factorymethod.abstractfactory;

import com.example.supernotes.designpattern.factorymethod.animal.IAnimal;
import com.example.supernotes.designpattern.factorymethod.factory.IAnimalFactory;

public abstract class AbstractAnimalFactory implements IAnimalFactory {
    public abstract IAnimal createAnimal();
}
