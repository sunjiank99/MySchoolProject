package com.example;

/**
 * Created by Administrator on 2017/10/20.
 */

public class MyDate {
    private int  year;
    private int  month;
    private int  day;

    public  MyDate(int year,int month,int day)
    {
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public void show()
    {
        System.out.println(year+"-"
        +month+"-"+day);
    }

    public int getTimeDistance(MyDate a,MyDate b)
    {
        int yeard;
        int monthd;
        int dayd;

        yeard=a.year-b.year;

        if(a.month==b.month)
        {
            monthd=a.day-b.day;
        }
        else
        {
            monthd=(a.day-b.day)*30
        }

        dayd=a.day-b.day;

        return yeard+monthd+dayd;


    }


}
