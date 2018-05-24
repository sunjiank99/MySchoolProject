package com.example.administrator.redline;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ServiceTest;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.Chat.ChatInfoTable;
import com.example.administrator.redline.testchat.TestData;
import com.example.administrator.redline.testchat.adapter.ChatAdapter;
import com.example.administrator.redline.testchat.model.ChatModel;
import com.example.administrator.redline.testchat.model.ItemModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChatWindows extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText et;
    private TextView tvSend;
    private String content;
    //目标聊天人账号头像
    private String AUserId;
    private Bitmap AHeadPIC;
    //本人账号头像
    private String BUserId;
    private Bitmap BHeadPIC;
    ACache mAcache;

   public List<ChatInfoTable> getChatInfoList;
    Thread threadGet;
    Thread threadSend;
    GetInfoThread  getInfoThread;
    GetInfoHandler getInfoHandler;
    UpInfoThread   upInfoThread;
    UpInfoHandler upInfoHandler;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    /*
     * 创建新窗口Handler
     */

    public void setgetChatInfoList(List<ChatInfoTable> obj)
    {
        this.getChatInfoList=obj;
    }

    public  class GetInfoHandler extends Handler {
        //接受message的信息
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.getData().getString("getState").equals("true")) {

                ArrayList<ItemModel> data = new ArrayList<>();
                String []list=msg.getData().getStringArray("getChatInfoList");

                for (int i=0;i<list.length;i++) {
                    ChatModel model = new ChatModel();
                    //model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
                    model.setContent(list[i]);
                    data.add(new ItemModel(ItemModel.CHAT_A, model));

                }
                if(data.size()>0) {
                    adapter.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
    /*
     *
     */
    public class GetInfoThread implements Runnable
    {



        public void run()
        {   ServiceTest conn = new ServiceTest();  //数据库连接类
            while (true) {
                Message msg = getInfoHandler.obtainMessage();
                // 给消息结构的arg1参数赋值

                getChatInfoList=conn.getChatInfoListBySendAndReceiveId(AUserId.trim(), BUserId.trim());
                Bundle bundle = new Bundle();   //初始化传递数据捆
                //ArrayList<ChatInfoTable> newList=getChatInfoList.to
                if (getChatInfoList.size()>0) {
                    bundle.putString("getState", "true");
                    String []newList=new String[getChatInfoList.size()];
                    for(int i=0;i<getChatInfoList.size();i++)
                    {
                        newList[i]=getChatInfoList.get(i).ChatInfo;
                    }
                    bundle.putStringArray("getChatInfoList",newList);


                } else {
                    bundle.putString("getState", "false");

                }

                msg.setData(bundle);    // 将bundle传递设置为传递的信息
                getInfoHandler.sendMessage(msg);  //将信息传递给handler
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public  class  UpInfoHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.getData().getString("upState").equals("true")) {

               Toast.makeText(getBaseContext(),"发送成功",Toast.LENGTH_LONG).show();
            }
        }
    }
    public class UpInfoThread implements Runnable
    {



        public void run()
        {

                Message msg = upInfoHandler.obtainMessage();
                // 给消息结构的arg1参数赋值
                ChatInfoTable upInfo=new ChatInfoTable();
                upInfo.SendId=BUserId;
                upInfo.ReceiveId= AUserId;
                upInfo.ChatInfo=content;
                Date d=new Date();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+08:00"));
                SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat1.setTimeZone(TimeZone.getTimeZone("UTC+08:00"));
                try {
                    upInfo.DataTime=dateFormat1.parse( dateFormat.format(d));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                upInfo.IsReceive=false;


                ServiceTest conn = new ServiceTest();  //数据库连接类
                boolean upState=conn.updateChatInfo(upInfo);
                Bundle bundle = new Bundle();   //初始化传递数据捆

                if (upState) {
                    bundle.putString("upState", "true");
                } else {
                    bundle.putString("upState", "false");

                }

                msg.setData(bundle);    // 将bundle传递设置为传递的信息
                upInfoHandler.sendMessage(msg);  //将信息传递给handler

            }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAcache=ACache.get(this);
        BUserId=getIntent().getStringExtra("BUserId");
        BHeadPIC=mAcache.getAsBitmap("headpic");
        AUserId=getIntent().getStringExtra("AUserId");
        AHeadPIC=mAcache.getAsBitmap("AUserIdPic");

        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        et = (EditText) findViewById(R.id.et);
        tvSend = (TextView) findViewById(R.id.tvSend);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new ChatAdapter(AHeadPIC,BHeadPIC));
  //      adapter.replaceAll(TestData.getTestAdData());
        getInfoHandler=new GetInfoHandler();
        getInfoThread=new GetInfoThread();
        threadGet=new Thread(getInfoThread);
        threadGet.start(); //开启获取信息线程

        upInfoThread=new UpInfoThread();
        upInfoHandler= new UpInfoHandler();

        threadSend=new Thread(upInfoThread);

        initData();
    }

    private void initData() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //threadSend.stop();
                threadSend=new Thread(upInfoThread);
                threadSend.start();//开启发送信息线程
                ArrayList<ItemModel> data = new ArrayList<>();
                ChatModel model = new ChatModel();
                model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
                model.setContent(content);
                data.add(new ItemModel(ItemModel.CHAT_B, model));
                adapter.addAll(data);

                et.setText("");
                hideKeyBorad(et);
            }
        });

    }

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
}
