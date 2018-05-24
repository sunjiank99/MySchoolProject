package com.example.administrator.redline.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.redline.AllMatchFriends.MyExpandableListViewAdapter;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.ChatWindows;
import com.example.administrator.redline.R;
import com.example.administrator.redline.match_page;
import com.example.administrator.redline.modelqq.been.Info;

import java.util.List;

public class AllMatchFragment extends Fragment {

    public List<Info> FriendsInfo;
    ACache mAcache;
    /*可拓展列表视图*/
    private ExpandableListView expandableListView ;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_all_match_, null);
            expandableListView = (ExpandableListView) view.findViewById(R.id.elv);

            /* 1.1 创建一个adapter实例*/
            MyExpandableListViewAdapter adapter = new MyExpandableListViewAdapter(this.getContext());


            /* 1. 设置适配器*/
            expandableListView.setAdapter(adapter);
            FriendsInfo=adapter.FriendsInfo;
            mAcache=ACache.get(this.getContext());

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {

                    TextView mubiaoid=(TextView) v.findViewById(R.id.tv_child_name);
                    Toast.makeText(getContext(), groupPosition+"||"+childPosition+mubiaoid.getText(), Toast.LENGTH_LONG).show();

                   Intent intent = new Intent(getActivity(), ChatWindows.class);
                   intent.putExtra("BUserId",mAcache.getAsString("id"));//用户账号
                   intent.putExtra("AUserId",FriendsInfo.get(childPosition).getUserId());//目标聊天人账号

                   mAcache.put("AUserIdPic",FriendsInfo.get(childPosition).getHeadpic());//缓存目标聊天人头像
                    //intent.setData(Uri.parse(adapter.getList().get(groupPosition).videos.get(childPosition).url));
                   startActivity(intent);
                    return true;
                }
            });

            return view;
        }




        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }


        @Override
        public void onDestroy() {
            super.onDestroy();
        }

}
