package com.example.egyedu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to make activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);



        YoYo.with ( Techniques.Flash )
                .duration(3000) // Time it for logo takes to bounce up and down
                .playOn(findViewById(R.id.image));

        YoYo.with ( Techniques.Shake )
                .duration(3000) // Time it for logo takes to bounce up and down
                .playOn(findViewById(R.id.textView));

        new Handler ().postDelayed (() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish ();
        }, 3000 );
    }
}