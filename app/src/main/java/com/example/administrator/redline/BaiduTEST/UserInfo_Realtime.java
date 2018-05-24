package com.example.administrator.redline.BaiduTEST;

import com.example.ServiceTest;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

public class UserInfo_Realtime implements KvmSerializable {
    public String UserId="";
    //<UserId>string</UserId>
    //        <Latitude>decimal</Latitude>
    //        <Longitude>decimal</Longitude>
    //        <CountryId>string</CountryId>
    //        <CountryInfo>string</CountryInfo>
    //        <ProvinceInfo>string</ProvinceInfo>
    //        <CityId>string</CityId>
    //        <CityInfo>string</CityInfo>
    //        <DistrictInfo>string</DistrictInfo>
    //        <FloorInfo>string</FloorInfo>
    //        <Speed>decimal</Speed>
    //        <Altitude>decimal</Altitude>
    //        <BuildingId>string</BuildingId>
    //        <BuildingInfo>string</BuildingInfo>
    //        <StreetId>string</StreetId>
    //        <StreetInfo>string</StreetInfo>
    public BigDecimal Latitude=BigDecimal.ZERO;
    public BigDecimal Longitude=BigDecimal.ZERO;
    public String CountryId="";
    public String CountryInfo="";
    public String ProvinceInfo="";
    public String CityId="";
    public String CityInfo="";
    public String DistrictInfo="";
    public String FloorInfo="";
    public BigDecimal Speed=BigDecimal.ZERO;
    public BigDecimal Altitude=BigDecimal.ZERO;
    public String BuildingId="";
    public String BuildingInfo="";
    public String StreetId="";
    public String StreetInfo="";
    public boolean OnlineState=true;


    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return UserId;
            case 1:
                return Latitude;
            case 2:
                return Longitude;
            case 3:
                return CountryId;
            case 4:
                return CountryInfo;
            case 5:
                return ProvinceInfo;
            case 6:
                return CityId;
            case 7:
                return CityInfo;
            case 8:
                return DistrictInfo;
            case 9:
                return FloorInfo;
            case 10:
                return Speed;
            case 11:
                return Altitude;
            case 12:
                return BuildingId;
            case 13:
                return BuildingInfo;
            case 14:
                return StreetId;
            case 15:
                return StreetInfo;
            case 16:
                return OnlineState;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 17;
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        switch (arg0) {
            case 0:
                UserId = arg1.toString();
                break;
            case 1:
                Latitude = BigDecimal.valueOf(Double.valueOf(arg1.toString()));
                break;
            case 2:
                Longitude= BigDecimal.valueOf(Double.valueOf(arg1.toString()));
                break;
            case 3:
                CountryId= arg1.toString();
                break;
            case 4:
                CountryInfo=arg1.toString();
                break;
            case 5:
                ProvinceInfo=arg1.toString();
                break;
            case 6:
                CityId=arg1.toString();
                break;
            case 7:
                CityInfo=arg1.toString();
                break;
            case 8:
                DistrictInfo=arg1.toString();
                break;
            case 9:
                FloorInfo=arg1.toString();
                break;
            case 10:
                Speed=BigDecimal.valueOf(Double.valueOf(arg1.toString()));
                break;
            case 11:
                Altitude=BigDecimal.valueOf(Double.valueOf(arg1.toString()));
                break;
            case 12:
                BuildingId=arg1.toString();
                break;
            case 13:
                BuildingInfo=arg1.toString();
                break;
            case 14:
                StreetId=arg1.toString();
                break;
            case 15:
                StreetInfo=arg1.toString();
                break;
            case 16:
                OnlineState=(boolean)arg1;
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        arg2.namespace="http://"+ ServiceTest.ClientIP+"/";
        switch (arg0) {
            case 0:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "UserId";
                break;
            case 1:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "Latitude";
                break;
            case 2:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "Longitude";
                break;
            case 3:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "CountryId";
                break;
            case 4:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "CountryInfo";
                break;
            case 5:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "ProvinceInfo";
                break;
            case 6:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "CityId";
                break;
            case 7:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "CityInfo";
                break;
            case 8:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "DistrictInfo";
                break;
            case 9:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="FloorInfo";
                break;
            case 10:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="Speed";
                break;
            case 11:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="Altitude";
                break;
            case 12:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="BuildingId";
                break;
            case 13:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="BuildingInfo";
                break;
            case 14:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="StreetId";
                break;
            case 15:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name ="StreetInfo";
                break;
            case 16:
                arg2.type=PropertyInfo.BOOLEAN_CLASS;
                arg2.name="OnlineState";

            default:
                break;
        }
    }
}
