package com.uae.tambolaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import com.uae.tambolaapp.helper_classes.Rotation;
import com.uae.tambolaapp.helper_classes.Utils;


public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        removeStatusBar();
        Utils.requestFullScreen(this);
       final ImageView img = (ImageView) findViewById(R.id.iv_splash);

        /*Rotation rotation = new Rotation(img,0,350,30,10,Rotation.ABSOLUTE,Rotation.ABSOLUTE);
        rotation.roatateView(700);
        rotation.stopRotation(4000);
*/


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        }, 2000);
    }
}
