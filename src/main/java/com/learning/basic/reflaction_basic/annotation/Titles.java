package com.learning.basic.reflaction_basic.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Titles {
    Title[] value();
}
