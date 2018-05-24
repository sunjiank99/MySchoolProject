package com.example;


import android.graphics.BitmapFactory;
import android.util.Log;


import com.example.administrator.redline.BaiduTEST.UserInfo_Realtime;
import com.example.administrator.redline.Chat.ChatInfoTable;
import com.example.administrator.redline.modelqq.been.Info;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.lang.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.ksoap2.SoapEnvelope.XSI;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ServiceTest {
    //public String ClientIP="172.24.63.90";
    //public static String ClientIP="172.25.182.161";
    public static String ClientIP="120.78.178.113";
    public static String ClientPort="80";

    public static void main(String[] args) {
       ServiceTest a=new ServiceTest();

      UserInfo_Extend userinfo=a.GetUserInfo_Extend("admin123");

       //List<ChatInfoTable> list=a.getChatInfoListBySendAndReceiveId("admin21","admin");


//       newinfo.UserId="admin";
//       newinfo.Longitude=BigDecimal.valueOf(1.2);
        //a.UpdateRealtimeInfo(newinfo);

     //a.GetUserInfo("admin");


//        boolean newT;
//        newT=a.UpdateUserinfo("admin","Nick","管孙健");

       // System.out.println(a.LoginUserInfo());
       // System.out.println(a.Exit_Id("sunjiank99"));
      // System.out.println(isA);


    }
/*
*检查账号是否存在
 */
    public boolean Exit_Id(String Id)
    {

        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "Exist_ID";
        // EndPoint
        String endPoint = "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/Exist_ID";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        rpc.addProperty("Id",Id);
//        rpc.addProperty("userId", "");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();

        if(result.equals("true"))
        {
            return  true;
        }
        else
        {
            return  false;
        }



    }

   public boolean Correct_Ps(String Id,String Ps)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "Correct_Ps";
        // EndPoint
        String endPoint =  "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/Correct_Ps";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        rpc.addProperty("Id",Id);
        rpc.addProperty("Ps",Ps);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();

        if(result.equals("true"))
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }

    //得到用户信息
    public     UserInfo GetUserInfo(String UserId)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "GetUserInfo";
        // EndPoint
        String endPoint = "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/GetUserInfo";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        rpc.addProperty("UserId",UserId);
       // rpc.addProperty("Ps",Ps);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        SoapObject result = (SoapObject) object.getProperty(0);



        UserInfo info =new UserInfo();

        info._id=Integer.parseInt(result.getProperty("Id").toString());
        info._image_url=result.getProperty("Image_URL").toString();
        info._ip=result.getProperty("Ip").toString();
        info._nick=result.getProperty("Nick").toString();
        info._sex=(result.getProperty("Sex")==null)?-1:Integer.parseInt(result.getProperty("Sex").toString());
        info._userpower=Integer.parseInt(result.getProperty("UserPower").toString());
        info._userid=result.getProperty("UserId").toString();
        info._location=result.getProperty("Location").toString();

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        if(result.getProperty("Birthday")!=null) {
            try {
                info._birthday = format1.parse(result.getProperty("Birthday").toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        info._email=result.getProperty("Email").toString();


       return info;

    }
    //得到用户扩展信息
    public  UserInfo_Extend GetUserInfo_Extend(String UserId)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "GetUserinfo_Extend";
        // EndPoint
        String endPoint = "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/GetUserinfo_Extend";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        rpc.addProperty("userid",UserId);
        // rpc.addProperty("Ps",Ps);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        SoapObject result = (SoapObject) object.getProperty(0);

        UserInfo_Extend info = new UserInfo_Extend();



        if(result.getPropertyCount()>0) {
            info._uerid = result.getProperty("UserId").toString();
            info._qq = result.getProperty("QQ").toString();
            info._stars = result.getProperty("Stars").toString();
            info._weibo = result.getProperty("WeiboId").toString();
        }


        return info;
    }
    //注册用户信息
   public boolean LoginUserInfo(UserInfoLogin info)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        String methodName = "LoginUserInfo";
        String soapAction = "http://"+ClientIP.toString()+"/LoginUserInfo";

        String url = "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";// 后面加不加那个?wsdl参数影响都不大

//
//        UserInfoLogin info = new UserInfoLogin();
//
//        info.setProperty(1, "admin3");
//        info.setProperty(2, "123456");
//        info.setProperty(3,"asd");
//
//        info.setProperty(5,"1");
//        info.setProperty(6,"0");



        // 建立webservice连接对象
        org.ksoap2.transport.HttpTransportSE transport = new HttpTransportSE(url);
        transport.debug = true;// 是否是调试模式

        // 设置连接参数
        SoapObject soapObject = new SoapObject(nameSpace, methodName);
        PropertyInfo objekt = new PropertyInfo();
        objekt.setName("info");
        objekt.setValue(info);
        objekt.setType(info.getClass());
        soapObject.addProperty(objekt);

        // 设置返回参数
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);// soap协议版本必须用SoapEnvelope.VER11（Soap
        // V1.1）
        envelope.dotNet = true;// 注意：这个属性是对dotnetwebservice协议的支持,如果dotnet的webservice
        // 不指定rpc方式则用true否则要用false
        envelope.bodyOut = transport;
        envelope.setOutputSoapObject(soapObject);// 设置请求参数
//      new MarshalDate().register(envelope);
        envelope.addMapping(nameSpace, "UserInfo", info.getClass());// 传对象时必须，参数namespace是webservice中指定的，
        //webservice中指定的类名

        // claszz是自定义类的类型
        try {
            transport.call(soapAction, envelope);
            // SoapObject sb = (SoapObject)envelope.bodyIn;//服务器返回的对象存在envelope的bodyIn中
            Object obj = envelope.getResponse();// 直接将返回值强制转换为已知对象
            Log.d("WebService", "返回结果：" + obj.toString());

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;

        // 解析返回的结果
        // return Boolean.parseBoolean(new AnalyzeUtil().analyze(response));
    }

    //更新用户信息
    public boolean UpdateUserinfo(String userid,String AlterIndex,String AlterContent)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "AlterUserInfo";
        // EndPoint
        String endPoint =  "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/AlterUserInfo";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        rpc.addProperty("UserId",userid);
        rpc.addProperty("AlterIndex",AlterIndex);
        rpc.addProperty("AlterContent",AlterContent);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);

        } catch (Exception e) {
            e.printStackTrace();
        }


        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();

        if(result.equals("true"))
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }

    //更新用户实时信息
    public boolean UpdateUserinfo_Realtime(String userid,String AlterIndex,String AlterContent)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "AlterUserInfo_Realtime";
        // EndPoint
        String endPoint =  "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/AlterUserInfo_Realtime";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        rpc.addProperty("UserId",userid);
        rpc.addProperty("AlterIndex",AlterIndex);
        rpc.addProperty("AlterContent",AlterContent);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);

        } catch (Exception e) {
            e.printStackTrace();
        }


        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();

        if(result.equals("true"))
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }
   //更新用户扩展信息
    public  boolean UpdateUserinfo_Extend(String userid,String AlterIndex,String AlterContent)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "AlterUserInfo_Extend";
        // EndPoint
        String endPoint =  "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/AlterUserInfo_Extend";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        rpc.addProperty("UserId",userid);
        rpc.addProperty("AlterIndex",AlterIndex);
        rpc.addProperty("AlterContent",AlterContent);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);

        } catch (Exception e) {
            e.printStackTrace();
        }


        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();

        if(result.equals("true"))
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }
   //下载头像
    public boolean UploadePic(String bytes,String FileName)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "upfilebyte";
        // EndPoint
        String endPoint =  "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/upfilebyte";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        // String uploadBuffer = new String(Base64.encode(bytes));
        rpc.addProperty("b",bytes);
        rpc.addProperty("FileName",FileName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);

        } catch (Exception e) {
            e.printStackTrace();
        }


        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();

        if(result.equals("上传成功!"))
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }

   //下载头像
    public String DownloadePic(String FileName)
    {
        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "downfilebyte";
        // EndPoint
        String endPoint =  "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/downfilebyte";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // String info="admin";
        // String uploadBuffer = new String(Base64.encode(bytes));
        //rpc.addProperty("b",bytes);
        rpc.addProperty("FileName",FileName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);

        } catch (Exception e) {
            e.printStackTrace();
        }


        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        if(object.getPropertyCount()==0)
        {
            return null;
        }
        // 获取返回的结果
        String result = object.getProperty(0).toString();





        return  result;
    }



    //得到附近的人匹配用户信息
    public List<Info> GetMatchUserInfo(String[] UserId)
    {
        List<Info>  ReturnTxt=new ArrayList<Info>();

        String nameSpace = "http://"+ClientIP.toString()+"/";
        // 调用的方法名称
        String methodName = "GetMatchUserInfoList";
        // EndPoint
        String endPoint = "http://"+ClientIP.toString()+":"+ClientPort.toString()+"/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://"+ClientIP.toString()+"/GetMatchUserInfoList";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        SoapObject rpneiCeng=new SoapObject(nameSpace, "idList");
        // String info="admin";
        for(int i=0;i<UserId.length;i++) {
            rpneiCeng.addProperty("string", UserId[i]);
        }
        rpc.addSoapObject( rpneiCeng);







        //objekt.setType(UserId.getClass());
//        Vector a=new Vector(UserInfo);
        //Vector gg=new Vector();
        //gg.addElement("admin");
       // gg.addElement("admin21");

//        soapObject.addProperty(objekt);
    //    rp.addProperty("string","admin");
        // rpc.addProperty("Ps",Ps);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        if(object.getPropertyCount()==0)
        {
             return ReturnTxt;
        }

        // 获取返回的结果
        SoapObject result = (SoapObject) object.getProperty(0);

        for(int i=0;i<result.getPropertyCount();i++) {
            SoapObject re = (SoapObject) result.getProperty(i);

            Info item =new Info();
            item.setName(re.getProperty("name").toString());
            item.setAge(re.getProperty("age").toString());
            item.setDistance(0);
            switch (Integer.parseInt(re.getProperty("age").toString()))
            {
                case 0:item.setSex(false);
                case 1:item.setSex(true);
            }

            String headpic=re.getProperty("headpic").toString();

            byte[] bytes = Base64.decode(headpic);

            double Longitude=Double.valueOf(  re.getProperty("Longitude").toString());
            double Latitude=Double.valueOf(  re.getProperty("Latitude").toString());
            item.setLongitude(Longitude);
            item.setLatitude(Latitude);
            item.setDescribe(re.getProperty("describe").toString());

            //Bitmap downpic = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            item.setHeadpic(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

            ReturnTxt.add(item);

        }

       return ReturnTxt;









        //return null;

    }


    public boolean UpdateRealtimeInfo(UserInfo_Realtime UserInfo) {
        boolean ReturnTxt ;

        String nameSpace = "http://" + ClientIP.toString() + "/";
        // 调用的方法名称
        String methodName = "UpdateRealtimeInfo";
        // EndPoint
        String endPoint = "http://" + ClientIP.toString() + ":" + ClientPort.toString() + "/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://" + ClientIP.toString() + "/UpdateRealtimeInfo";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
      //  SoapObject rpneiCeng = new SoapObject(nameSpace, "UserInfo");
        // String info="admin";

        PropertyInfo objekt = new PropertyInfo();
        objekt.setName("UserInfo");
        objekt.setValue(UserInfo);
        objekt.setType(UserInfo.getClass());





          rpc.addProperty(objekt);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        envelope.encodingStyle=XSI;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        envelope.addMapping(nameSpace, "UserInfo_Realtime", UserInfo.getClass());// 传对象时必须，参数namespace是webservice中指定的，
       // envelope.addMapping(nameSpace, "decimal", MarshalFloat.class);// 传对象时必须，参数namespace是webservice中指定的，
        (new MarshalFloat()).register(envelope); //要注册一下否则Decimal无法转换
        (new MarshalBase64()).register(envelope);


        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        if(object==null||object.getPropertyCount()==0)
        {
            return false;
        }

        // 获取返回的结果
        String result =  object.getProperty(0).toString();

        if(result.equals("true"))

        {
            return true;
        }
        return false;
    }

   //根据事实动态位置获取周围用户
    public List<Info> GetMatchUerInfoByRealTime(UserInfo_Realtime UserInfo) {
        List<Info> ReturnTxt =new ArrayList<Info>();

        String nameSpace = "http://" + ClientIP.toString() + "/";
        // 调用的方法名称
        String methodName = "GetMatchUserInfoListByRealTime";
        // EndPoint
        String endPoint = "http://" + ClientIP.toString() + ":" + ClientPort.toString() + "/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://" + ClientIP.toString() + "/GetMatchUserInfoListByRealTime";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        //  SoapObject rpneiCeng = new SoapObject(nameSpace, "UserInfo");
        // String info="admin";

        PropertyInfo objekt = new PropertyInfo();
        objekt.setName("User");
        objekt.setValue(UserInfo);
        objekt.setType(UserInfo.getClass());


        rpc.addProperty(objekt);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        envelope.encodingStyle = XSI;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        envelope.addMapping(nameSpace, "UserInfo_Realtime", UserInfo.getClass());// 传对象时必须，参数namespace是webservice中指定的，
        // envelope.addMapping(nameSpace, "decimal", MarshalFloat.class);// 传对象时必须，参数namespace是webservice中指定的，
        (new MarshalFloat()).register(envelope); //要注册一下否则Decimal无法转换
        (new MarshalBase64()).register(envelope);


        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;

        if(object==null||object.getPropertyCount()==0)
        {
            return ReturnTxt;
        }

        // 获取返回的结果
        SoapObject result = (SoapObject) object.getProperty(0);

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject re = (SoapObject) result.getProperty(i);

            Info item = new Info();
            item.setName(re.getProperty("name").toString());
            item.setAge(re.getProperty("age").toString());
            item.setDistance(Float.valueOf(re.getProperty("distance").toString()));
            switch (Integer.parseInt(re.getProperty("age").toString())) {
                case 0:
                    item.setSex(false);
                case 1:
                    item.setSex(true);
            }
            item.setUserId(re.getProperty("userid").toString());

            String headpic = re.getProperty("headpic").toString();

            byte[] bytes = Base64.decode(headpic);

            double Longitude = Double.valueOf(re.getProperty("Longitude").toString());
            double Latitude = Double.valueOf(re.getProperty("Latitude").toString());
            item.setLongitude(Longitude);
            item.setLatitude(Latitude);
            item.setDescribe(re.getProperty("describe").toString());
            //Bitmap downpic = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            item.setHeadpic(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

            ReturnTxt.add(item);

        }
        return ReturnTxt;

    }


    //根据聊天id获取聊天信息
    public List<ChatInfoTable> getChatInfoListBySendAndReceiveId(String sendId,String reveivedId) {
        List<ChatInfoTable> ReturnTxt =new ArrayList<ChatInfoTable>();

        String nameSpace = "http://" + ClientIP.toString() + "/";
        // 调用的方法名称
        String methodName = "getChatInfoListBySendAndReceiveId";
        // EndPoint
        String endPoint = "http://" + ClientIP.toString() + ":" + ClientPort.toString() + "/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://" + ClientIP.toString() + "/getChatInfoListBySendAndReceiveId";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        //  SoapObject rpneiCeng = new SoapObject(nameSpace, "UserInfo");
        // String info="admin";




        rpc.addProperty("sendId",sendId);
        rpc.addProperty("receiveId",reveivedId);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        envelope.encodingStyle = XSI;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
       // envelope.addMapping(nameSpace, "UserInfo_Realtime", UserInfo.getClass());// 传对象时必须，参数namespace是webservice中指定的，
        // envelope.addMapping(nameSpace, "decimal", MarshalFloat.class);// 传对象时必须，参数namespace是webservice中指定的，
       // (new MarshalFloat()).register(envelope); //要注册一下否则Decimal无法转换
       // (new MarshalBase64()).register(envelope);


        HttpTransportSE transport = new HttpTransportSE(endPoint);

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;


        if(object==null||object.getPropertyCount()==0) //超时直接返回空
        {
            return ReturnTxt;
        }


        // 获取返回的结果
        SoapObject result = (SoapObject) object.getProperty(0);

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject re = (SoapObject) result.getProperty(i);

            ChatInfoTable item = new ChatInfoTable ();
            item.SendId=re.getProperty("SendId").toString();
            item.ReceiveId=re.getProperty("ReceiveId").toString();

            item.ChatInfo=re.getProperty("ChatInfo").toString();

            DateFormat dat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            try {
                item.DataTime=dat.parse( re.getProperty("DataTime").toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ReturnTxt.add(item);

        }
        return ReturnTxt;

    }


    //上传聊天信息
    public boolean updateChatInfo(ChatInfoTable UserInfo) {
        boolean ReturnTxt ;

        String nameSpace = "http://" + ClientIP.toString() + "/";
        // 调用的方法名称
        String methodName = "updateChatInfo";
        // EndPoint
        String endPoint = "http://" + ClientIP.toString() + ":" + ClientPort.toString() + "/RedLine.asmx";
        // SOAP Action
        String soapAction = "http://" + ClientIP.toString() + "/updateChatInfo";
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        //  SoapObject rpneiCeng = new SoapObject(nameSpace, "UserInfo");
        // String info="admin";

        PropertyInfo objekt = new PropertyInfo();
        objekt.setName("UserInfo");
        objekt.setValue(UserInfo);
        objekt.setType(UserInfo.getClass());


        rpc.addProperty(objekt);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        //envelope.encodingStyle = XSI;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        envelope.addMapping(nameSpace, "ChatInfoTable", UserInfo.getClass());// 传对象时必须，参数namespace是webservice中指定的，
        // envelope.addMapping(nameSpace, "decimal", MarshalFloat.class);// 传对象时必须，参数namespace是webservice中指定的，
//        (new MarshalFloat()).register(envelope); //要注册一下否则Decimal无法转换
//        (new MarshalBase64()).register(envelope);
        (new MarshalDate()).register(envelope);



        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        if(object==null||object.getPropertyCount()==0)
        {
            return false;
        }

        // 获取返回的结果
       String result = object.getProperty(0).toString();

        if(result.equals("true")) {
            return true;
        }
        else
        {
            return false;
        }
    }
}







