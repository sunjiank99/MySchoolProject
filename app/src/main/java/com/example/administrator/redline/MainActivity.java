package com.example.administrator.redline;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ServiceTest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;



public class MainActivity extends AppCompatActivity {

    private Button btn_add;//登入按钮
    private Button btn_login;//注册按钮
    private EditText edit_Id;
    private EditText edit_Ps;
    private TextView text_Error;
    Thread thread;
    IdPsThread idPsThread;
    IdPsHandler idPsHandler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


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
                if(msg.getData().getString("Correct_Ps").equals("true"))

                {
                    //调用新窗口
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Main2Activity.class);
                    MainActivity.this.startActivity(intent);



                   // text_Error.setText("登入成功");
                }
                else
                {
                    text_Error.setText("密码错误");
                }
            }
            else
            {
                text_Error.setText("用户名不存在");
            }

        }
    }
   /*
    *
    */
    public class IdPsThread implements Runnable
    {


        String InputID;
        String InputPs;
        public void setName(String name,String Ps)
        {
            this.InputID=name;
            this.InputPs=Ps;
        }
        public void run()
        {
            Message msg = idPsHandler.obtainMessage();
            // 给消息结构的arg1参数赋值
            ServiceTest conn=new ServiceTest();  //数据库连接类

            Bundle bundle=new Bundle();   //初始化传递数据捆

            if(conn.Exit_Id(InputID))
            {
                bundle.putString("Exit_ID", "true");   //Exit_ID为是否存在当前用户名
                if(conn.Correct_Ps(InputID,InputPs))
                {
                    bundle.putString("Correct_Ps","true");  //Correct_Ps判断用户名是否正确
                }
                else
                {
                    bundle.putString("Correct_Ps","false");
                }

            }
            else
            {
                bundle.putString("Exit_ID", "false");
            }

            msg.setData(bundle);    // 将bundle传递设置为传递的信息
            idPsHandler.sendMessage(msg);  //将信息传递给handler
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉系统默认上边标题
        setContentView(R.layout.activity_main);


        edit_Id = (EditText) findViewById(R.id.Id);
        edit_Id.getBackground().setAlpha(100);
        edit_Ps = (EditText) findViewById(R.id.Ps);
        edit_Ps.getBackground().setAlpha(100);
        btn_add = (Button) findViewById(R.id.button);
        btn_login=(Button)findViewById(R.id.btn_Login);
                text_Error = (TextView) findViewById(R.id.ErrorTip);
        idPsHandler=new IdPsHandler();

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id=edit_Id.getText().toString();
                String ps=edit_Ps.getText().toString();
                idPsThread = new IdPsThread();
                idPsThread.setName(id,ps); //传入用户名 密码
                Thread thread = new Thread(idPsThread);
                thread.start();
                //finish();



            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //调用新窗口
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, welcome_page.class);
                MainActivity.this.startActivity(intent);


            }
        });




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }
}

