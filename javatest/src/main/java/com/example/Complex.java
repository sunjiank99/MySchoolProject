package com.example;

/**
 * Created by Administrator on 2017/10/20.
 */

public class Complex {
    float intNum=0;
    float virtualNum=0;
    public  Complex(float i,float j)
    {
        this.intNum=i;
        this.virtualNum=j;
    }

    public  Complex(float i)
    {
        this.intNum=i;
    }

    public void show()
    {
        System.out.println(intNum+","+virtualNum+"i");
    }

    public void add(Complex c)
    {
        this.intNum+=c.intNum;
        virtualNum+=c.virtualNum;
    }
    public void sub(Complex c)
    {
        this.intNum-=c.intNum;
        virtualNum-=c.virtualNum;
    }

}
