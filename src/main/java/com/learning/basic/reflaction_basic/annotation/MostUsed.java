package com.learning.basic.reflaction_basic.annotation;

import java.lang.annotation.*;

@Documented
/*
    When annotation marked with @Documented is used
    above class, method, etc. Then this annotation
    will be visible in generated java docs.
    Example:

    @MostView
    public class Test {}

    If Test class also have @SuppressWarnings annotation,
    it will not be visible in generated docs
 */
@Inherited
/*
   Means that annotation marked with @Inherited will be also
   inherited for subclassed.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
/*
    @Target Says where annotation could be used.
    In this case annotation could be used
    only with type(class, interface...), methods
 */
@Retention(RetentionPolicy.RUNTIME)
/*
    @Retention specifies how long annotation marked
    with it should be read. For example:
    SOURCE - till the time it is compiled,
    after compilation it will be discarded by compiler
 */
public @interface MostUsed {

    String value() default "Java";

}
