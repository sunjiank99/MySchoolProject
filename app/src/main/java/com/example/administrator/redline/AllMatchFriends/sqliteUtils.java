package com.example.administrator.redline.AllMatchFriends;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.administrator.redline.MainActivity;
import com.example.administrator.redline.modelqq.been.Info;

import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class sqliteUtils {
    private SQLiteDatabase db;
    //private String userid;
    public sqliteUtils(String userid,String dirPath)
    {
        db=SQLiteDatabase.openOrCreateDatabase(dirPath+userid+".db",null);
        //this.userid=userid;
    }
    public void createTable(){
   //创建表SQL语句
        String stu_table="create table if not exists usertable (id INTEGER  primary key autoincrement,Userid VARCHAR(10) , Nick VARCHAR(20)  ,Sex INTEGER ,Headpic TEXT)";
   //执行SQL语句
        db.execSQL(stu_table);
    }

    public void insert(Info userInfo){
//实例化常量值
        ContentValues cValue = new ContentValues();
//添加用户名
        cValue.put("Userid",userInfo.getUserId());
        cValue.put("Nick",userInfo.getName());

        if(userInfo.getSex()==true)
        {
            cValue.put("Sex",1);
        }
        if(userInfo.getSex()==false)
        {
            cValue.put("Sex",0);
        }
        //bitmap转换成Base64
        Bitmap bitmap =userInfo.getHeadpic();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uploadString = new String(Base64.encode(baos.toByteArray()));
        cValue.put("Headpic",uploadString);

//        cValue.put("Sex",userInfo.getSex());
////添加密码
//        cValue.put("snumber","01005");
//调用insert()方法插入数据
        db.insert("usertable",null,cValue);
    }


    public int query(String id) {
//查询获得游标
        Cursor cursor = db.query("usertable",null,"Userid='"+id+"'",null,null, null,null);

        return cursor.getCount();

    }
   public List<Info> queryList() {
        List<Info> MatchInfo= new ArrayList<Info>();
      // Info MatchInfo=new Info();
//查询获得游标
        Cursor cursor = db.query("usertable",null,null,null,null, null,null);

//判断游标是否为空
        if(cursor.moveToFirst()) {
//遍历游标

            while(!cursor.isAfterLast()){
                Info item=new Info();
                //cursor.move(i);
                item.setName(cursor.getString(2));
//获得用户名
                item.setUserId(cursor.getString(1));
                if(cursor.getString(3).equals("0")) {
                    item.setSex(false);
                }
                if (cursor.getString(3).equals("1"))
                {
                    item.setSex(true);
                }
                byte[] bytes = Base64.decode(cursor.getString(4));

                Bitmap downpic = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                item.setHeadpic(downpic);
                MatchInfo.add(item);
                cursor.moveToNext();
            }

//            for(int i=0;i<cursor.getCount();i++){
//                Info item=new Info();
//                cursor.move(i);
//                item.setName(cursor.getString(2));
////获得用户名
//                item.setUserId(cursor.getString(1));
//                if(cursor.getString(3).equals("0")) {
//                    item.setSex(false);
//                }
//                if (cursor.getString(3).equals("1"))
//                {
//                    item.setSex(true);
//                }
//                byte[] bytes = Base64.decode(cursor.getString(4));
//
//                Bitmap downpic = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                item.setHeadpic(downpic);
//                MatchInfo.add(item);
//            }
        }
        return MatchInfo;
    }

    public void deleteTable(){
        db.execSQL("delete from usertable");
    }


}
