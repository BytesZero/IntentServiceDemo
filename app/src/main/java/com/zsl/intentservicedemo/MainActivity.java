package com.zsl.intentservicedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity{

    String path_home="/sdcard/imgs/";

    Button bt_start;
    LinearLayout ll_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_message = (LinearLayout) findViewById(R.id.main_ll_message);
        bt_start= (Button) findViewById(R.id.main_bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpload();
            }
        });


    }

    /**
     * 开始上传
     */
    private void startUpload() {
        for (int i=0;i<10;i++) {
            String path=path_home + i + ".jpg";
            TextView tv_message=new TextView(this);
            tv_message.setText(path+"  uploading ...");
            tv_message.setTag(path);
            ll_message.addView(tv_message);

            UploadImgService.startUPloadImg(MainActivity.this, path_home + i + ".jpg");
        }
    }

    @Override
    protected void onStart() {
        //注册EventBus
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        //注销EventBus
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(MessageEvent event) {
        showContent(event.message);
    }


    private void showContent(String path) {
        TextView tv= (TextView) ll_message.findViewWithTag(path);
        tv.setText(path+"  success");
    }


}
