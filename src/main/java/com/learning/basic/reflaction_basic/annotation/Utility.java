package com.learning.basic.reflaction_basic.annotation;

@MostUsed // visible in generated docs and inherited by children
public class Utility {

    void doSth() {
        System.out.println("Do sth");
    }

    @MostUsed("Scala")
    void doSth(String action) {
        System.out.println("Do sth: " + action);
    }

    void doSth(int times) {
        System.out.println("Do sth " + times + " times");
    }

}

// This class inherits @MostView annotation for its parent
class SubUtility extends Utility {
}
