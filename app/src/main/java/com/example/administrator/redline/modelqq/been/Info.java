package com.example.administrator.redline.modelqq.been;

import android.graphics.Bitmap;

/**
 * Created by Mr_immortalZ on 2016/5/3.
 * email : mr_immortalz@qq.com
 */
public class Info {
    private int portraitId;//头像id
    private String name;//名字
    private String age;//年龄
    private boolean sex;//false为男，true为女
    private float distance;//距离
    private Bitmap headpic;//头像图片
    private double Latitude;//纬度
    private double Longitude;//精度
    private String userId;//用户名
    private String describe;//描述

    public int getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(int portraitId) {
        this.portraitId = portraitId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setHeadpic(Bitmap headpic)
    {
        this.headpic=headpic;
    }
    public Bitmap getHeadpic()
    {
        return headpic;
    }

    public void setLatitude(double latitude)
    {
        this.Latitude=latitude;
    }
    public double getLatitude()
    {
        return Latitude;
    }
    public void  setLongitude(double longitude)
    {
        this.Longitude=longitude;
    }
    public  double getLongitude()
    {
        return  Longitude;
    }

    public void setUserId(String userId)
    {
        this.userId=userId;
    }
    public String getUserId()
    {
        return userId;
    }

    public void setDescribe(String describe)
    {
        this.describe=describe;
    }
    public String getDescribe()
    {
        return describe;
    }
}
