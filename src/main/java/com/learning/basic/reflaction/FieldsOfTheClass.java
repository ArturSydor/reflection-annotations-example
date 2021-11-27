package com.learning.basic.reflaction;

import java.lang.reflect.Field;

public class FieldsOfTheClass {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // non-declared: return all public elements of that class and its superclass
        // declared: return all elements of that class only

        var iron = new IronMan(10, "John");
        System.out.println(iron);
        var ironClazz = iron.getClass();

        // return all public fields of class and its superclass
        var publicFields = ironClazz.getFields();
        for (Field field : publicFields) {
            System.out.println(field.getName());
        }

        // return all fields public, protected, private
        var allFields = ironClazz.getDeclaredFields();
        for (Field field : allFields) {
            System.out.println(field.getName());
        }

        var countryField = ironClazz.getField("COUNTRY");
        countryField.set(iron, "UK");

        System.out.println(iron);

        var powerField = ironClazz.getDeclaredField("power");
        powerField.setAccessible(Boolean.TRUE);
        powerField.set(iron, 1000);

        System.out.println(iron);


    }

}
