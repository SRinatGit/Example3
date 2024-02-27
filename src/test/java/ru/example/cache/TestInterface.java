package ru.example.cache;

public interface TestInterface {

    @Cache
    int counterCache();

    @Mutator
    int counterMutator();

}