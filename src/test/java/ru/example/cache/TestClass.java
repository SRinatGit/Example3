package ru.example.cache;

public class TestClass implements TestInterface {
    private int callCount;
    private long timelife;

    public TestClass() {
        this.callCount = 0;
    }

    @Override
    @Cache
    public int counterCache() {
        if (System.currentTimeMillis() > timelife) {
            return this.callCount;
        }
        return ++this.callCount;
    }

    public void counterClear() {
        timelife = System.currentTimeMillis();
        callCount = 0;
    }

    @Override
    @Mutator
    public int counterMutator() {
        return this.callCount = 0;
    }
}


