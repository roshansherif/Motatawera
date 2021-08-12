package com.example.egyedu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.egyedu.databinding.ActivityUpdateProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {
    private ActivityUpdateProfileBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    ImageView back;
    String NameDB, ImageDB;

    private StorageReference storageReference;
    private Uri imageUri = null;
    private static String ImageURL = null;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        binding = DataBindingUtil.setContentView ( this , R.layout.activity_update_profile );
        firestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();
        storageReference = FirebaseStorage.getInstance ().getReference ();
        back = findViewById ( R.id.back1 );

        back.setOnClickListener ( v -> {
            finish ();
        } );

        getUserdata ();

        binding.selectPhoto.setOnClickListener ( v -> {
            checkPermission ();

        } );
        binding.btnSaveUpdateProfile.setOnClickListener ( v -> {
            checkValidation ();

        } );
    }

    private void getUserdata () {


        if (firebaseAuth.getCurrentUser () != null) {

            //  progressBar.setVisibility(View.VISIBLE);

            String UID = firebaseAuth.getCurrentUser ().getUid ();
            // كود بيرجع الداتا من ال firebase

            DocumentReference documentReference = firestore.collection ( "users" )
                    .document ( UID );

            documentReference.get ().addOnCompleteListener ( task -> {

                if (task.isSuccessful ()) {

                    DocumentSnapshot snapshot = task.getResult ();
                    assert snapshot != null;
                    NameDB = snapshot.getString ( "name" );
                    ImageDB = snapshot.getString ( "image" );
                    binding.studentName.setText ( NameDB );
                    if (!ImageDB.equals ( "" )) {
                        Glide.with ( this )
                                .load ( ImageDB )
                                .placeholder ( R.drawable.bgsplash )
                                .into ( binding.studentPhoto );
                        // progressBar.setVisibility(View.VISIBLE);
                    }
                    // لو الداتا رجعت كامله اخفي ال progressBar من علي الشاشة
                } else {
                    //  progressBar.setVisibility(View.GONE);
                    Toast.makeText ( this , "Error in task " + task.getException ().getMessage () , Toast.LENGTH_SHORT ).show ();
                }
            } );
        }
    }

    private void checkValidation () {
        String name = binding.studentName.getText ().toString ().trim ();
        if (name.isEmpty ()) {
            binding.studentName.requestFocus ();
            return;
        }
        //  hideKeyboard(this);
        if (imageUri != null) uploadImage ();
        else saveChange ();
    }
    void showAlert ( String msg ) {
        new AlertDialog.Builder ( this )
                .setTitle ( "Error" )
                .setMessage ( msg )
                .setIcon ( R.drawable.ic_error )
                .setPositiveButton ( "OK" , null )
                .create ().show ();
    }
    private void uploadImage () {
        // chick if user image is null or not
        if (ImageDB != null) {
            String userID = firebaseAuth.getCurrentUser ().getUid ();
            // mack progress bar dialog
            final ProgressDialog progressDialog = new ProgressDialog ( this );
            progressDialog.setTitle ( "Please wait.." );
            progressDialog.setCancelable ( false );
            progressDialog.show ();
            // mack collection in fireStorage
            final StorageReference ref = storageReference.child ( "profile_image_user" ).child ( userID + ".jpg" );
            // get image user and give to imageUserPath
            ref.putFile ( imageUri ).addOnProgressListener ( taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred ()) / taskSnapshot.getTotalByteCount ();
                progressDialog.setMessage ( "upload " + (int) progress + "%" );
            } ).continueWithTask ( task -> {
                if (!task.isSuccessful ()) {
                    throw task.getException ();
                }
                return ref.getDownloadUrl ();
            } ).addOnCompleteListener ( task -> {
                if (task.isSuccessful ()) {
                    progressDialog.dismiss ();
                    Uri downloadUri = task.getResult ();
                    assert downloadUri != null;
                    ImageURL = downloadUri.toString ();
                    progressDialog.dismiss ();
                    saveChange ();
                } else {
                    progressDialog.dismiss ();
                    Toast.makeText ( UpdateProfileActivity.this , " Error in addOnCompleteListener " + task.getException ().getMessage () , Toast.LENGTH_SHORT ).show ();
                }
            } );
        }
    }
    private void checkPermission () {
        //use permission to READ_EXTERNAL_STORAGE For Device >= Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission ( UpdateProfileActivity.this , Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                // to ask user to reade external storage
                ActivityCompat.requestPermissions ( UpdateProfileActivity.this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 1 );
            } else {
                OpenGalleryImagePicker ();
            }
            //implement code for device < Marshmallow
        } else {
            OpenGalleryImagePicker ();
        }
    }

    private void OpenGalleryImagePicker () {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity ()
                .setGuidelines ( CropImageView.Guidelines.ON ) // to show option camera, gallery, derive
                .setAllowFlipping ( false ) // to set width equal height
                .setAspectRatio ( CropImageView.MEASURED_SIZE_MASK , CropImageView.MEASURED_SIZE_MASK ) // to set min width and height "parameter is min of image"
                .start ( this ); // to start cropping activity
    }

    @Override
    protected void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult ( requestCode , resultCode , data );


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult ( data );

            if (resultCode == RESULT_OK) {

                imageUri = result.getUri ();
                binding.studentPhoto.setImageURI ( imageUri );

                uploadImage ();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError ();
                Toast.makeText ( UpdateProfileActivity.this , "Error : " + error , Toast.LENGTH_LONG ).show ();

            }
        }
    }

    private void saveChange () {

        // mack progress bar dialog
        final ProgressDialog progressDialog = new ProgressDialog ( this );
        progressDialog.setTitle ( "Please wait.." );
        progressDialog.setCancelable ( false );
        progressDialog.show ();

        String userID = firebaseAuth.getCurrentUser ().getUid ();

        Map<String, Object> map = new HashMap<> ();
        map.put ( "name" , binding.studentName.getText ().toString ().trim () );


        String url = null;
        if (ImageURL == null) {

            url = ImageDB;
        } else
            url = ImageURL;

        map.put ( "image" , url );

        firestore.collection ( "users" )
                .document ( userID )
                .update ( map )
                .addOnCompleteListener ( task -> {
                    if (task.isSuccessful ()) {


                        progressDialog.dismiss ();
                        new AlertDialog.Builder ( this )
                                .setMessage ( "profile update successfully!" )
                                .setCancelable ( false )
                                .setPositiveButton ( "OK" , ( dialog , which ) -> {

                                    finish ();
                                } )
                                .create ()
                                .show ();
                    } else {
                        Toast.makeText ( this , "error " + task.getException () , Toast.LENGTH_SHORT ).show ();
                        progressDialog.dismiss ();
                    }
                } );

    }

}