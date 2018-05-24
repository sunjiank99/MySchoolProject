package com.example.administrator.redline.Chat;

import com.example.ServiceTest;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class ChatInfoTable implements KvmSerializable {

    public String SendId;
    public String ReceiveId;
    public String ChatInfo;
    public Date DataTime;
    public boolean IsReceive;
    public int id;



    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return SendId;
            case 1:
                return ReceiveId;
            case 2:
                return ChatInfo;
            case 3:
                return DataTime;
            case 4:
                return IsReceive;
            case 5:
                return id;
            default:
                break;
        }
        return null;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        arg2.namespace="http://"+ ServiceTest.ClientIP+"/";
        switch (arg0) {
            case 0:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "SendId";
                break;
            case 1:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "ReceiveId";
                break;
            case 2:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "ChatInfo";
                break;
            case 3:
                arg2.type = MarshalDate.DATE_CLASS;
                arg2.name = "DataTime";
                break;
            case 4:
                arg2.type = PropertyInfo.BOOLEAN_CLASS;
                arg2.name = "IsReceive";
                break;
            case 5:
                arg2.type = PropertyInfo.INTEGER_CLASS;
                arg2.name = "id";
                break;

            default:
                break;
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        switch (arg0) {
            case 0:
                SendId = arg1.toString();
                break;
            case 1:
                ReceiveId = arg1.toString();
                break;
            case 2:
                ChatInfo= arg1.toString();
                break;
            case 3:
                SimpleDateFormat format1 = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.fff");
                try {
                    DataTime=format1.parse(arg1.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                IsReceive=Boolean.valueOf( arg1.toString());
                break;
            case 5:
                id=Integer.valueOf( arg1.toString());
                break;

            default:
                break;
        }
    }

    @Override
    public int getPropertyCount() {
        return 6;
    }
}
