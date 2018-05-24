package com.example.administrator.redline.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ServiceTest;
import com.example.UserInfo;
import com.example.administrator.redline.AllMatchFriends.sqliteUtils;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.BaiduTEST.LocationUtils;
import com.example.administrator.redline.BaiduTEST.UserInfo_Realtime;
import com.example.administrator.redline.BaiduTESTMAP;
import com.example.administrator.redline.Main2Activity;
import com.example.administrator.redline.MainActivity;
import com.example.administrator.redline.R;
import com.example.administrator.redline.camera.ImageUtils;
import com.example.administrator.redline.modelqq.been.Info;
import com.example.administrator.redline.modelqq.custom.CustomViewPager;
import com.example.administrator.redline.modelqq.custom.RadarViewGroup;
import com.example.administrator.redline.modelqq.utils.FixedSpeedScroller;
import com.example.administrator.redline.modelqq.utils.LogUtil;
import com.example.administrator.redline.modelqq.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/11.
 */

public class GoAroundFragment extends Fragment  implements ViewPager.OnPageChangeListener, RadarViewGroup.IRadarClickListener {

    ACache mACache;
    private CustomViewPager viewPager;
    private RelativeLayout ryContainer;
    private RadarViewGroup radarViewGroup;
    private int[] mImgs = {R.drawable.len, R.drawable.leo, R.drawable.lep,
            R.drawable.leq, R.drawable.ler, R.drawable.les, R.drawable.mln, R.drawable.mmz, R.drawable.mna,
            R.drawable.mnj, R.drawable.leo, R.drawable.leq, R.drawable.les, R.drawable.lep};
    private String[] mNames = {"ImmortalZ", "唐马儒", "王尼玛", "张全蛋", "蛋花", "王大锤", "叫兽", "哆啦A梦"};
    private int mPosition;
    private FixedSpeedScroller scroller;
    private SparseArray<Info> mDatas = new SparseArray<>();
    private int LastTimeDataSize;
    //百度地图API调用区
    private final String TAG = "MainActivity";
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private static final int BAIDU_READ_PHONE_STATE =100;


    UserInfo_Realtime LocationNewinfo;//线程读取的实时地理位置

    Thread upInfoThread ;   //地理位置上传线程
    UploadMLocationThread upInfoRuanabel;//地理位置上传句柄

    //上传地理位置信息Runnable
    public class UploadMLocationThread implements Runnable {
        public void run() {


                 ServiceTest test = new ServiceTest();
                 test.UpdateRealtimeInfo(LocationNewinfo);


        }
    }


    IdPsThread idPsThread;
    IdPsHandler idPsHandler;

    private List<Info> DataList=new ArrayList <Info>() ;


    /*
     * 创建新窗口Handler
     */

    public  class IdPsHandler extends Handler
    {
        //接受message的信息
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.getData().getString("Exit_ID").equals("true"))
            {
                mDatas.clear();
                for (int i = 0; i < DataList.size(); i++) {
                    Info info = new Info();
                    info.setPortraitId(i);
                    info.setAge(DataList.get(i).getAge());
                    info.setName(DataList.get(i).getName());
                    info.setSex(DataList.get(i).getSex());
                    info.setDescribe(DataList.get(i).getDescribe());


                       LocationUtils jisuanju=new LocationUtils();

                      double juli=jisuanju.getDistance(Double.valueOf( LocationNewinfo.Latitude.toString()),Double.valueOf( LocationNewinfo.Longitude.toString()),DataList.get(i).getLatitude(),DataList.get(i).getLongitude());
                    //double juli=jisuanju.getDistance(Double.valueOf( LocationNewinfo.Latitude.toString()),Double.valueOf( LocationNewinfo.Longitude.toString()),DataList.get(i).getLatitude(),DataList.get(i).getLongitude());
                    info.setDistance(Float.valueOf(String.valueOf(juli)));

                    //info.setDistance(Math.round((Math.random() * 10) * 100) / 100);
                    info.setDistance(Math.round(( juli)));
                    Bitmap headpicC =DataList.get(i).getHeadpic();
                    headpicC=ImageUtils.toRoundBitmap(headpicC);
                    info.setHeadpic(headpicC);

                    mDatas.put(i, info);

                }

               if(mDatas.size()!=LastTimeDataSize) {
                   ryContainer.setOnTouchListener(new View.OnTouchListener() {
                       @Override
                       public boolean onTouch(View v, MotionEvent event) {
                           return viewPager.dispatchTouchEvent(event);
                       }
                   });
                   ViewpagerAdapter mAdapter = new ViewpagerAdapter();
                   viewPager.setAdapter(mAdapter);
                   //设置缓存数为展示的数目
                   //viewPager.setOffscreenPageLimit(mImgs.length);
                   viewPager.setOffscreenPageLimit(DataList.size());
                   viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
                   //设置切换动画
                   viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                   viewPager.addOnPageChangeListener(GoAroundFragment.this);
                   setViewPagerSpeed(250);
//                radarViewGroup.setDatas(mDatas);
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {

                           int len = radarViewGroup.getChildCount();
                           for (int i = 1; i < len; i++) {
                               final View child = radarViewGroup.getChildAt(1);
                               radarViewGroup.removeView(child);
                           }
                           radarViewGroup.setDatas(mDatas);
                           //radarViewGroup.requestLayout();

                           //radarViewGroup.onScanSuccess();

                           //radarViewGroup.removeAllViews();

                       }
                   }, 1500);
                   // radarViewGroup.setiRadarClickListener(GoAroundFragment.this);
//                Message msgg=new Message();
//                Bundle bundle=new Bundle();   //初始化传递数据捆
//                bundle.putString("Exit_ID", "false");
//                msgg.setData(bundle);    // 将bundle传递设置为传递的信息
//                idPsHandler.sendMessage(msgg);  //将信息传递给handler
                   LastTimeDataSize = mDatas.size();
               }
            }



        }
    }
    /*
     *
     */
    public class IdPsThread implements Runnable
    {



        public void run()
        {

                Message msg = idPsHandler.obtainMessage();
                // 给消息结构的arg1参数赋值
                ServiceTest conn = new ServiceTest();  //数据库连接类

                Bundle bundle = new Bundle();   //初始化传递数据捆

                //String []idlist={"admin","admin21","sunjian"};

                DataList = conn.GetMatchUerInfoByRealTime(LocationNewinfo);//得到匹配信息

            //更新好友列表
            String dirPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/data/";
                sqliteUtils SQLOp= new sqliteUtils(mACache.getAsString("id"),dirPath);
                int DataListLen=DataList.size();
                for(int i=0;i<DataListLen;i++) {

                    if(SQLOp.query(DataList.get(i).getUserId())==0)
                    {
                        SQLOp.insert(DataList.get(i));
                    }
                }
                if (DataList != null) {
                    bundle.putString("Exit_ID", "true");
                }

            //int len=radarViewGroup.getChildCount();

                msg.setData(bundle);    // 将bundle传递设置为传递的信息
                idPsHandler.sendMessage(msg);  //将信息传递给handler




        }

    }

    Thread GetMatchInfoThread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        if(mACache.put("personal_matchList", DataList.toArray());) {
//            mACache.put("personal_matchList", DataList.toArray());
//        }
        idPsHandler=new IdPsHandler();
        idPsThread =new IdPsThread();
        GetMatchInfoThread=new Thread(idPsThread);
        //GetMatchInfoThread.start();

        if (Build.VERSION.SDK_INT>=23){
            showContacts();
        }

        // 声明LocationClient类
        mLocationClient = new LocationClient(getContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);


        //声明地理位置实时上传线程
        upInfoRuanabel = new UploadMLocationThread();
        upInfoThread=new Thread(upInfoRuanabel);




        //savedInstanceState.putString("GoAround","true");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go_around, null);
        //initView();
        mACache=ACache.get(getContext());
        viewPager = (CustomViewPager)view. findViewById(R.id.vp);
        radarViewGroup = (RadarViewGroup) view.findViewById(R.id.radar);
        ryContainer = (RelativeLayout) view.findViewById(R.id.ry_container);

        LastTimeDataSize=0;


        getLocation();//开启设置获取地理位置
        if(DataList==null)
        {
            initData();
        }
        //initData();
//        idPsHandler=new IdPsHandler();
//        idPsThread =new IdPsThread();
//        Thread a=new Thread(idPsThread);
//        a.start();

        /**
         * 将Viewpager所在容器的事件分发交给ViewPager
         */

//        ryContainer.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return viewPager.dispatchTouchEvent(event);
//            }
//        });
//         ViewpagerAdapter mAdapter = new ViewpagerAdapter();
//        viewPager.setAdapter(mAdapter);
//        //设置缓存数为展示的数目
//        //viewPager.setOffscreenPageLimit(mImgs.length);
//        viewPager.setOffscreenPageLimit(DataList.size());
//        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
//        //设置切换动画
//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        viewPager.addOnPageChangeListener(this);
//        setViewPagerSpeed(250);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
////                //initData();
////               ViewpagerAdapter mAdapter = new ViewpagerAdapter();
////               viewPager.setAdapter(mAdapter);
////                viewPager.setOffscreenPageLimit(DataList.size());
//
//                radarViewGroup.setDatas(mDatas);
//            }
//        }, 1500);
//        radarViewGroup.setiRadarClickListener(this);


        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }



    private void initData() {


        ACache mACache=ACache.get(this.getContext());

        Bitmap bitmap=mACache.getAsBitmap("headpic");
        bitmap= ImageUtils.toRoundBitmap(bitmap);

        for (int i = 0; i < mImgs.length; i++) {
            Info info = new Info();
            info.setPortraitId(mImgs[i]);
            info.setAge(((int) Math.random() * 25 + 16) + "岁");
            info.setName(mNames[(int) (Math.random() * mNames.length)]);
            info.setSex(i % 3 == 0 ? false : true);
            info.setDistance(Math.round((Math.random() * 10) * 100) / 100);
            info.setHeadpic(bitmap);

            mDatas.put(i, info);
        }
    }

//    private void initView() {
//        viewPager = (CustomViewPager) findViewById(R.id.vp);
//        radarViewGroup = (RadarViewGroup) findViewById(R.id.radar);
//        ryContainer = (RelativeLayout) findViewById(R.id.ry_container);
//    }

    /**
     * 设置ViewPager切换速度
     *
     * @param duration
     */
    private void setViewPagerSpeed(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            scroller = new FixedSpeedScroller(getContext(), new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(duration);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
        radarViewGroup.setCurrentShowItem(position);
        LogUtil.m("当前位置 " + mPosition);
        LogUtil.m("速度 " + viewPager.getSpeed());
        //当手指左滑速度大于2000时viewpager右滑（注意是item+2）
        if (viewPager.getSpeed() < -1800) {

            viewPager.setCurrentItem(mPosition + 2);
            LogUtil.m("位置 " + mPosition);
            viewPager.setSpeed(0);
        } else if (viewPager.getSpeed() > 1800 && mPosition > 0) {
            //当手指右滑速度大于2000时viewpager左滑（注意item-1即可）
            viewPager.setCurrentItem(mPosition - 1);
            LogUtil.m("位置 " + mPosition);
            viewPager.setSpeed(0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRadarItemClick(int position) {
        viewPager.setCurrentItem(position);
    }


    class ViewpagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {




            final Info info = mDatas.get(position);
            //设置一大堆演示用的数据，麻里麻烦~~


            View view = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_layout, null);
            ImageView ivPortrait = (ImageView) view.findViewById(R.id.iv);
            ImageView ivSex = (ImageView) view.findViewById(R.id.iv_sex);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvDistance = (TextView) view.findViewById(R.id.tv_distance);
            tvName.setText(info.getName());
            tvDistance.setText(info.getDistance() + "m");
            //ivPortrait.setImageResource(info.getPortraitId());
            ivPortrait.setImageBitmap(info.getHeadpic());
            if (info.getSex()) {
                ivSex.setImageResource(R.drawable.girl);
            } else {
                ivSex.setImageResource(R.drawable.boy);
            }
            ivPortrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Toast toast= Toast.makeText(getContext(), "这是 " + info.getName() + " >.<"+"\n"+
                            "缘分匹配情况：\n"+info.getDescribe(), Toast.LENGTH_LONG);
                    showMyToast(toast, 10*1000);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {

            //return mImgs.length;
            return DataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }


    //获取定位权限

    public void showContacts(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        }
    }
    /** 获得所在位置经纬度及详细地址 */
    public void getLocation() {
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
    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息

                LocationNewinfo=new UserInfo_Realtime();
                LocationNewinfo.UserId=mACache.getAsString("id");
                LocationNewinfo.Longitude= BigDecimal.valueOf(location.getLongitude());
                LocationNewinfo.Latitude=BigDecimal.valueOf(location.getLatitude());
                LocationNewinfo.StreetInfo=location.getStreet();
                LocationNewinfo.StreetId=location.getStreetNumber();
                LocationNewinfo.ProvinceInfo=location.getProvince();
                //海拔高度大于1存入临时信息
                if(location.getAltitude()>1) {
                    LocationNewinfo.Altitude = BigDecimal.valueOf(location.getAltitude());
                }

                LocationNewinfo.BuildingInfo=location.getBuildingName();
                LocationNewinfo.BuildingId=location.getBuildingID();
                LocationNewinfo.CityInfo=location.getCity();
                LocationNewinfo.CityId=location.getCityCode();
                LocationNewinfo.CountryInfo=location.getCountry();
                LocationNewinfo.CountryId=location.getCountryCode();
                LocationNewinfo.FloorInfo= location.getFloor();
                LocationNewinfo.Speed=BigDecimal.valueOf(location.getSpeed());
                LocationNewinfo.DistrictInfo=location.getDistrict();

                //test.UpdateRealtimeInfo(newinfo);
                upInfoThread.setName("地理位置上传线程");
                upInfoThread=new Thread(upInfoRuanabel);
                upInfoThread.start();//开启地理位置上传数据库
                GetMatchInfoThread.setName("得到匹配结果线程");
                GetMatchInfoThread=new Thread(idPsThread);
                GetMatchInfoThread.start();//得到匹配结果


               // showInfo.setText(xinxi);
                mLocationClient.startIndoorMode();
                mLocationClient.start();


            }
        }
    }

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

}
