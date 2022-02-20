package com.learning.basic.reflaction_basic;

import java.util.Arrays;

public class ClassObject {
    public static void main(String[] args) throws ClassNotFoundException {

        // forName
        var clazz1 = Class.forName("com.learning.basic.reflaction_basic.IronMan");
        var clazz2 = Class.forName("com.learning.basic.reflaction_basic.IronMan");

        // Class.forName always returns the same object
        System.out.println(clazz1 == clazz2);

        // ClassName.class
        var clazzInt = int.class;
        var clazzIron = IronMan.class;

        // obj.getClass
        IronMan iron = new IronMan(5, "Tony");
        var clazzFromObj = iron.getClass();

        // super class
        var superClazz = clazzFromObj.getSuperclass();
        System.out.println(superClazz.toString());

        // interfaces implemented by class
        var interfaces = clazz1.getInterfaces();
        Arrays.stream(interfaces).forEach(System.out::println);

        // getName
        System.out.println(clazzIron.getName());

    }
}

