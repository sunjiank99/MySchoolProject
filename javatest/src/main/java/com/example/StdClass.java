package com.example;

/**
 * Created by Administrator on 2017/10/20.
 */

public class StdClass {
    String className;
    int pupolation;
    Student []number=null;

    public StdClass(int Num)
    {
        pupolation=Num;
        number=new Student[pupolation];

    }

    public int getTotalScore()
    {   int sum=0;
        for(int i=0;i<pupolation;i++)
        {
            sum=number[i].getScore()+pupolation;
        }
        return sum;
    }

    public float getAverage()
    {
        return getTotalScore()/pupolation;
    }

    public Student getBestStudent()
    {   int flag=0;
        int score=number[0].getScore();
        for(int i=0;i<pupolation;i++)
        {
            if(score<number[i].getScore())
            {
                flag=i;
            }
        }
        return  number[flag];

    }

    public Student getWorstStudent()
    {   int flag=0;
        int score=number[0].getScore();
        for(int i=0;i<pupolation;i++)
        {
            if(score>number[i].getScore())
            {
                flag=i;
            }
        }
        return  number[flag];
    }

}
