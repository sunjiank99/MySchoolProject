package com.example;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class Friends extends  Person{
    List<Friends> number;

    public void Add(Friends a)
    {
        number.add(a);
    }
    public void Delete(Friends a)
    {
        number.remove(a);
    }
}
