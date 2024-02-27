package ru.example.cache;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Start ===============================");

        Fraction fr = new Fraction(2, 3); //= 123
        FractionInterface num = new CacheUtils().cache(fr); //CacheUtils.cache(fr); //= 567

        num.doubleValue(); System.out.println("     sout сработал");// sout сработал
        num.doubleValue(); System.out.println("     sout молчит");// sout молчит

        num.doubleValue(); System.out.println("     sout молчит");// 

        num.setNum(5);
        num.doubleValue(); System.out.println("     sout сработал");// sout сработал
        num.doubleValue(); System.out.println("     sout молчит");// sout молчит

        num.setNum(2);
        num.doubleValue(); System.out.println("      sout молчит");// sout молчит
        num.doubleValue(); System.out.println("     sout молчит");// sout молчит

        Thread.sleep(1500);
        num.doubleValue();System.out.println("      sout сработал");// sout сработал
        num.doubleValue(); System.out.println("     sout молчит");// sout молчит

        System.out.println("End ===============================");


    }
}
