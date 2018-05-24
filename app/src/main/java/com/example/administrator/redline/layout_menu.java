package com.example.administrator.redline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class layout_menu extends AppCompatActivity {
    private TextView textview_myredline;
    private RelativeLayout Rlt_myredline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_menu);


        textview_myredline=(TextView)findViewById(R.id.layout_menu_myredline);
        Rlt_myredline=(RelativeLayout)findViewById(R.id.layout_menu_layout_myredline) ;


        textview_myredline.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        Rlt_myredline.setActivated(true);



                    }
                }
        );






    }
}
