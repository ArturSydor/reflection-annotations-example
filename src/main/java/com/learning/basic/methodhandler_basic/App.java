package com.learning.basic.methodhandler_basic;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class App {
    public static void main(String[] args) throws Throwable {
        var lookup = MethodHandles.lookup();
        var clazz = lookup.findClass(City.class.getName());

        var stringGetterType = MethodType.methodType(String.class);

        var getTitleHandler = lookup.findVirtual(clazz, "getTitle", stringGetterType);

        var lviv = City.builder()
                .title("Lviv")
                .population(1_000_000)
                .postCode("11111")
                .build();

        getTitleHandler.invoke(lviv);

        var noArgsConstructorType = MethodType.methodType(void.class);
        var noArgsConstructorHandler = lookup.findConstructor(clazz, noArgsConstructorType);
        var emptyCity = (City) noArgsConstructorHandler.invoke();
        emptyCity.setTitle("EMPTY");
        System.out.println(emptyCity);

        var allArgsConstructorType = MethodType.methodType(void.class, String.class, int.class, String.class);
        var allArgsConstructorHandler = lookup.findConstructor(clazz, allArgsConstructorType);
        var london = (City) allArgsConstructorHandler.invoke("London", 20_000_000, "22222");
        System.out.println(london);

        var stringSetterType = MethodType.methodType(void.class, String.class);
        var setPostCodeHandler = lookup.findVirtual(clazz, "setPostCode", stringSetterType);
        setPostCodeHandler.invoke(london, "12345");
        System.out.println(london);

        // It is needed to access private fields
        var privateLookup = MethodHandles.privateLookupIn(clazz, lookup);

        var getPopulationHandler = privateLookup.findGetter(clazz, "population", int.class);
        var setPopulationHandler = privateLookup.findSetter(clazz, "population", int.class);
        var currentPopulation = (int) getPopulationHandler.invoke(london);
        setPopulationHandler.invoke(london, currentPopulation * 2);
        System.out.println(london);

        var titleVarHandler = privateLookup.findVarHandle(clazz, "title", String.class);
        System.out.println("VarHandler for " + titleVarHandler.get(lviv));

    }
}
