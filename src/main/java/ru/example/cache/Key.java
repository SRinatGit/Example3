package ru.example.cache;

public class Key {// https://habr.com/ru/articles/140214/


    private final Object val;
    private long timelife; // время жизни

    public Key(Object val, long timeout) {
        this.val = val;
        this.timelife = System.currentTimeMillis() + timeout;
    }
    public Key(Object val, long timeout, Clock clock) {
        this.val = val;
        this.timelife = clock.currentMillis() + timeout;
    }

  /*  public String getKey() {
        return val;
    }
*/
    public Object getVal() {
        return val;
    }

    public void setTimelife(long timelife) {
        this.timelife = timelife;
    }

    public boolean isLive(long currentTimeMillis) {
        return currentTimeMillis < timelife;
    }

}