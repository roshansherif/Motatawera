package com.example.egyedu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.egyedu.ChangePasswordActivity;
import com.example.egyedu.R;
import com.example.egyedu.SettingActivity;
import com.example.egyedu.SplashActivity;
import com.example.egyedu.UpdateProfileActivity;
import com.example.egyedu.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    //تعريف عناصر ال  id
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String FirstNameDB, ImageDB;

    public ProfileFragment () {
    }
    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate ( inflater , R.layout.fragment_profile , container , false );
        return binding.getRoot ();
    }
    @Override
    public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated ( view , savedInstanceState );
        getUserdata ();


        binding.btnchangepass.setOnClickListener ( v -> startActivity ( new Intent ( getActivity () , ChangePasswordActivity.class ) ) );
        binding.logout.setOnClickListener ( logout -> {
            ProfileFragment.this.logout ();
        } );

        binding.btnUpdateprof.setOnClickListener ( v -> startActivity ( new Intent ( getActivity () , UpdateProfileActivity.class ) ) );
        binding.btnsitting.setOnClickListener ( v -> startActivity ( new Intent ( getActivity () , SettingActivity.class ) ) );
    }



    @Override
    public void onResume () {
        super.onResume ();
        getUserdata ();
    }

    private void logout () {
        new AlertDialog.Builder ( getActivity () )
                .setTitle ( getString ( R.string.logout ) )
                .setMessage ( getString ( R.string.Are_You_Sure ) )
                .setIcon ( R.drawable.ic_error )
                .setPositiveButton ( getString ( R.string.yes ) , ( dialog , which ) -> {
                    getActivity ().getSharedPreferences ( getString ( R.string.login ) , MODE_PRIVATE )
                            .edit ()
                            .clear ()
                            .apply ();
                    firebaseAuth.signOut ();
                    getActivity ().finish ();
                    startActivity ( new Intent ( getActivity () , SplashActivity.class ) );
                } )
                .setNegativeButton ( getString ( R.string.Cancel ) , null )
                .create ().show ();
    }


    private void getUserdata () {
        firebaseAuth = FirebaseAuth.getInstance ();
        firestore = FirebaseFirestore.getInstance ();
        if (getActivity () != null) {

            if (firebaseAuth.getCurrentUser () != null) {

                //  progressBar.setVisibility(View.VISIBLE);

                String UID = firebaseAuth.getCurrentUser ().getUid ();
                // كود بيرجع الداتا من ال firebase

                DocumentReference documentReference = firestore.collection  ( "users"  )
                        .document ( UID );
                documentReference.get ().addOnCompleteListener ( task -> {

                    if (task.isSuccessful ()) {

                        DocumentSnapshot snapshot = task.getResult ();
                        assert snapshot != null;
                        FirstNameDB = snapshot.getString ( "name" );
                        binding.profileUserName.setText ( "HI  "+FirstNameDB );
                        ImageDB = snapshot.getString ("image" );
                        if (!ImageDB.equals ( "" )) {
                            Glide.with ( getActivity () )
                                    .load ( ImageDB )
                                    .placeholder ( R.drawable.bgsplash )
                                    .into ( binding.profileImageUser );
                            // progressBar.setVisibility(View.VISIBLE);
                        }
                        // لو الداتا رجعت كامله اخفي ال progressBar من علي الشاشة


                    } else {
                        //  progressBar.setVisibility(View.GONE);
                        Toast.makeText ( getActivity () , "Error in task " + task.getException ().getMessage () , Toast.LENGTH_SHORT ).show ();

                    }


                } );
            }

        }
    }


}
