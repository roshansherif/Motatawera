package com.example.egyedu;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Pay extends AppCompatActivity {
    private Button buttonback ;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_pay );
        buttonback = findViewById ( R.id.buttonback );

        buttonback.setOnClickListener ( v -> {
            finish ();
        } );

    }

}