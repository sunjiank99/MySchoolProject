package com.example.administrator.redline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.administrator.redline.AsimpleChache.ACache;

public class welcome_page extends AppCompatActivity {
    private Button btn_login;
    private Button btn_otherlogin;
    ACache mCache ;
    String cache_id_state="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉系统默认上边标题
        setContentView(R.layout.activity_welcome_page);
        btn_login=(Button)findViewById(R.id.welcome_btn_login);
        btn_otherlogin=(Button)findViewById(R.id.welcome_btn_otherlogin) ;
        mCache=ACache.get(this);

        cache_id_state=mCache.getAsString("id_state"); //缓存获取登录信息
        if(cache_id_state==null)
        {
            cache_id_state="false";
        }

        btn_login.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        if(cache_id_state.equals("false")) {


                            Intent intent = new Intent();
                            intent.setClass(welcome_page.this, login_page.class);
                            welcome_page.this.startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent();
                            intent.setClass(welcome_page.this, match_page.class);

                            welcome_page.this.startActivity(intent);
                        }
                    }
                }
        );

        btn_otherlogin.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setClass(welcome_page.this, otherlogin.class);
                        welcome_page.this.startActivity(intent);
                    }
                }
        );
    }
}
