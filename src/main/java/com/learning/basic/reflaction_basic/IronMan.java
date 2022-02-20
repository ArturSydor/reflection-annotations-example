package com.learning.basic.reflaction_basic;

import java.io.Serializable;

public class IronMan implements Serializable {
    public String COUNTRY = "US";
    private int power;
    private String name;

    private IronMan() {
        this.power = 1;
        this.name = "DEFAULT";
    }

    public IronMan(int power, String name) {
        this.power = power;
        setName(name);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IronMan{" +
                "COUNTRY='" + COUNTRY + '\'' +
                ", power=" + power +
                ", name='" + name + '\'' +
                '}';
    }
}