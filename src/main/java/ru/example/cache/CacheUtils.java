package ru.example.cache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {
    private Clock clock;

    public CacheUtils(Clock clock) {
        this.clock = clock;
    }

    public CacheUtils() {
        this(System::currentTimeMillis);
    }

    public static Map<String, Long> getAnnotatedMethodsMap(Object obj, Class annotation) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        Map<String, Long> annotatedMethods = new HashMap<>();
        for (Method method : methods) {
            if (method.getAnnotation(annotation) != null) {
                Cache cacheAnnotation = (Cache) method.getAnnotation(annotation);
                annotatedMethods.put(method.getName(), cacheAnnotation.value());// запись метод + время жизни из поля интерфейса
            }
        }

        return annotatedMethods;
    }

    public static List<String> getAnnotatedMethodsList(Object obj, Class annotation) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        List<String> annotatedMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.getAnnotation(annotation) != null) {
                annotatedMethods.add(method.getName());
            }
        }

        return annotatedMethods;
    }

    private static Key getLatestCacheValue(List<Key> objectCaches) {
        ListIterator li = objectCaches.listIterator(objectCaches.size());
        while (li.hasPrevious()) {
            return (Key) li.previous();
        }
        return null;
    }

    private static void deleteNotLive(List<Key> objectCaches, Clock clock) {
        ListIterator li = objectCaches.listIterator(objectCaches.size());
        while (li.hasPrevious()) {
            Key dc = (Key) li.previous();
            if (!dc.isLive(clock.currentMillis())) {
                synchronized (objectCaches) {
                    objectCaches.remove(objectCaches.indexOf(dc));
                }
            }
        }
    }

    public  <T> T cache(T obj) {
        Map<String, List<Key>> cacheMap = new ConcurrentHashMap<>();

        Map<String, Long> cacheMethods = getAnnotatedMethodsMap(obj, Cache.class);
        List<String> mutatorMethods = getAnnotatedMethodsList(obj, Mutator.class);

        InvocationHandler handler = (proxy, method, args) -> {
            String methodName = method.getName();
            Object result;
            Key cacheKey = null;
            Long timeLive = cacheMethods.get(methodName); // получим время жизни объекта в кеше из поля аннотации

            if (timeLive != null) {// если в кеше есть
                if (cacheMap.containsKey(methodName)) { // если methodName ключ в cacheMap
                    deleteNotLive(cacheMap.get(methodName), clock); // удаляем то что есть если срок жизни истек
                    cacheKey = getLatestCacheValue(cacheMap.get(methodName));// получаем последний элемент  в списке кешей ключей
                } else {
                    cacheMap.put(methodName, new ArrayList<>()); // если метода нет в кеше, тогда добавляем
                }

                if (cacheKey == null) { // если ключа нет, т.е. еще не кешировали
                    result = method.invoke(obj, args);
                    Key cacheValue = new Key(result, timeLive, clock); // создаем новый ключ с текушим временем
                    cacheMap.get(methodName).add(cacheValue);// добавляем ключ в массив ключей данного метода
                    synchronized (cacheMap) {
                        cacheMap.put(methodName, cacheMap.get(methodName)); // добавляем в кеш метод + массив кешей Key
                    }
                } else {
                    result = cacheKey.getVal(); // вернем закешированный объект
                }
                return result;
            }


            if (mutatorMethods.contains(methodName)) {// если метод со сбросом кеша
                synchronized (cacheMap) {
                    cacheMap.clear(); // чистим весь кэш без разбора
                }
                return method.invoke(obj, args);
            }

            return method.invoke(obj, args);
        };

        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}