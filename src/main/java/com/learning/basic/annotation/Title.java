package com.learning.basic.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(Titles.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Title {
    String value();
}
