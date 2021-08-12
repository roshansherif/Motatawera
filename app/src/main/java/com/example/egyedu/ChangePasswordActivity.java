package com.example.egyedu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.egyedu.databinding.ActivityChangePasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;
    FirebaseAuth firebaseAuth;
    String dd;
    FirebaseFirestore firestore;
    ImageView back;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        binding = DataBindingUtil.setContentView ( this , R.layout.activity_change_password );
        firestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();
        firestore.collection ( "users" )
                .document ( firebaseAuth.getCurrentUser ().getUid () )
                .get ()
                .addOnCompleteListener ( task -> {
                    if (task.isSuccessful ()) {
                        dd = task.getResult ().getString ( "password" );
                    } else {
                        showAlert ( task.getException ().getMessage () );
                    }
                } );
        back = findViewById ( R.id.back );

        binding.back.setOnClickListener ( v -> {
            finish ();
        } );

        binding.btnchangepass.setOnClickListener ( v -> {
            checkValidation ();

        } );


    }

    private void checkValidation () {
        String old = binding.oldpassword.getText ().toString ().trim ();
        String nnew = binding.newpassword.getText ().toString ().trim ();
        String password = binding.Confirmpassword.getText ().toString ().trim ();

        if (old.isEmpty ()) {
            binding.oldpassword.requestFocus ();

            return;
        }
        if (!old.equals ( dd )) {
            binding.oldpassword.requestFocus ();
            showAlert ( "your current password is incorrect" );
            return;
        }
        if (nnew.isEmpty ()) {
            binding.newpassword.requestFocus ();

            return;
        }
        if (nnew.length () < 6) {
            binding.newpassword.requestFocus ();
            showAlert ( "password muse be 6 digit" );
            return;
        }
        if (!password.equals ( nnew )) {
            binding.Confirmpassword.requestFocus ();
            showAlert ( "The password does not match" );
            return;

        } else saveChange ();
    }

    void showAlert ( String msg ) {
        new AlertDialog.Builder ( this )
                .setTitle ( "Error" )
                .setMessage ( msg )
                .setIcon ( R.drawable.ic_error )
                .setPositiveButton ( "OK" , null )
                .create ().show ();
    }


    private void saveChange () {

        // mack progress bar dialog
        final ProgressDialog progressDialog = new ProgressDialog ( this );
        progressDialog.setTitle ( "Please wait.." );
        progressDialog.setCancelable ( false );
        progressDialog.show ();


        String userID = firebaseAuth.getCurrentUser ().getUid ();
        Map<String, Object> map = new HashMap<> ();
        map.put ( "password" , binding.Confirmpassword.getText ().toString ().trim () );

        firestore.collection ( "users" )
                .document ( userID )
                .update ( map )
                .addOnCompleteListener ( task -> {
                    if (task.isSuccessful ()) {
                        FirebaseUser user = FirebaseAuth.getInstance ().getCurrentUser ();
                        user.updatePassword ( binding.Confirmpassword.getText ().toString ().trim () )
                                .addOnCompleteListener ( task1 -> {
                                    if (task1.isSuccessful ()) {

                                        progressDialog.dismiss ();
                                        new AlertDialog.Builder ( this )
                                                .setMessage ( "password update successfully!" )
                                                .setCancelable ( false )
                                                .setPositiveButton ( "OK" , ( dialog , which ) -> {

                                                    finish ();
                                                } )
                                                .create ()
                                                .show ();
                                    } else {
                                        showAlert ( task1.getException ().getMessage () );
                                    }
                                } );
                    } else {

                        progressDialog.dismiss ();
                    }
                } );

    }

}