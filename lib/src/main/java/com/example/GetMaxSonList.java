package com.example;

/**
 * Created by Administrator on 2016/10/8.
 */
public class GetMaxSonList {
    public int []testList;
    int firstFlag=0;  //最大子数组起始下标
    int lastFlag=0;   //最大子数组结束下标


    int MAXSUM=0;           //已有最大子数组之和

    public  GetMaxSonList(int []cache)
    {
        testList=cache;
    }
    public void  Get()
    {         int MAXSUMCache=0;     //最大子数组之和缓存
              for(int i=0;i<testList.length;i++)
              {
                   for(int j=i;j<testList.length;j++)
                   {
                        MAXSUMCache+=testList[j];// 结束下标从起始下标开始，逐渐累加求和，计入缓存
                        if(MAXSUMCache>MAXSUM)   //缓存之和大于已有最大子数组之和
                       {
                           //记录下标
                           firstFlag = i;
                           lastFlag = j;
                           MAXSUM = MAXSUMCache; //更新已有最大子数组之和
                       }

                   }
                  MAXSUMCache=0;//缓存清零进行下次循环

              }

    }

    public void OutPut()
    {
        for(int i=firstFlag;i<=lastFlag;i++)
        {
            System.out.print(testList[i]+",");


        }
        System.out.println("The Maxsum of sonlist is "+MAXSUM);

    }










}
