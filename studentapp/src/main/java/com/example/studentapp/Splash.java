package com.example.studentapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView txtStudentPortal;
    ImageView imgAppIcon;
    Animation top_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imgAppIcon=findViewById(R.id.imgAppIcon);
        txtStudentPortal=findViewById(R.id.txtStudentPortal);
        top_animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_animation);
        imgAppIcon.setAnimation(top_animation);
        txtStudentPortal.setAnimation(top_animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}