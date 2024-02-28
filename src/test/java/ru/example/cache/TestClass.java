package ru.example.cache;

public class TestClass implements TestInterface   {

    private int counter;

    public TestClass() {
        this.counter = 0;
    }

    @Override
    @Cache(1000)
    public Object methodTest() {
        return new Object();
    }



    @Override
    @Mutator
    public void testCounter() {
        counter = 0;
    }
}


