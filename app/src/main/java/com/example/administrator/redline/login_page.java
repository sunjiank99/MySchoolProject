package com.example.administrator.redline;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ServiceTest;
import com.example.UserInfo;
import com.example.UserInfoLogin;
import com.example.UserInfo_Extend;
import com.example.administrator.redline.AllMatchFriends.sqliteUtils;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.modelqq.been.Info;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class login_page extends AppCompatActivity {

    private ACache mAcache;
    private String cache_id_state;
    //注册登录页面 顶部按钮
    private Button btn_login; //登录按钮
    private Button btn_rigister;//注册按钮

    //注册登录页面 底部按钮，实际提交信息的按钮

    private  Button real_btn_login;
    private  Button real_btn_rigister;

    private RelativeLayout page_login;//登录区
    private RelativeLayout page_rigister;//注册区
    //注册区
    private EditText txt_mail; //邮箱
    private EditText txt_userid;
    private EditText txt_userps;
    private EditText txt_userCertainPs;
    private EditText txt_usernick;
    private Switch state_getNews;

    //登录区
    private EditText txt_login_UserId;
    private EditText txt_login_UserPs;

    String downloadPic;
   //注册线程
    Thread thread;
    RegisterHandler registerHandler;//注册信息反馈更新ui句柄
    RegisterThread registerThread; //获取注册信息

    public  class RegisterHandler extends Handler
    {
        //接受message的信息
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //注册完成ui更新
            if(msg.getData().getString("requestId").equals("0")) {
                if (msg.getData().getString("Exit_ID").equals("true")) {

                    //txt_mail.setText("该用户名已经存在");
                    Toast.makeText(getBaseContext(),"该用户名已经存在",Toast.LENGTH_LONG).show();
                } else {
                    if (msg.getData().getString("Register_Success").equals("true")) {

                       // txt_mail.setText("注册成功");


                        Toast.makeText(getBaseContext(),"注册成功",Toast.LENGTH_LONG).show();
                    } else {


                        Toast.makeText(getBaseContext(),"注册失败",Toast.LENGTH_LONG).show();
                    }
                }
            }
           //登录完成UI更新
            if(msg.getData().getString("requestId").equals("1"))
            {
                if(msg.getData().getString("Login_State").equals("true"))
                {
                    Intent intent = new Intent();
                    intent.setClass(login_page.this, match_page.class);
                    login_page.this.startActivity(intent);
                    login_page.this.finish();

                }
                else
                {
                    //txt_login_UserId.setText("登录失败");
                    Toast.makeText(getBaseContext(),"登录失败",Toast.LENGTH_LONG).show();
                }
            }

        }
    }
    public class RegisterThread implements Runnable
    {
        UserInfoLogin registerInfo;
        int requestId=-1;

        public  void SetInfo(UserInfoLogin Info,int requestId)
        {
            this.registerInfo=Info;
            this.requestId=requestId;
        }
        public void run()
        {
            Bundle bundle = new Bundle();   //初始化传递数据捆

            //requestId=0:注册请求
           if(requestId==0) {
               // 给消息结构的arg1参数赋值
               ServiceTest conn = new ServiceTest();  //数据库连接类
               bundle.putString("requestId","0");


               if (conn.Exit_Id(registerInfo.UserId)) {
                   bundle.putString("Exit_ID", "true");   //Exit_ID为是否存在当前用户名


               } else {
                   bundle.putString("Exit_ID", "false");
                   if (conn.LoginUserInfo(registerInfo)) {
                       bundle.putString("Register_Success", "true");
                   } else {
                       bundle.putString("Register_Success", "false");
                   }
               }
           }
           //登录请求
           if(requestId==1)
           {   bundle.putString("requestId", "1");

              //先判断缓存，然后请求数据
               if(cache_id_state.equals("false")) {

                   ServiceTest conn = new ServiceTest();  //网络数据库连接类

                   if (conn.Correct_Ps(registerInfo.UserId, registerInfo.Ps)) {
                       bundle.putString("Login_State", "true");

                       mAcache.put("id_state","true"); //缓存登录状态
                       mAcache.put("id",registerInfo.UserId);//缓存登录账号
                       UserInfo userinfo=conn.GetUserInfo(registerInfo.UserId);
                       UserInfo_Extend userInfo_Extend=conn.GetUserInfo_Extend(registerInfo.UserId);

                       userinfo._stars=userInfo_Extend._stars;
                       userinfo._weibo=userInfo_Extend._weibo;
                       userinfo._qq=userinfo._qq;
                       mAcache.put(registerInfo.UserId,userinfo); //缓存用户数据





                       downloadPic=conn.DownloadePic(registerInfo.UserId);  //下载头像
                       String dirPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/data/";
                       File dir=new File(dirPath);
                       if(!dir.exists())
                           dir.mkdirs();
                       sqliteUtils sqlop=new sqliteUtils(registerInfo.UserId,dirPath);
                       sqlop.createTable();//创建表

                       if(downloadPic!=null) {
                           byte[] bytes = Base64.decode(downloadPic);

                           Bitmap downpic = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                           mAcache.put("headpic", downpic);//缓存头像
                       }

                   } else {
                       bundle.putString("Login_State", "false");
                   }
               }
               else {
                   bundle.putString("Login_State", "true");
               }

           }

            Message msg = registerHandler.obtainMessage();
            msg.setData(bundle);    // 将bundle传递设置为传递的信息
            registerHandler.sendMessage(msg);  //将信息传递给handler
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btn_login=(Button)findViewById(R.id.login_btn_login);
        btn_rigister=(Button)findViewById(R.id.login_btn_register);
        page_login=(RelativeLayout)findViewById(R.id.login_page_login);
        page_rigister=(RelativeLayout)findViewById(R.id.login_page_register);
        txt_mail=(EditText) findViewById(R.id.login_page_edt_email);
        txt_userid=(EditText) findViewById(R.id.login_page_edt_id);
        txt_userps=(EditText)findViewById(R.id.login_page_edt_ps);
        txt_userCertainPs=(EditText)findViewById(R.id.login_page_edt_confirm_ps);
        txt_usernick=(EditText) findViewById(R.id.login_page_edt_nick);
        state_getNews=(Switch)findViewById(R.id.switch1);

        txt_login_UserId=(EditText)findViewById(R.id.login_id_editText) ;
        txt_login_UserPs=(EditText)findViewById(R.id.login_ps_editText);

        //注册登录
        real_btn_rigister=(Button)findViewById(R.id.login_page_btn_singup);
        real_btn_login=(Button)findViewById(R.id.login_btn_login_real);


        btn_login.setActivated(true);
        mAcache=ACache.get(this);
        cache_id_state=mAcache.getAsString("id_state");
        if(cache_id_state==null)
        {
            cache_id_state="false";
        }

        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(login_page.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    login_page.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        //顶部登录按钮监听
        btn_login.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                           page_login.setVisibility(View.VISIBLE);
                           page_rigister.setVisibility(View.GONE);
                           btn_login.setActivated(true);
                           btn_rigister.setActivated(false);

                    }
                }
        );
        //顶部注册按钮监听
        btn_rigister.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        page_login.setVisibility(View.GONE);
                        page_rigister.setVisibility(View.VISIBLE);
                        btn_rigister.setActivated(true);
                        btn_login.setActivated(false);


                    }
                }
        );

        //注册按钮监听
        real_btn_rigister.setOnClickListener(

                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        UserInfoLogin userinfo=new UserInfoLogin();
                        userinfo.setProperty(1,txt_userid.getText().toString().trim());
                        userinfo.setProperty(2,txt_userps.getText().toString().trim());
                        userinfo.setProperty(3,txt_usernick.getText().toString().trim());
                        userinfo.setProperty(10,txt_mail.getText().toString().trim());
                        registerThread=new RegisterThread();
                        registerHandler=new RegisterHandler();
                        registerThread.SetInfo(userinfo,0);

                        Thread a=new Thread(registerThread);
                        a.setName("注册账号进程");
                        a.start();



                    }
                }

        );

        //登录监听按钮
        real_btn_login.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        UserInfoLogin userinfo=new UserInfoLogin();
                        userinfo.setProperty(1,txt_login_UserId.getText().toString().trim());
                        userinfo.setProperty(2,txt_login_UserPs.getText().toString().trim());

                        registerThread=new RegisterThread();
                        registerHandler=new RegisterHandler();
                        registerThread.SetInfo(userinfo,1);

                        Thread a=new Thread(registerThread);
                        a.setName("登录进程");
                        a.start();


                    }
                }
        );


    }


}
