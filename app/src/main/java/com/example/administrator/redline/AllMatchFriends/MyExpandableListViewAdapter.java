package com.example.administrator.redline.AllMatchFriends;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.R;
import com.example.administrator.redline.View.CustomImageView;
import com.example.administrator.redline.modelqq.been.Info;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    /* 布局填充器*/
    private LayoutInflater inflater;
    private String[] group = new String[]{"有缘人"};
//    private String[][] childs= new String[][]{{ "习大大","李克强","普京", "金正恩", "安倍晋三"},
//            {"刘铁男","万庆良","周永康", "徐才厚", "谷俊山", "令计划","郭伯雄","苏荣","陈水扁","蒋洁敏","李东生","白恩培" },
//            { "马云", "麻花藤", "李彦宏", "周鸿祎","雷布斯","库克" },
//            {"李冰冰","范冰冰","李小璐","杨颖","周冬雨","Lady GaGa","千颂伊","尹恩惠"}};
    private String[][] childs={{ "习大大","李克强","普京", "金正恩", "安倍晋三"}};

    ACache mACache;
    public List<Info> FriendsInfo;

    /* 构造,因为布局填充器填充布局需要使用到Context,通过构造来传递 */
    public MyExpandableListViewAdapter (Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mACache= ACache.get(context);
        String id=mACache.getAsString("id");
        String dirPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/data/";
        File dir=new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        sqliteUtils sqlop=new sqliteUtils(id,dirPath);
        FriendsInfo=sqlop.queryList();

        childs=new String[group.length][FriendsInfo.size()];
        for (int i=0;i<FriendsInfo.size();i++)
        {
            childs[0][i]=FriendsInfo.get(i).getName();
        }

    }



    /*组数*/
    @Override
    public int getGroupCount() {
        return group.length;
    }

    /* 指定组的子Item数*/
    @Override
    public int getChildrenCount(int groupPosition) {
        return childs[groupPosition].length;
    }

    /*组数据*/
    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    /*返回子选项的数据*/
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs[groupPosition][childPosition];
    }

    /*组ID*/
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /*子ID*/
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /*ID是否唯一*/
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /* 组选项的视图处理 */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        /* 填充布局*/
        View view = inflater.inflate(R.layout.item_elv_group,null);

        ImageView iv_group_icon = (ImageView) view.findViewById(R.id.iv_group_icon);
        TextView tv_group_name = (TextView) view.findViewById(R.id.tv_group_name);
        TextView tv_group_number = (TextView) view.findViewById(R.id.tv_group_number);

        tv_group_name.setText(group[groupPosition]);
        tv_group_number.setText(childs[groupPosition].length+"/"+childs[groupPosition].length);

        /*isExpanded 子列表是否展开*/
//        if(isExpanded){
//            iv_group_icon.setImageResource(R.mipmap.logo_01);
//        }else {
//            iv_group_icon.setImageResource(R.mipmap.logo_01);
//        }

        return view;

    }

    /* 子选项的视图处理 */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_elv_child,null);

        String Userid=FriendsInfo.get(childPosition).getUserId();
        CustomImageView iv_child_icon = (CustomImageView) view.findViewById(R.id.iv_child_icon);
        TextView tv_child_info = (TextView) view.findViewById(R.id.tv_child_info);
        TextView tv_child_name = (TextView) view.findViewById(R.id.tv_child_name);
        TextView tv_child_network = (TextView) view.findViewById(R.id.tv_child_network);

        tv_child_name.setText(childs[groupPosition][childPosition]);
        tv_child_network.setText(childPosition % 2 == 0?"5G":"6G");
        iv_child_icon.setImageBitmap(FriendsInfo.get(childPosition).getHeadpic());

        return view;
    }



    /*子选项是否可选 */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
