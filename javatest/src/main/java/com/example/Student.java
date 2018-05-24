package com.example;


/**
 * Created by Administrator on 2017/10/20.
 */

public class Student {
   private String name;
    private String Id;
    private Integer score;

    public Student(String  name,String Id,int score)
    {
              this.Id=Id;
              this.name=name;
              this.score=score;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setId(String id)
    {
        this.Id=id;

    }
    public void setScore(int score)
    {


        this.score=score;
    }

    public String getName()
    {
        return name;
    }
    public String getId()
    {
        return  Id;
    }
    public int getScore()
    {
        return  score;
    }





}
