package ru.example.cache;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCache {
    private static TestClass testCount;
    private static TestInterface intefaceTest;



    @BeforeEach // перед каждым вызовом создаем обект и кеш
    void actual() {
        testCount = new TestClass();
        intefaceTest = new CacheUtils().cache(testCount);

    }

    @Test
    @DisplayName("Время жизни кеша больше времени сна, должен вернуть кеш (1)")
    void getCacheTimeOut() {
        testCount.counterClear();
        var actual = testCount.counterCache();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }

        actual = testCount.counterCache();

        Assertions.assertEquals(1, actual);
    }

    @Test
    @DisplayName("Время жизни кеша меньше времени сна, кеш должен быть сброшен")
    void getCacheTimeOutTest() {
        testCount.counterClear();
        var actual = testCount.counterCache();
        actual = testCount.counterCache();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }
        actual = testCount.counterCache();

        Assertions.assertEquals(2, actual);
    }
}


