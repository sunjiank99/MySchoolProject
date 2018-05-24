package com.example.administrator.redline.camera;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class BaseFragment extends Fragment {
    protected FragmentActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }
}
