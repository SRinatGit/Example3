package ru.example.cache;

interface FractionInterface {
    //@Cache
    double doubleValue();
    //@Mutator
    void setNum(int num);
    //@Mutator
    void setDenum(int denum);
}