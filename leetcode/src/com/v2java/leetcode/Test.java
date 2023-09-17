package com.v2java.leetcode;

public class Test {

    public String str="123";
    private int num=1;
    final Integer num2=2;
    static Integer num3 = Integer.MAX_VALUE;

    static {
        num3 = Integer.MIN_VALUE;
    }

    public Test(){
        this.num=3;
    }

    public int add(int c){
        int a=1;
        int b=2;
        return a+b+c;
    }

    public static void main(String[] args) {
        new Test().add(1);
    }
}
