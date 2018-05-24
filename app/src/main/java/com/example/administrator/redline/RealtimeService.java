package com.example.administrator.redline;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.ServiceTest;
import com.example.administrator.redline.AsimpleChache.ACache;

import java.util.List;

public class RealtimeService extends Service {

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
                while (true) {
                    if(!isAppAlive(getBaseContext(),"com.example.administrator.redline")) {
                        Toast.makeText(getBaseContext(),"下线成功",Toast.LENGTH_LONG).show();
                        Message msg = registerHandler.obtainMessage();
                        // 给消息结构的arg1参数赋值
                        ServiceTest conn = new ServiceTest();  //数据库连接类

                        Bundle bundle = new Bundle();   //初始化传递数据捆
                        bundle.putString("operateState", "1");   //Exit_ID为是否存在当前用户名
                        String id = mACache.getAsString("id");
                        if (conn.UpdateUserinfo_Realtime("admin", "OnlineState", "false")) {
                            bundle.putString("AlterStateDownload", "true");   //修改用户下线状态成功


                        } else {
                            bundle.putString("AlterStateDownload", "false");   //修改用户下线状态失败
                        }

                        msg.setData(bundle);    // 将bundle传递设置为传递的信息
                        registerHandler.sendMessage(msg);  //将信息传递给handler
                        break;
                    }
                }

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

    public RealtimeService() {

    }
    @Override
    public void onCreate()
    {
        registerThread=new RegisterThread(1);
        thread=new Thread(registerThread);
        thread.start();
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_REDELIVER_INTENT;
        return super.onStartCommand(intent, flags, startId);
    }
    private boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
