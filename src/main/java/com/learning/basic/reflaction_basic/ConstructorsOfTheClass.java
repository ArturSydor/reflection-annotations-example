package com.learning.basic.reflaction_basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorsOfTheClass {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var ironClazz = Class.forName("com.learning.basic.reflaction_basic.IronMan");

        // returns public constructors of class
        var publicConstructors = ironClazz.getConstructors();
        for (Constructor<?> c: publicConstructors) {
            System.out.println(c);
        }
        System.out.println("-------------");
        // returns all constructors of that class
        var allConstructors = ironClazz.getDeclaredConstructors();
        for (Constructor<?> c: allConstructors) {
            System.out.println(c);
        }

        var publicConstructor = ironClazz.getConstructor(int.class, String.class);
        var iron = publicConstructor.newInstance(30, "Reflection");
        System.out.println(iron);

        var privateConstructor = ironClazz.getDeclaredConstructor();
        privateConstructor.setAccessible(Boolean.TRUE);
        iron = privateConstructor.newInstance();
        System.out.println(iron);
;
    }
}
