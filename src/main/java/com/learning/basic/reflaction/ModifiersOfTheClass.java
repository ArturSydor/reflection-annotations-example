package com.learning.basic.reflaction;

import java.lang.reflect.Modifier;

public class ModifiersOfTheClass {
    public static void main(String[] args) throws NoSuchMethodException {
        // Modifier allows to decode class and members access modifiers

        var iron = new IronMan(1, "Child");
        var ironClazz = iron.getClass();

        int classModifier = ironClazz.getModifiers();
        System.out.println("Is public? " + ((classModifier & Modifier.PUBLIC) == 1));

        int powerModifier = ironClazz.getMethod("getPower").getModifiers();
        System.out.println("Is public? " + Modifier.isPublic(powerModifier));
        System.out.println("Is private? " + Modifier.isPrivate(powerModifier));


    }
}
