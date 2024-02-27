package ru.example.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//целью нашей Аннотации является метод
@Target(ElementType.METHOD)
public @interface Mutator {  //аннотация, сбросить кэш
}
