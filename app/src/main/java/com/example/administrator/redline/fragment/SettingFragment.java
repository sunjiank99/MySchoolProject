package com.example.administrator.redline.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.redline.AllMatchFriends.sqliteUtils;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    private Button ClearCache;
    private ACache mACache;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        ClearCache=(Button)view.findViewById(R.id.setting_btn_ClearCache);
         mACache=ACache.get(getContext());
        ClearCache.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mACache=ACache.get(getContext());
                       // mACache.clear();
                        String dirPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/data/";
                        sqliteUtils SQLOp= new sqliteUtils(mACache.getAsString("id"),dirPath);
                        SQLOp.deleteTable();
                        mACache.clear();
                        Toast.makeText(getContext(),"清除缓存成功",Toast.LENGTH_LONG).show();
                    }
                }
        );

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
