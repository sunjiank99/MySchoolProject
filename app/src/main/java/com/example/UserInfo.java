package com.example;

import org.ksoap2.serialization.KvmSerializable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/14.
 * 这是用户信息类
 */
public class UserInfo implements Serializable {


    public int _id;
    public String _userid;
    public String _ps;
    public String _nick;
    public String _image_url;
    public int _userpower;
    public int _sex;
    public String _ip;
    public String _location;
    public Date _birthday;
    public String _email;
    public String _weibo;
    public String _qq;
    public String _stars;


}
