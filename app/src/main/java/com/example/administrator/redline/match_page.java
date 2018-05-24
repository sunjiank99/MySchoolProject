package com.example.administrator.redline;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ServiceTest;
import com.example.UserInfoLogin;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.BaiduTEST.ActivityStateUitl;
import com.example.administrator.redline.fragment.GoAroundFragment;
import com.example.administrator.redline.fragment.LeftFragment;
import com.example.administrator.redline.fragment.MatchPageFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class match_page extends SlidingFragmentActivity implements
        View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
    private ImageView topButton;
    private Fragment mContent;
    private TextView topTextView;

    Thread thread;
    RegisterHandler registerHandler;
    RegisterThread registerThread;
    ACache mACache;




    public  class RegisterHandler extends Handler
    {
        //接受message的信息
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.getData().getString("operateState").equals("1"))
            {
               if(msg.getData().getString("AlterStateDownload").equals("true"))
               {
                   Toast.makeText(getBaseContext(),"下线成功",Toast.LENGTH_LONG).show();
               }
               else
               {
                   Toast.makeText(getBaseContext(),"下线失败",Toast.LENGTH_LONG).show();
               }
            }


        }
    }
    public class RegisterThread implements Runnable
    {


        int operateState=-1;
        public RegisterThread(int i)
        {
            operateState=i;
        }

        public void run()
        {
            if(operateState==1) {
                Message msg = registerHandler.obtainMessage();
                // 给消息结构的arg1参数赋值
                ServiceTest conn = new ServiceTest();  //数据库连接类

                Bundle bundle = new Bundle();   //初始化传递数据捆
                bundle.putString("operateState", "1");   //Exit_ID为是否存在当前用户名
                String id=mACache.getAsString("id");
                if (conn.UpdateUserinfo_Realtime(id,"OnlineState","false")) {
                    bundle.putString("AlterStateDownload", "true");   //修改用户下线状态成功


                } else {
                    bundle.putString("AlterStateDownload", "false");   //修改用户下线状态失败
                }

                msg.setData(bundle);    // 将bundle传递设置为传递的信息
                registerHandler.sendMessage(msg);  //将信息传递给handler
            }
        }
//        public static void main(String[] args)
//        {
//            MyThread2 myThread = new MyThread2();
//            myThread.setName("world");
//            Thread thread = new Thread(myThread);
//            thread.start();
//        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match_page);
        initSlidingMenu(savedInstanceState);
        topButton = (ImageView) findViewById(R.id.match_page_topButton);
        topButton.setOnClickListener(this);
        topTextView = (TextView) findViewById(R.id.match_page_topText);
        mACache=ACache.get(this);
        registerHandler=new RegisterHandler();
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(match_page.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    match_page.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

//        Intent intentOne = new Intent(this, RealtimeService.class);
//        startService(intentOne);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 初始化侧边栏
     */
    private void initSlidingMenu(Bundle savedInstanceState) {
        // 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment

            if (savedInstanceState != null) {

                    mContent = getSupportFragmentManager().getFragment(
                            savedInstanceState, "mContent");

            }





        if (mContent == null) {
            mContent = new MatchPageFragment();
        }

        // 设置左侧滑动菜单
        setBehindContentView(R.layout.activity_menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new LeftFragment()).commit();

        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动的菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单阴影的图像资源
        sm.setShadowDrawable(null);
        // 设置滑动菜单视图的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.35f);
        // 设置触摸屏幕的模式,这里设置为全屏
        //sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //必须从边缘开始滑动才能打开侧滑菜单
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
       //int a= mContent.getId();

      // String a= mContent.getTag().toString();


            //sm.setTouchModeAbove(SlidingMenu.LEFT);

        // 设置下方视图的在滚动时的缩放比例
        sm.setBehindScrollScale(0.0f);

    }



    /**
     * 切换Fragment
     *
     * @param fragment
     */
    public void switchConent(Fragment fragment, String title) {
        mContent = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
        getSlidingMenu().showContent();
        topTextView.setText(title);


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    public Notification.Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("match_page Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }

    @Override
    public void onStart() {
        super.onStart();


            //ActivityStateUitl.getInstance().addActivity(this);//记录avtivity的状态，最后记录程序是否在运行


        // Toast.makeText(getBaseContext(),"onstart启动",Toast.LENGTH_LONG);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
                registerThread=new RegisterThread(1);
        thread=new Thread(registerThread);
        thread.start();
       // String TAG="abcde";
        //Log.i(TAG, "onStop: ");
        //ActivityStateUitl.getInstance().exitActivity(this);
        //Toast.makeText(getBaseContext(),"onStop启动",Toast.LENGTH_LONG);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        registerThread=new RegisterThread(1);
//        thread=new Thread(registerThread);
//        thread.start();


    }
    @Override
    public void finish()
    {


        super.finish();

    }
    @Override
    public  void onDestroy()
    {


        super.onDestroy();
        //连续启动Service


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.match_page_topButton:
                toggle();
                break;
            default:
                break;
        }
    }
}
