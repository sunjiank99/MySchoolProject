package com.example.administrator.redline.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.*;
import android.widget.Toast;

import com.example.ServiceTest;
import com.example.UserInfo;
import com.example.UserInfoLogin;
import com.example.administrator.redline.AsimpleChache.ACache;
import com.example.administrator.redline.Birthday;
import com.example.administrator.redline.R;
import com.example.administrator.redline.View.CustomImageView;
import com.example.administrator.redline.camera.BaseFragment;
import com.example.administrator.redline.camera.ImageUtils;
import com.example.administrator.redline.match_page;

import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalInfoSettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalInfoSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalInfoSettingFragment extends Fragment {
    //生日设置区
    private RelativeLayout Rlt_birthday_view;
    //记录时间变量
    private int year;
    private int month;
    private int day;

    private TextView txtView_birthday;

    private String uploadString;
    private String downloadString;
    //头像设置区
    private static final String TAG = "MainActivity";

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    protected static Uri uritempFile;
    //头像
    private CustomImageView iv_personal_icon;
    private ImageView headPic;
    private ACache mACache;



    //个人资料显示
    //用户名
    private TextView personalview_dynamictxt_username;
    //手机号
    private TextView personalview_dynamictxt_phonenumber;
    //邮箱
    private TextView personalview_dynamictxt_email;
    //性别
    private TextView personalview_dynamictxt_sex;

    //微博设置
    private TextView getPersonalview_dynamictxt_weibo;

    //明星设置
    private TextView getPersonalview_dynamictxt_star;



    //用户信息区
    private String id;
    private UserInfo userInfo;

    UpdateHandler updateHandler;
    UpdateThread  updateThread;

    public  class UpdateHandler extends Handler
    {
        //接受message的信息
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.getData().getString("RequestId").equals("0"))
            {
                        if(msg.getData().getString("UpdateEmail").equals("true"))
                        {
                            Toast.makeText(getContext(), "邮箱修改成功",Toast.LENGTH_LONG).show();
                            userInfo._email=msg.getData().getString("UpdateEmailContent").toString();
                            //更新缓存
                            mACache.put(id,userInfo);
                            personalview_dynamictxt_email.setText(userInfo._email);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "邮箱修改失败",Toast.LENGTH_LONG).show();
                        }
            }
            if(msg.getData().getString("RequestId").equals("1"))
            {
                if(msg.getData().getString("UpdateBirthday").equals("true"))
                {
                    Toast.makeText(getContext(), "生日修改成功",Toast.LENGTH_LONG).show();
                    DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date=new Date();
                    try {
                        date=format1.parse(msg.getData().getString("UpdateBirthdayContent").toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    userInfo._birthday=date;
                    //更新缓存
                    mACache.put(id,userInfo);
                    txtView_birthday.setText(msg.getData().getString("UpdateBirthdayContent").toString());
                }
                else
                {
                    Toast.makeText(getContext(), "生日修改失败",Toast.LENGTH_LONG).show();
                }
            }
            if(msg.getData().getString("RequestId").equals("2"))
            {
                if(msg.getData().getString("UpdateSex").equals("true"))
                {
                    Toast.makeText(getContext(), "性别修改成功",Toast.LENGTH_LONG).show();
                    userInfo._sex=Integer.parseInt(msg.getData().getString("UpdateSexContent").toString());

                    //更新缓存
                    mACache.put(id,userInfo);
                    //刷新
                    switch (userInfo._sex) {
                        case 0: personalview_dynamictxt_sex.setText("男");break;
                        case 1: personalview_dynamictxt_sex.setText("女");break;
                        case 2: personalview_dynamictxt_sex.setText("不告诉你");break;

                    }


                }
                else
                {
                    Toast.makeText(getContext(), "性别修改失败",Toast.LENGTH_LONG).show();
                }
            }
            if(msg.getData().getString("RequestId").equals("3"))
            {
                if (msg.getData().getString("uploade_state").equals("true")) {
                    Toast.makeText(getContext(), "头像上传成功", Toast.LENGTH_LONG);
                       //getActivity().getFragmentManager().findFragmentByTag("LeftFragment").getContext().

                    CustomImageView yinyong=(CustomImageView) getActivity().findViewById(R.id.profile_image);
                    yinyong.setImageBitmap(mACache.getAsBitmap("headpic"));
                }
            }
            if(msg.getData().getString("RequestId").equals("4"))
            {

                    Toast.makeText(getContext(), "头像下载成功", Toast.LENGTH_LONG);

                    byte[] bytes = Base64.decode(downloadString);

                    Bitmap downpic=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    iv_personal_icon.setImageBitmap(downpic);


            }
            if(msg.getData().getString("RequestId").equals("5"))
            {

                if(msg.getData().getString("UpdateWeiboId").equals("true"))
                {
                    Toast.makeText(getContext(), "微博修改成功",Toast.LENGTH_LONG).show();


                    userInfo._weibo=msg.getData().getString("UpdateWeiboIdContent").toString();
                    //更新缓存
                    mACache.put(id,userInfo);
                    getPersonalview_dynamictxt_weibo.setText(msg.getData().getString("UpdateWeiboIdContent").toString());
                }
                else
                {
                    Toast.makeText(getContext(), "微博修改失败",Toast.LENGTH_LONG).show();
                }


            }

            if(msg.getData().getString("RequestId").equals("6"))
            {

                if(msg.getData().getString("UpdateStars").equals("true"))
                {
                    Toast.makeText(getContext(), "明星修改成功",Toast.LENGTH_LONG).show();


                    userInfo._stars=msg.getData().getString("UpdateStarsContent").toString();
                    //更新缓存
                    mACache.put(id,userInfo);
                    getPersonalview_dynamictxt_star.setText(msg.getData().getString("UpdateStarsContent").toString());
                }
                else
                {
                    Toast.makeText(getContext(), "明星修改失败",Toast.LENGTH_LONG).show();
                }


            }

        }


    }
    public class UpdateThread implements Runnable
    {

        private String Content;
        private int RequestId;
        public void setContent(String Content,int RequestId)
        {
            this.Content=Content;
            this.RequestId=RequestId;
        }


        public void run()
        {
            Message msg = updateHandler.obtainMessage();
            // 给消息结构的arg1参数赋值
            ServiceTest conn=new ServiceTest();  //数据库连接类

            Bundle bundle=new Bundle();   //初始化传递数据捆
            //修改邮件
           if(RequestId==0) {
               bundle.putString("RequestId","0");
               if (conn.UpdateUserinfo(id, "Email", Content))
               {
                   bundle.putString("UpdateEmail","true");
                   bundle.putString("UpdateEmailContent",Content);
               }
               else
               {
                   bundle.putString("UpdateEmail","false");
               };
           }
            //修改生日
            if(RequestId==1) {
                bundle.putString("RequestId","1");
                if (conn.UpdateUserinfo(id, "Birthday", Content))
                {
                    bundle.putString("UpdateBirthday","true");
                    bundle.putString("UpdateBirthdayContent",Content);
                }
                else
                {
                    bundle.putString("UpdateBirthday","false");
                };
            }
            //修改性别
            if(RequestId==2) {
                bundle.putString("RequestId","2");
                if (conn.UpdateUserinfo(id, "Sex", Content))
                {
                    bundle.putString("UpdateSex","true");
                    bundle.putString("UpdateSexContent",Content);
                }
                else
                {
                    bundle.putString("UpdateSex","false");
                };
            }
            //上传头像
            if(RequestId==3) {
                bundle.putString("RequestId","3");
                if (conn.UploadePic(uploadString, id)) {
                    bundle.putString("uploade_state", "true");   //Exit_ID为是否存在当前用户名


                } else {
                    bundle.putString("uploade_state", "false");

                }
            }
            //下载头像
            if(RequestId==4)
            {
                bundle.putString("RequestId","4");
                downloadString=conn.DownloadePic(id);
                bundle.putString("downloade_state", "true");   //Exit_ID为是否存在当前用户名
            }

            //设置微博
            if(RequestId==5)
            {
                bundle.putString("RequestId","5");
                if (conn.UpdateUserinfo_Extend(id, "WeiboId", Content))
                {
                    bundle.putString("UpdateWeiboId","true");
                    bundle.putString("UpdateWeiboIdContent",Content);
                }
                else
                {
                    bundle.putString("UpdateWeiboId","false");
                };

            }
            //设置最喜欢的明星
            if(RequestId==6)
            {
                bundle.putString("RequestId","6");
                if (conn.UpdateUserinfo_Extend(id, "Stars", Content))
                {
                    bundle.putString("UpdateStars","true");
                    bundle.putString("UpdateStarsContent",Content);
                }
                else
                {
                    bundle.putString("UpdateStars","false");
                };

            }
            msg.setData(bundle);    // 将bundle传递设置为传递的信息
            updateHandler.sendMessage(msg);  //将信息传递给handler
        }
//        public static void main(String[] args)
//        {
//            MyThread2 myThread = new MyThread2();
//            myThread.setName("world");
//            Thread thread = new Thread(myThread);
//            thread.start();
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2=ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED||permission2!= PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personalview, null);
        iv_personal_icon=(CustomImageView) view.findViewById(R.id.personalview_image_headPic);

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //生日设置区
        Rlt_birthday_view=(RelativeLayout)view.findViewById(R.id.personalview_Rlt_birthday);
        txtView_birthday=(TextView)view.findViewById(R.id.personalview_dynamictxt_birthday);
        //获取缓存
        mACache=ACache.get(this.getContext());
        id =mACache.getAsString("id");

        updateHandler=new UpdateHandler();

        updateThread=new UpdateThread();

        personalview_dynamictxt_username=(TextView)view.findViewById(R.id.personalview_dynamictxt_username);
        personalview_dynamictxt_email=(TextView)view.findViewById(R.id.personalview_dynamictxt_email);
        personalview_dynamictxt_phonenumber=(TextView)view.findViewById(R.id.personalview_dynamictxt_phonenumber);
        personalview_dynamictxt_sex=(TextView)view.findViewById(R.id.personalview_dynamictxt_sex);
        getPersonalview_dynamictxt_weibo=(TextView)view.findViewById(R.id.Edit_weibo);
        getPersonalview_dynamictxt_star=(TextView)view.findViewById(R.id.Edit_stars);
        //获取个人信息
        userInfo=(UserInfo) mACache.getAsObject(id);

        //显示头像
        iv_personal_icon.setImageBitmap(mACache.getAsBitmap("headpic"));
        //显示用户名
        personalview_dynamictxt_username.setText(userInfo._userid);
        personalview_dynamictxt_email.setText(userInfo._email);
        if(userInfo._weibo!=null) {
            getPersonalview_dynamictxt_weibo.setText(userInfo._weibo);
        }
        if(userInfo._stars!=null) {
            getPersonalview_dynamictxt_star.setText(userInfo._stars);
        }
        //显示性别

            switch (userInfo._sex) {
                case 0: personalview_dynamictxt_sex.setText("男");break;
                case 1: personalview_dynamictxt_sex.setText("女");break;
                case 2: personalview_dynamictxt_sex.setText("不告诉你");break;

            }



        //修改生日
        Rlt_birthday_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatePickerDialog datePickerDialog=new DatePickerDialog(PersonalInfoSettingFragment.this.getContext(),new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                String dateTxt=year+"-"+(month+1)+"-"+dayOfMonth;
                                updateThread.setContent(dateTxt,1);
                                Thread AupdateThread =new Thread(updateThread);
                                AupdateThread.start();
                                //txtView_birthday.setText(year+"-"+(month+1)+"-"+dayOfMonth);

                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                }
        );
       //头像设置
        iv_personal_icon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showChoosePicDialog();
                    }
                }
        );
      //邮箱设置
        personalview_dynamictxt_email.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert_email(v);
                    }
                }
        );
        //
        personalview_dynamictxt_sex.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert_Sex();
                    }
                }
        );

        getPersonalview_dynamictxt_star.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert_stars();
                    }
                }
        );

        getPersonalview_dynamictxt_weibo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert_weibo();
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

    public void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoSettingFragment.this.getContext());
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        takePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }
    private void takePicture() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(this.getContext(), permissions[0]);
            int check1=ContextCompat.checkSelfPermission(this.getContext(), permissions[1]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            if(check1!=PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

        //requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
		Intent openCameraIntent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
//        Intent openCameraIntent = new Intent(
//                "android.media.action.IMAGE_CAPTURE");

//        File file = new File(Environment
//                .getExternalStorageDirectory(), "case.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//			tempUri = FileProvider.getUriForFile(getContext(), "com.lt.uploadpicdemo.fileProvider", file);

//            ContentValues contentValues = new ContentValues(1);
//            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
//
//
//
//            tempUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

           /* intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_CAMERA);*/
            tempUri = Uri.fromFile(new File(Environment
                   .getExternalStorageDirectory(), "image.jpg"));
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //selFragment.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    //startPhotoZoom(data.getData());
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uritempFile));

                        //缓存头像
                        //mACache.put("headpic",bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //bitmap = ImageUtils.toRoundBitmap(bitmap); // 这个时候的图片已经被处理成圆形的了

                    //缓存头像
                    mACache.put("headpic",bitmap);

                    //bitmap转换成Base64
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    uploadString = new String(Base64.encode(baos.toByteArray()));

                    //上传头像
                    updateThread=new UpdateThread();
                    updateThread.setContent("nothing",3);
                    Thread aa=new Thread(updateThread);
                    aa.start();


                    iv_personal_icon.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        /*intent.putExtra("return-data", true);*/

//        uritempFile= Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        uritempFile=Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "case.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,  uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    //修改邮箱事件
    public void alert_email(View view){
        final EditText et = new EditText(this.getContext());
        et.setText(personalview_dynamictxt_email.getText());
        new AlertDialog.Builder(this.getContext()).setTitle("邮箱")

                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                       // Toast.makeText(getContext(), "修改成功",Toast.LENGTH_LONG).show();
                        //personalview_dynamictxt_email.setText(et.getText().toString());

                        updateThread.setContent(et.getText().toString(),0);
                        Thread AupdateThread =new Thread(updateThread);
                        AupdateThread.start();

                    }
                }).setNegativeButton("取消",null).show();
    }


    //修改性别
    private void alert_Sex() {
         final String[] sexArry = new String[]{"不告诉你", "女", "男"};// 性别选择
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this.getContext());// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                //changesex_textview.setText(sexArry[which]);
                int Content=2;
                switch (sexArry[which].toString())
                {
                    case "不告诉你":Content=2;break;
                    case "女":Content=1;break;
                    case "男":Content=0;break;
                }

                updateThread.setContent(String.valueOf(Content),2);
                Thread AupdateThread =new Thread(updateThread);
                AupdateThread.start();
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    private void alert_weibo()
    {
        final EditText et = new EditText(this.getContext());
        et.setText(getPersonalview_dynamictxt_weibo.getText());
        new AlertDialog.Builder(this.getContext()).setTitle("设置微博id")

                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        // Toast.makeText(getContext(), "修改成功",Toast.LENGTH_LONG).show();
                        //personalview_dynamictxt_email.setText(et.getText().toString());

                        updateThread.setContent(et.getText().toString(),5);
                        Thread AupdateThread =new Thread(updateThread);
                        AupdateThread.start();

                    }
                }).setNegativeButton("取消",null).show();
    }

    private void alert_stars()
    {
        final EditText et = new EditText(this.getContext());
        et.setText(getPersonalview_dynamictxt_star.getText());
        new AlertDialog.Builder(this.getContext()).setTitle("设置最喜欢的明星")

                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        // Toast.makeText(getContext(), "修改成功",Toast.LENGTH_LONG).show();
                        //personalview_dynamictxt_email.setText(et.getText().toString());

                        updateThread.setContent(et.getText().toString(),6);
                        Thread AupdateThread =new Thread(updateThread);
                        AupdateThread.start();

                    }
                }).setNegativeButton("取消",null).show();
    }
}
