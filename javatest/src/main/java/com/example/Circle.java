package com.example;

/**
 * Created by Administrator on 2017/10/20.
 */

public class Circle {
    private Point middle;
    private int Radiam;

    public Circle(int x,int y,int R)
    {
        middle=new Point(x,y);
        Radiam=R;
    }

    public void outPut()
    {
        System.out.println("中心");
        middle.Output();
        System.out.println("半径："+Radiam);


    }

}
