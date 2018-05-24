
package com.example.administrator.redline;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.Client;
import com.example.ServiceTest;
import com.example.UserInfoLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Main2Activity extends AppCompatActivity {
     private Button btn;   //消息发送按钮
     private TextView TextUserId;
     private TextView TextPs;
     private TextView TextNick;
     private TextView TextError;
     private EditText EditTextUserId;
     private EditText EditTextPs;
     private EditText EditNick;
     private RadioGroup SexGroupe;
     private RadioButton RadioBtn;
     Thread thread;
      RegisterHandler registerHandler;
      RegisterThread registerThread;




    public  class RegisterHandler extends Handler
    {
        //接受message的信息
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.getData().getString("Exit_ID").equals("true"))
            {
                    TextError.setText("该用户名已经存在");
            }
            else
            {
                    if(msg.getData().getString("Register_Success").equals("true"))
                    {
                        TextError.setText("注册成功");
                    }
                    else
                    {
                        TextError.setText("注册失败");
                    }
            }

        }
    }
    public class RegisterThread implements Runnable
    {
        UserInfoLogin registerInfo;


        public  void SetInfo(UserInfoLogin Info)
        {
            this.registerInfo=Info;
        }
        public void run()
        {
            Message msg = registerHandler.obtainMessage();
            // 给消息结构的arg1参数赋值
            ServiceTest conn=new ServiceTest();  //数据库连接类

            Bundle bundle=new Bundle();   //初始化传递数据捆

            if(conn.Exit_Id(registerInfo.UserId))
            {
                bundle.putString("Exit_ID", "true");   //Exit_ID为是否存在当前用户名


            }
            else
            {
                bundle.putString("Exit_ID", "false");
                if(conn.LoginUserInfo(registerInfo))
                {
                    bundle.putString("Register_Success","true");
                }
                else
                {
                    bundle.putString("Register_Success","false");
                }
            }

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉系统默认上边标题
//        Intent intent=getIntent();
        setContentView(R.layout.activity_main2);

        EditTextUserId=(EditText)findViewById(R.id.editTextUserid);
        EditTextPs=(EditText)findViewById(R.id.editTextPs);
        EditNick=(EditText)findViewById(R.id.editTextNick);
        SexGroupe=(RadioGroup)findViewById(R.id.SexGroupe);

        RadioBtn=(RadioButton)findViewById(SexGroupe.getCheckedRadioButtonId());
        TextError=(TextView)findViewById(R.id.Error_View);
        btn=(Button)findViewById(R.id.buttonCertain);
        registerHandler=new RegisterHandler();

        btn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                                  UserInfoLogin info=new UserInfoLogin();
                                  info.setProperty(1,EditTextUserId.getText().toString());
                                  info.setProperty(2,EditTextPs.getText().toString());
                                  info.setProperty(3,EditNick.getText().toString());
                                  info.setProperty(5,1);
                                  if(RadioBtn.getText().equals("男"))
                                  {
                                      info.setProperty(6,0);
                                  }
                                  else
                                  {
                                      info.setProperty(6,1);
                                  }
                                registerThread=new RegisterThread();
                                registerThread.SetInfo(info);
                                Thread a=new Thread(registerThread);

                               a.start();
                    }
                }


        );


//        btn = (Button) findViewById(R.id.buttonfasong);  //消息发送按钮
//        ChatContent=(TextView)findViewById(R.id.textView) ;
//        FasongContext=(EditText)findViewById(R.id.editText);
//        myBtn=(Button)findViewById(R.id.MyButton);
//        newText=(TextView) findViewById(R.id.textView2);

//        myBtn.setOnClickListener(new View.OnClickListener() {
//                                     public void onClick(View v) {
//
//                                                           newText.setText("HellO World");
//                                     }
//                                 });

//        btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO 自动生成的方法存根
//                //向本机的5469端口发出客户请求
//                Socket socket= null;
//                try {
//                    socket = new Socket("172.24.11.208",5469);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //由Socket对象得到输入流，并构造相应的BufferedReader对象
//                BufferedReader is= null;
//                try {
//                    is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //由Socket对象得到输出流，并构造PrintWriter对象
//                PrintWriter os= null;
//                try {
//                    os = new PrintWriter(socket.getOutputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //由系统标准输入设备构造BufferedReader对象
////                BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
//                while(true){
////                    String str=sin.readLine();//从系统标准输入读入一字符串
//                    String str=FasongContext.getText().toString();
//                    os.println(str);
//                    os.flush(); //刷新输出流，使Server马上收到该字符串
//
//                    String s= null;
//                    try {
//                        s = is.readLine();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    System.out.println("Server : "+s);//在标准输出上打印从Server读入的字符串
//                    ChatContent.setText("Server : "+s);
//                    if(str.equals("end")){
//                        break;
//                    }
//                }
//                try {
//                    is.close();//关闭Socket输入流
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                os.close();//关闭Socket输出流
//                try {
//                    socket.close();//关闭Socket
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }
}
