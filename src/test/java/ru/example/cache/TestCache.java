package ru.example.cache;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCache {

    static class TestClock implements Clock {
        long time;

        public TestClock(long time) {
            this.time = time;
        }

        @Override
        public long currentMillis() {
            return this.time;
        }
    }

    @Test
    @DisplayName("Задержка меньше времени жизни кэша")
    void getCacheTimeMin() {
        var clock = new TestClock(1500);
        var ci = (TestInterface) new CacheUtils(clock).cache(new TestClass());

        var v1 = ci.methodTest();
        var v2 = ci.methodTest();

        Assertions.assertEquals(v1, v2);
    }
    @Test
    @DisplayName("Сброс кеша ")
    void getCacheTimeClear() {
        var clock = new TestClock(1500);
        var ci = (TestInterface) new CacheUtils(clock).cache(new TestClass());

        var v1 = ci.methodTest();
        ci.testCounter();
        var v2 = ci.methodTest();
        Assertions.assertNotEquals(v1, v2);
    }

}


