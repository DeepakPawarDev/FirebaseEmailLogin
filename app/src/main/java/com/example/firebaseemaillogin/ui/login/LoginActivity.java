package com.example.firebaseemaillogin.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.firebaseemaillogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    FirebaseApp firebaseApp;
    private static final String TAG = "LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:1018323974700:android:034f2f473cfeea9a") // Required for Analytics.
                .setApiKey("AIzaSyDUHlom1YuebY0cvWquFWSzFcHdRsvqYMU") // Required for Auth.

                // ...
                .build();
        firebaseApp = FirebaseApp.initializeApp(this /* Context */, options, "secondary");

        editTextEmail = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        Button button = ((Button) findViewById(R.id.login));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        Button buttonLogin = ((Button) findViewById(R.id.btn_login));
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        Button buttonVerify = ((Button) findViewById(R.id.btn_verified));
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVerified();
            }
        });

        Button buttonSendEmailVerify = ((Button) findViewById(R.id.btn_send_verification));
        buttonSendEmailVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendVerification();
            }
        });
    }


    private void login(){

        FirebaseAuth.getInstance(firebaseApp).signOut();
        Task<AuthResult> firebaseUser = FirebaseAuth.getInstance(firebaseApp).signInWithEmailAndPassword(editTextEmail.getText().toString(),
                editTextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d(TAG, "Log in" );

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Log in failed"+e );

            }
        });



    }
    private void registerUser() {

        FirebaseAuth auth = FirebaseAuth.getInstance(firebaseApp);
        auth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
                editTextPassword.getText().toString())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "failed" + e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Login", "Added");
                        }
                    }
                });
    }


    private void sendVerification() {


        FirebaseUser firebaseUser = FirebaseAuth.getInstance(firebaseApp).getCurrentUser();

        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "verification email send");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "verification email not send");
            }
        });
    }

    public void isVerified() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance(firebaseApp).getCurrentUser();
        if (firebaseUser != null) {

            boolean isUserVerified = firebaseUser.isEmailVerified();
            Log.i(TAG, "" + isUserVerified);
        }

    }
}
