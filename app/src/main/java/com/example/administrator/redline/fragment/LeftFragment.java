package com.example.administrator.redline.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ServiceTest;
import com.example.UserInfo;
import com.example.UserInfoLogin;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.R;
import com.example.administrator.redline.View.CustomImageView;
import com.example.administrator.redline.match_page;

import java.io.Serializable;


/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description 侧边栏菜单
 */
public class LeftFragment extends Fragment implements OnClickListener {
    private  int cishu=0;
	/*侧栏按钮TextView*/
	private View view_goaround;
	private View view_info;
	private View view_allmatch;
	private View view_myredline;
	private View view_setting;
	private View view_personinfosetting;


	/* 图标按钮容器*/
	private RelativeLayout relativeL_goarund;
	private RelativeLayout relativeL_info;
	private RelativeLayout relativeL_allmatch;
	private RelativeLayout relativeL_myredline;
	private RelativeLayout relativeL_setting;

   //头像
	public CustomImageView ImageView;
	//昵称
	private TextView layout_menu_text_nick;

	//缓存
	private ACache mACache;
	private String id;
	private UserInfo userInfo;

    public RegisterHandler RqtHandler;
    private RegisterThread  RqtThread;

    public  class RegisterHandler extends Handler
    {
        //接受message的信息
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.getData().getString("get_userinf_state").equals("true"))
            {
                mACache.put(id,userInfo);

				layout_menu_text_nick.setText(userInfo._nick);
            }


        }
        public void updatePic()
		{
			mACache=mACache.get(getContext());
			Bitmap pic=mACache.getAsBitmap("headpic");
			if(pic!=null)
			{
				ImageView.setImageBitmap(pic);
			}
		}

    }
    public class RegisterThread implements Runnable
    {




        public void run()
        {
            Message msg = RqtHandler.obtainMessage();
            // 给消息结构的arg1参数赋值
            ServiceTest conn=new ServiceTest();  //数据库连接类

            Bundle bundle=new Bundle();   //初始化传递数据捆
            userInfo=conn.GetUserInfo(id);
            if(userInfo!=null)
            {
                bundle.putString("get_userinf_state","true");
            }
            else
            {
                bundle.putString("get_userinf_state","false");
            }


            msg.setData(bundle);    // 将bundle传递设置为传递的信息
            RqtHandler.sendMessage(msg);  //将信息传递给handler
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);




	}


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_layout_menu, null);

		//初始化id
		findViews(view);

		return view;
	}
	
	
	public void findViews(View view) {

		/*获取按钮TextView*/
		view_goaround =  view.findViewById(R.id.layout_menu_goaround);
		view_allmatch = view.findViewById(R.id.layout_menu_allmatch);
		view_info = view.findViewById(R.id.layout_menu_myinfo);
		view_myredline = view.findViewById(R.id.layout_menu_myredline);
		view_setting= view.findViewById(R.id.layout_menu_setting);
		view_personinfosetting=view.findViewById(R.id.profile_image)	; //个人信息设置

		//获取按钮容器
		relativeL_goarund=(RelativeLayout)view.findViewById(R.id.layout_menu_layout_goaround) ;
		relativeL_allmatch=(RelativeLayout)view.findViewById(R.id.layout_menu_layout_allmatch);
		relativeL_info=(RelativeLayout)view.findViewById(R.id.layout_menu_layout_myinfo);
		relativeL_myredline=(RelativeLayout)view.findViewById(R.id.layout_menu_layout_myredline);
		relativeL_setting=(RelativeLayout)view.findViewById(R.id.layout_menu_layout_setting) ;

		//回调按钮监听
		view_goaround.setOnClickListener(this);
		view_allmatch.setOnClickListener(this);
		view_info.setOnClickListener(this);
		view_myredline.setOnClickListener(this);
		view_setting.setOnClickListener(this);
		view_personinfosetting.setOnClickListener(this);

		ImageView=(CustomImageView)view.findViewById(R.id.profile_image);

		layout_menu_text_nick=(TextView)view.findViewById(R.id.layout_menu_text_nick);


		mACache=ACache.get(this.getContext());
		id=mACache.getAsString("id");

		userInfo=(UserInfo) mACache.getAsObject(id);
		layout_menu_text_nick.setText(userInfo._nick);

		Bitmap pic=mACache.getAsBitmap("headpic");
		if(pic!=null)
		{
			ImageView.setImageBitmap(pic);
		}
		//获取用户model 如果缓存未有，启动服务器请求
//		if(userInfo==null)
//		{
////			ServiceTest apiRqst=new ServiceTest();
////			userInfo=apiRqst.GetUserInfo(id);
////			mACache.put(id, (Serializable) userInfo);
//            Thread Rqt=new Thread(RqtThread);
//            Rqt.start();
//		}





	}

	public void setAllLayoutActivedFalse()
	{
		relativeL_goarund.setActivated(false);
		relativeL_info.setActivated(false);
		relativeL_allmatch.setActivated(false);
		relativeL_myredline.setActivated(false);
		relativeL_setting.setActivated(false);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		String title = null;
		setAllLayoutActivedFalse();
		switch (v.getId()) {
		case R.id.layout_menu_allmatch: // 所有配对
			newContent = new AllMatchFragment();
			title = getString(R.string.allmatch);
			relativeL_allmatch.setActivated(true);
			break;
		case R.id.layout_menu_goaround:// 附近走走
			newContent = new GoAroundFragment();
			title = getString(R.string.goaround);
			relativeL_goarund.setActivated(true);
			break;
		case R.id.layout_menu_myinfo: // 我的消息
			newContent = new MyInfoFragment();
			title = getString(R.string.myinfo);
			relativeL_info.setActivated(true);
			break;
		case R.id.layout_menu_myredline: // 我的红线
			newContent = new MyRedLineFragment();
			title = getString(R.string.myredline);
			relativeL_myredline.setActivated(true);
			break;
		case R.id.layout_menu_setting: // 系统设置
			newContent = new SettingFragment();
			title = getString(R.string.setting);
			relativeL_setting.setActivated(true);
			break;

			case  R.id.profile_image://个人信息设置
				newContent =new PersonalInfoSettingFragment();
				title="个人信息设置";
				break;






		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}
	
	/**
	 * 切换fragment
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof match_page) {
			match_page fca = (match_page) getActivity();
			fca.switchConent(fragment, title);
		}
	}
	
}
