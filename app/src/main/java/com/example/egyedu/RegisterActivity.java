package com.example.egyedu;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //view
    private EditText edEmail, edphone, edname, edpassword, edconfirmpassword;
    private ProgressBar progressBar;
    //firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        //findView

        edEmail = findViewById(R.id.email_register);
        edpassword = findViewById(R.id.password_register);
        edname = findViewById(R.id.name_register);
        edphone = findViewById(R.id.phone_register);
        edconfirmpassword = findViewById(R.id.confirm_password_register);
        progressBar = findViewById(R.id.progressbar_sign_in);


        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //on click to sign in new user
        findViewById(R.id.signup).setOnClickListener(v -> {
            validationData();
        });

        //on click to finish this activity
        findViewById(R.id.close_register).setOnClickListener(v -> {
            finish();
        });

    }

    private void validationData() {
        String email = edEmail.getText().toString().trim();
        String phone = edphone.getText().toString().trim();
        String name = edname.getText().toString().trim();
        String password = edpassword.getText().toString().trim();
        String confirmpassword = edconfirmpassword.getText().toString().trim();


        if (email.isEmpty()) {
            edEmail.requestFocus();
            showAlert (getString ( R.string.valid_email ) );
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.requestFocus();
            showAlert (getString ( R.string.invalid_email ) );
            return;
        }
        if (password.isEmpty()) {
            edpassword.requestFocus();
            showAlert (getString ( R.string.validpassword ) );
            return;
        }
        if (password.length() < 6) {
            edpassword.requestFocus();
            showAlert (getString ( R.string.invalid_password ) );
            return;
        }
        if (confirmpassword.isEmpty()) {
            edconfirmpassword.requestFocus();
            showAlert (getString ( R.string.confirm_valid_password ) );
            return;
        }
        if (confirmpassword.length() < 6) {
            edconfirmpassword.requestFocus();
            showAlert(getString ( R.string.confirm_invalid_password ));
            return;
        }
        if (!password.equals(confirmpassword)) {
            showAlert(getString ( R.string.not_equal_password ));
            return;
        }
        if (name.isEmpty()) {
            edname.requestFocus();
            showAlert(getString ( R.string.name_required ));
            return;
        }
        if (phone.isEmpty()) {
            edphone.requestFocus();
            showAlert(getString ( R.string.phone_required ));
            return;
        }
        if (phone.length() > 11) {
            edphone.requestFocus();
            showAlert(getString ( R.string.invalid_phone ));
            return;
        }

        signUpWithFirebase(email, password);

    }

    private void signUpWithFirebase(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserData();
                        Toast.makeText(this, ("Data Saved" ), Toast.LENGTH_SHORT).show();

                    } else {
                        progressBar.setVisibility(View.GONE);


                        showAlert(task.getException().getLocalizedMessage());
                    }
                });
    }

    private void saveUserData() {
        if (firebaseAuth.getCurrentUser() != null) {
            String userID = firebaseAuth.getCurrentUser().getUid();
            Map<String, Object> user = new HashMap<>();
            user.put("email", edEmail.getText().toString().trim());
            user.put("password", edpassword.getText().toString().trim());
            user.put("name", edname.getText().toString().trim());
            user.put("Phone", edphone.getText().toString().trim());
            user.put("id", userID);
            user.put("image", "");


            firestore.collection("users")
                    .document(userID)
                    .set(user)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            new AlertDialog.Builder(this)
                                    .setTitle(getString ( R.string.Congratulation ))
                                    .setMessage(getString ( R.string.Account_created_successful ))
                                    .setIcon(R.drawable.ic_done)
                                    .setPositiveButton(getString ( R.string.ok ), (dialog, which) -> {

                                      //  startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    })
                                    .create()
                                    .show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            showAlert("Error\n" + task.getException().getMessage());
                        }
                    });
        }
    }

    void showAlert(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(msg)
                .setIcon(R.drawable.ic_error)
                .setPositiveButton(getString ( R.string.ok ), null)
                .create().show();
    }
}