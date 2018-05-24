package com.example;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/10/9.
 */
public class GetMaxSonListTest {

    @Test
    public void testOutPut() throws Exception {
            int [] testArray={5,10,-9,-3,1,0,-5,10};
           GetMaxSonList test=new GetMaxSonList(testArray);
        test.Get();//进行最大子数组计算
        test.OutPut();//输出


    }
}