package com.example.egyedu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    //view
    private EditText _email, _password;
    private CheckBox rememberMe;
    private ProgressBar progressBar;
    //firebase
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        //FindView
        _email = findViewById ( R.id.email_login );
        _password = findViewById ( R.id.password_login );
        rememberMe = findViewById ( R.id.remember_me_login );
        progressBar = findViewById ( R.id.progress_login );


        //firebase
        firebaseAuth = FirebaseAuth.getInstance ();

        //intent to register activity
        findViewById ( R.id.new_user_login ).setOnClickListener ( v -> {
            Intent textv = new Intent ( LoginActivity.this , RegisterActivity.class );
            startActivity ( textv );
        } );

        //onClick to login
        findViewById ( R.id.sign_in_login ).setOnClickListener ( v -> {
            validationData ();

        } );
        //resentpassword
        findViewById ( R.id.forgotpassword_login ).setOnClickListener ( v -> {
            Intent textv = new Intent ( LoginActivity.this , ResetPasswordActivity.class );
            startActivity ( textv );
        } );


    }

    private void validationData () {
        String email = _email.getText ().toString ().trim ();
        String password = _password.getText ().toString ().trim ();

        if (email.isEmpty ()) {
            _email.requestFocus ();
            showAlert ( getString ( R.string.valid_email ) );

        } else if (!Patterns.EMAIL_ADDRESS.matcher ( email ).matches ()) {
            _email.requestFocus ();
            showAlert ( getString (R.string.invalid_email ) );

        } else if (password.isEmpty ()) {
            _password.requestFocus ();
            showAlert ( getString ( R.string.validpassword ) );

        } else if (password.length () < 6) {
            _password.requestFocus ();
            showAlert ( getString ( R.string.invalid_password ) );

        } else signInWithFarebase ( email , password );
    }

    private void signInWithFarebase ( String email , String password ) {
        progressBar.setVisibility ( View.VISIBLE );
        firebaseAuth.signInWithEmailAndPassword ( email , password )
                .addOnCompleteListener ( task -> {
                    if (task.isSuccessful ()) {
                        progressBar.setVisibility ( View.GONE );

                        if (rememberMe.isChecked ()) {
                            getSharedPreferences ( "login" , MODE_PRIVATE )
                                    .edit ()
                                    .putBoolean ( "isLogin" , true )
                                    .apply ();
                        }
                        goToMain ();
                    } else if ((firebaseAuth != null)) {
                        progressBar.setVisibility ( View.GONE );
                        showAlert ( "Error\n" + task.getException ().getMessage () );
                    } else {
                        startActivity ( new Intent ( LoginActivity.this , MainActivity.class ) );
                        finish ();
                    }

                } );
    }

    void showAlert ( String msg ) {
        new AlertDialog.Builder ( this )
                .setTitle ( "Error" )
                .setMessage ( msg )
                .setIcon ( R.drawable.ic_error )
                .setPositiveButton ( "OK" , null )
                .create ().show ();
    }

    @Override
    protected void onStart () {
        super.onStart ();

        boolean isLogin = getSharedPreferences ( "login" , MODE_PRIVATE ).getBoolean ( "isLogin" , false );

        if (isLogin) {
            goToMain ();
        }
    }

    void goToMain () {
        startActivity ( new Intent ( LoginActivity.this , MainActivity.class ) );
        finish ();
    }
}