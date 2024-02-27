package ru.example.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Указывает, что наша Аннотация может быть использована во время выполнения через Reflection (нам как раз это нужно)
@Retention(RetentionPolicy.RUNTIME)
//Указывает, что целью нашей Аннотации является метод, Не класс, не переменная, не поле, а именно метод.
@Target(ElementType.METHOD)
public @interface Cache {    //аннотация, кэшировать вызов метода
    long value() default 500; // время жизни кеша
}
