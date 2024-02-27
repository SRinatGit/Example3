package ru.example.cache;

public class Fraction implements FractionInterface {
    private int num;
    private int denum;


    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Cache(1000)
    public double doubleValue() {
        System.out.println("    Fraction.doubleValue: вызов");
        return (double) num / denum;
    }

    @Mutator
    public void setNum(int num) {
        System.out.println("    Fraction.setNum: вызов");
        this.num = num;
    }

    @Mutator
    public void setDenum(int denum) {
        System.out.println("    Fraction.setDenum: вызов");
        this.denum = denum;
    }
}
