package com.example;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by Administrator on 2016/12/7.
 */

public class UserInfoLogin implements KvmSerializable {
    /*
    *主键
     */
    public int Id;
    /**
     * 用户名
     */
    public String UserId;

    /**
     * 密码
     */
    public String Ps;
    /**
     *  昵称
     */
    public String Nick;
    /*
    * 头像图片地址
     */
    public String Image_URL;
    /*
    * 权限
     */

    public int UserPower;
    /*
    *性别
     */
    public int Sex;
    /*
    *Ip地址
     */
    public String Ip;
   /*
   *坐标
    */
    public String Location;

    public Date Birthday;

    public String Email;

    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return Id;
            case 1:
                return UserId;
            case 2:
                return Ps;
            case 3:
                return Nick;
            case 4:
                return Image_URL;
            case 5:
                return UserPower;
            case 6:
                return Sex;
            case 7:
                return Ip;
            case 8:
                return Location;
            case 9:
                return Birthday;
            case 10:
                return Email;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 11;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        arg2.namespace="http://"+ServiceTest.ClientIP+"/";
        switch (arg0) {
            case 0:
                arg2.type = PropertyInfo.INTEGER_CLASS;
                arg2.name = "Id";
                break;
            case 1:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "UserId";
                break;
            case 2:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "Ps";
                break;
            case 3:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "Nick";
                break;
            case 4:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "Image_URL";
                break;
            case 5:
                arg2.type = PropertyInfo.INTEGER_CLASS;
                arg2.name = "UserPower";
                break;
            case 6:
                arg2.type = PropertyInfo.INTEGER_CLASS;
                arg2.name = "Sex";
                break;
            case 7:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "Ip";
                break;
            case 8:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "Location";
                break;
            case 9:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="Birthday";
                break;
            case 10:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="Email";
                break;
            default:
                break;
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        switch (arg0) {
            case 0:
                Id = Integer.parseInt(arg1.toString());
                break;
            case 1:
                UserId = arg1.toString();
                break;
            case 2:
                Ps=  arg1.toString();
                break;
            case 3:
                Nick= arg1.toString();
                break;
            case 4:
                Image_URL=arg1.toString();
                break;
            case 5:
                UserPower=Integer.parseInt(arg1.toString());
                break;
            case 6:
                Sex=Integer.parseInt(arg1.toString());
                break;
            case 7:
                Ip=arg1.toString();
                break;
            case 8:
                Location=arg1.toString();
                break;
            case 9:
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Birthday=format1.parse(arg1.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 10:
                Email=arg1.toString();
                break;
            default:
                break;
        }

    }
}
