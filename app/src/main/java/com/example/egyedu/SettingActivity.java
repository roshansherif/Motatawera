package com.example.egyedu;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    SwitchMaterial switch_theme;
    private TextView selectedLanguage;

    LinearLayout btn_info ;
    LinearLayout btnlanguage;
    ImageView baack;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_setting );
        //findView

        switch_theme = findViewById ( R.id.switch_theme );
        selectedLanguage = findViewById ( R.id.selectedLanguage );
        btnlanguage = findViewById ( R.id.btnlanguage );
        baack = findViewById ( R.id.baack );

        baack.setOnClickListener ( v -> {
            finish ();
        } );

        String currentLang = Locale.getDefault ().getDisplayLanguage ();
        String lang = getSharedPreferences ( "language" , MODE_PRIVATE )
                .getString ( "lang" , "currentLang" );
        if (lang.equals ( "ar" ))
            selectedLanguage.setText ( "عربي" );
        else
            selectedLanguage.setText ( "English" );

        findViewById ( R.id.btnlanguage ).setOnClickListener ( v -> {
            showAlertLanguage ();
        } );
        //to switch themes
        switch_theme.setOnCheckedChangeListener ( ( buttonView , isChecked ) -> {
            if (isChecked)
                saveTheme ( true );
            else
                saveTheme ( false );

        } );
    }





    private void saveTheme ( boolean b ) {
        getSharedPreferences ( "theme" , MODE_PRIVATE )
                .edit ()
                .putBoolean ( "themeSelected" , b )
                .apply ();

        if (b) {

            AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_YES );
        } else
            AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_NO );

    }


    private void showAlertLanguage () {
        AlertDialog alert = new AlertDialog.Builder ( this ).create ();
        View view = LayoutInflater.from ( this ).inflate ( R.layout.layout_language , null );
        alert.setView ( view );
        alert.show ();

        ImageView cancle_language = view.findViewById ( R.id.cancel_language );
        cancle_language.setOnClickListener ( v -> {
            alert.dismiss ();
        } );

        RadioButton radio_en = view.findViewById ( R.id.radio_en );
        RadioButton radio_ar = view.findViewById ( R.id.radio_ar );
        view.findViewById ( R.id.save_language ).setOnClickListener ( v -> {
            if (radio_en.isChecked ())
                saveLanguage ( "en" );
            else if (radio_ar.isChecked ())
                saveLanguage ( "ar" );

        } );
        alert.show ();
    }

    void saveLanguage ( String language ) {
        getSharedPreferences ( "language" , MODE_PRIVATE )
                .edit ()
                .putString ( "lang" , language )
                .apply ();

        //language
        Locale l = new Locale ( language );
        Locale.setDefault ( l );
        Configuration configuration = new Configuration ();
        configuration.locale = l;
        getResources ().updateConfiguration ( configuration , getResources ().getDisplayMetrics () );
        Intent intent = new Intent ( SettingActivity.this , SplashActivity.class );
        intent.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity ( intent );
    }
}

