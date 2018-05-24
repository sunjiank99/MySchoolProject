package com.example.administrator.redline.BaiduTEST;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class ActivityStateUitl {
    String TAG = "abcde";

    private static Boolean isLine = false;//true 表示登录用户正在使用app，false登录用户已下线再上线，
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ActivityStateUitl instance;

    private ActivityStateUitl() {
    }

    // 单例模式中获取唯一的MyApplication实例
    public static ActivityStateUitl getInstance() {
        if (null == instance) {
            instance = new ActivityStateUitl();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
        Log.i("loginstatus", "addActivity: ");
        if (!isLine) {//此时用户已经下线，才可以再次上线，已上线则不可再次上线
            try {
//                db=new DbOperatorLogin(activity);
//                endUserId=db.getLoginEndUserInfo().getEndUserId();//此处是获取等咯用户id
//                Log.e(TAG, "addActivity: "+"用户上线");
//                new LoginStatusToHttp().userUpLine(endUserId);//设置用户上线，调用上线用户请求
                isLine = true;//上线成功后，设置true
            } catch (Exception e) {
                Log.i(TAG, "addActivity: 抛出异常");
            }
        }
    }

    // DbOperatorLogin db;
    String endUserId;

    //移除该activity，并且30秒后检查集合是否为空，为空则表示退出程序
    public void exitActivity(final Activity activity) {
        Log.i(TAG, "exit:某activity " + activity);
        activityList.remove(activity);
        //db=new DbOperatorLogin(activity);
        try {
            //endUserId=db.getLoginEndUserInfo().getEndUserId();
            if (endUserId == null) {
                return;
            }
        } catch (Exception e) {
            Log.i(TAG, "exitActivity: " + e.toString());
        }
        new Thread() {//程序走到这里，说明已有用户登录，故此需要检查activity的stop的时候，是否是用户下线
            @Override
            public void run() {
                super.run();
                try {
                    sleep(30000);//等待三十秒，检测是否有新的activity添加进来
                    checkActivity(endUserId);
                    instance = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //检查用户是否下线，是关闭则通知网络
    public void checkActivity(String endUserId) {
        if (isLine) {//用户在线时候，才有下线，如果用户已经下线，则不会再次下线
            if (activityList.size() > 0) {
                Log.i(TAG, "checkActivity: " + "程序正在运行");
            } else {
                Log.e(TAG, "checkActivity: " + "程序已关闭,用户下线");
                //LoginStatusToHttp.userUnderLine(endUserId);//调用下线网络请求
                isLine = false;//表示用户已经下线
            }
        } else {
            Log.i(TAG, "checkActivity: " + "无用户在线，不可下线");
        }
    }
}
