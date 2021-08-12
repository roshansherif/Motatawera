package com.example.egyedu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.egyedu.databinding.ActivityShowCoursDataBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ShowCoursDataActivity extends AppCompatActivity {

    private ActivityShowCoursDataBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String id;
    ImageView back;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        binding = DataBindingUtil.setContentView ( this , R.layout.activity_show_cours_data );
        firestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();
        id = getIntent ().getStringExtra ( "id" );
        back = findViewById ( R.id.bback );


        findViewById ( R.id.visa ).setOnClickListener ( v -> {
            Intent textv = new Intent ( ShowCoursDataActivity.this , Pay.class );
            startActivity ( textv );
        } );

        getData ();
        checkBtnfav ();
        checkpay ();



        back.setOnClickListener ( v -> {
            finish ();
        } );

        binding.pay.setOnClickListener ( v -> {
            setpayData  ();
        } );

        binding.favBtn.setOnClickListener ( v -> {
            setFavData ();
        } );


    }

    private void checkBtnfav () {
        String Uid = firebaseAuth.getUid ();
        firestore.collection ( "users" )
                .document ( Uid )
                .collection ( "favorite" )

                .addSnapshotListener ( ( value , error ) -> {
                    if (error == null) {

                        if (value == null) {
                            Toast.makeText ( this , "No Data Found" , Toast.LENGTH_SHORT ).show ();

                        } else {
                            for (DocumentChange documentChange : value.getDocumentChanges ())
                                if (documentChange.getDocument ().getId ().equals ( id )) {
                                    binding.favBtn.setEnabled ( false );
                                    binding.favBtn.setClickable ( false );
                                } else {

                                    binding.favBtn.setEnabled ( true );
                                    binding.favBtn.setClickable ( true );
                                }

                        }
                    } else {
                        Toast.makeText ( this , error.getMessage () , Toast.LENGTH_SHORT ).show ();
                    }
                } );
    }



    private void checkpay () {
        String Uid = firebaseAuth.getUid ();
        firestore.collection ( "users" )
                .document ( Uid )
                .collection ( "mycourses" )

                .addSnapshotListener ( ( value , error ) -> {
                    if (error == null) {

                        if (value == null) {
                            Toast.makeText ( this , "No Data Found" , Toast.LENGTH_SHORT ).show ();

                        } else {
                            for (DocumentChange documentChange : value.getDocumentChanges ())
                                if (documentChange.getDocument ().getId ().equals ( id )) {
                                    binding.pay.setEnabled ( false );
                                    binding.pay.setClickable ( false );
                                } else {

                                    binding.pay.setEnabled ( true );
                                    binding.pay.setClickable ( true );
                                }

                        }
                    } else {
                        Toast.makeText ( this , error.getMessage () , Toast.LENGTH_SHORT ).show ();
                    }
                } );
    }

    private void setFavData () {
        String Uid = firebaseAuth.getUid ();
        firestore.collection ( "courses" )
                .document ( id )
                .get ()
                .addOnCompleteListener ( task -> {
                    if (task.isSuccessful ()) {
                        Map<String, Object> corsses = new HashMap<> ();
                        corsses.put ( "id" , id );
                        corsses.put ( "course_name" , task.getResult ().getString ( "course_name" ) );
                        corsses.put ( "price" , task.getResult ().get ( "price" ) );
                        corsses.put ( "center_name" , task.getResult ().getString ( "center_name" ) );
                        corsses.put ( "info" , task.getResult ().getString ( "info" ) );
                        corsses.put ( "category" , task.getResult ().getString ( "category" ) );
                        corsses.put ( "date" , task.getResult ().getString ( "date" ) );
                        corsses.put ( "image" , task.getResult ().getString ( "image" ) );
                        firestore.collection ( "users" )
                                .document ( Uid )
                                .collection ( "favorite" ).document ( id ).set ( corsses );
                        binding.favBtn.setClickable ( false );
                        binding.favBtn.setEnabled ( false );


                    } else {
                        Toast.makeText ( this , task.getException ().getMessage () , Toast.LENGTH_SHORT ).show ();
                    }
                } );


    }

    private void setpayData () {
        String Uid = firebaseAuth.getUid ();
        firestore.collection ( "courses" )
                .document ( id )
                .get ()
                .addOnCompleteListener ( task -> {
                    if (task.isSuccessful ()) {

                        new AlertDialog.Builder ( this )
                                .setMessage ( getString ( R.string.data_saved_at_your_courses_page ) )
                                .setCancelable ( false )
                                .setPositiveButton ( "OK" , ( dialog , which ) -> {

                                    finish ();
                                } )
                                .create ()
                                .show ();



                        Map<String, Object> corsses = new HashMap<> ();
                        corsses.put ( "id" , id );
                        corsses.put ( "course_name" , task.getResult ().getString ( "course_name" ) );
                        corsses.put ( "price" , task.getResult ().get ( "price" ) );
                        corsses.put ( "center_name" , task.getResult ().getString ( "center_name" ) );
                        corsses.put ( "info" , task.getResult ().getString ( "info" ) );
                        corsses.put ( "category" , task.getResult ().getString ( "category" ) );
                        corsses.put ( "date" , task.getResult ().getString ( "date" ) );
                        corsses.put ( "image" , task.getResult ().getString ( "image" ) );
                        firestore.collection ( "users" )
                                .document ( Uid )
                                .collection ( "mycourses" ).document ( id ).set ( corsses );
                        binding.pay.setClickable ( false );
                        binding.pay.setEnabled ( false );


                    } else {
                        Toast.makeText ( this , task.getException ().getMessage () , Toast.LENGTH_SHORT ).show ();
                    }
                } );


    }





    private void getData () {


        String centerName = getIntent ().getStringExtra ( "centerName" );
        int price = getIntent ().getIntExtra ( "price" , 0 );
        id = getIntent ().getStringExtra ( "id" );
        String image = getIntent ().getStringExtra ( "image" );
        String info = getIntent ().getStringExtra ( "info" );
        String cat = getIntent ().getStringExtra ( "cat" );
        String courseName = getIntent ().getStringExtra ( "courseName" );
        String city = getIntent ().getStringExtra ( "city" );
        String date = getIntent ().getStringExtra ( "date" );
        binding.scname.setText ( courseName );
        binding.scinfo.setText ( info );
        binding.sccentername.setText ( centerName );
        binding.scprice.setText ( String.valueOf ( price ) );
        binding.scdate.setText ( date );
        Glide.with ( this )
                .load ( image )
                .placeholder ( R.drawable.ic_booking )
                .into ( binding.coursimage );


    }








}