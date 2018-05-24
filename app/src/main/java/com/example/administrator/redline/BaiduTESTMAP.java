package com.example.administrator.redline;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ServiceTest;
import com.example.administrator.redline.BaiduTEST.UserInfo_Realtime;

import java.math.BigDecimal;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class BaiduTESTMAP extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private static final int BAIDU_READ_PHONE_STATE =100;
    private TextView showInfo;

    UserInfo_Realtime newinfo;

    Thread upInfoThread ;
    IdPsThread upInfoRuanabel;


    public class IdPsThread implements Runnable {


        public void run() {


                 while (true) {
                     ServiceTest test = new ServiceTest();
                     test.UpdateRealtimeInfo(newinfo);
                 }


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //requestPermissions( new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION ,ACCESS_FINE_LOCATION,ACCESS_WIFI_STATE,ACCESS_NETWORK_STATE,CHANGE_WIFI_STATE,READ_PHONE_STATE,WRITE_EXTERNAL_STORAGE,INTERNET,MOUNT_UNMOUNT_FILESYSTEMS},BAIDU_READ_PHONE_STATE );
        if (Build.VERSION.SDK_INT>=23){
            showContacts();
        }
        setContentView(R.layout.activity_baidu_testmap);
        showInfo=(TextView)findViewById(R.id.textView4) ;

        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);

        upInfoRuanabel = new IdPsThread();
        upInfoThread=new Thread(upInfoRuanabel);




    }
    public void showContacts(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        }
    }

    /** 获得所在位置经纬度及详细地址 */
    public void getLocation(View view) {
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        option.setIsNeedAltitude(true);
        option.setOpenGps(true);
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();

       // upInfoThread.start();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }

    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr();
                String CountryInfo=location.getCountry();
                String CityInfo=location.getCity();
                String DistInfo=location.getDistrict();
                String buildInfo=location.getBuildingName();
                String FLoutInfo=location.getFloor();
                String StreetInfo=location.getStreet();
                double AltitudeInfo=location.getAltitude();

                double sudu=location.getSpeed();
                Log.i(TAG, "address:" + address + " latitude:" + latitude
                        + " longitude:" + longitude + "---");
                String xinxi="address:" + address + " latitude:" + latitude
                        + " longitude:" + longitude + "---\n"+
                        "城市："+CityInfo+"\n"+
                        "区县："+DistInfo+"\n"+
                        "建筑："+buildInfo+"\n"+
                        "楼层："+FLoutInfo+"\n"+
                        "街道："+StreetInfo+"\n"+
                        "速度："+sudu+"km/h"+"\n"+
                        "高度："+AltitudeInfo+"m";
                ServiceTest test =new ServiceTest();
                newinfo=new UserInfo_Realtime();
                newinfo.UserId="admin";
                newinfo.Longitude= BigDecimal.valueOf(longitude);
                newinfo.Latitude=BigDecimal.valueOf(latitude);
                newinfo.StreetInfo=StreetInfo;
                if(AltitudeInfo>1) {
                    newinfo.Altitude = BigDecimal.valueOf(AltitudeInfo);
                }
                newinfo.StreetInfo=StreetInfo;
                newinfo.BuildingInfo=buildInfo;
                newinfo.CityInfo=CityInfo;
                newinfo.CountryInfo=CountryInfo;
                newinfo.FloorInfo= FLoutInfo;
                newinfo.Speed=BigDecimal.valueOf( sudu);
                newinfo.DistrictInfo=DistInfo;
                //test.UpdateRealtimeInfo(newinfo);
                upInfoThread.start();
                showInfo.setText(xinxi);
                mLocationClient.startIndoorMode();
                mLocationClient.start();


            }
        }
    }
}
