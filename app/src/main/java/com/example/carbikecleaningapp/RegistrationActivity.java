package com.example.carbikecleaningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carbikecleaningapp.Data.DataUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private EditText name, email, password, phone, address;
    private Button registration;
    private FirebaseUser user;
    String userId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference().child("messages");
        registration = findViewById(R.id.btn_register);
        name = findViewById(R.id.et_name_reg);
        email = findViewById(R.id.et_email_reg);
        password = findViewById(R.id.et_password_reg);
        phone = findViewById(R.id.et_phone_reg);
        address = findViewById(R.id.et_address);
        progressBar = findViewById(R.id.progressBar_reg);
        progressBar.setVisibility(View.GONE);


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty()) {
                    name.setError("name required!");
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("email required");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("password required");
                } else if (phone.getText().toString().isEmpty()) {
                    phone.setError("phone No. required");
                } else if (address.getText().toString().isEmpty()) {
                    address.setError("address required");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    createUser();
                }

            }
        });

    }

    private void createUser() {

        String mEmail = email.getText().toString().trim();
        String mPassword = password.getText().toString().trim();
        final String mUsername = name.getText().toString();
        final String mPhone = phone.getText().toString().trim();
        final String mAddress = address.getText().toString();

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Register Success!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            userId = user.getUid().toString();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(mUsername).build();
                            user.updateProfile(profileUpdate);

                            DataUser dataUser = new DataUser(mAddress, mPhone, mUsername);
                            mDatabaseRef.child(userId).setValue(dataUser);
                            progressBar.setVisibility(View.GONE);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

    }
}