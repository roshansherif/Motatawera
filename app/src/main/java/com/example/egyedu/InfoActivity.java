 package com.example.egyedu;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
ImageView baaaack;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_info );
        baaaack=findViewById ( R.id.baaaack );
        baaaack.setOnClickListener ( v -> {
            finish ();
        } );
    }
}