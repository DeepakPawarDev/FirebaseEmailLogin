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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

EditText editTextEmail,editTextPassword;
    FirebaseApp firebaseApp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:1018323974700:android:034f2f473cfeea9a") // Required for Analytics.
                .setApiKey("AIzaSyDUHlom1YuebY0cvWquFWSzFcHdRsvqYMU") // Required for Auth.

                // ...
                .build();
         firebaseApp=  FirebaseApp.initializeApp(this /* Context */, options, "secondary");

        editTextEmail=(EditText)findViewById(R.id.username) ;
        editTextPassword=(EditText)findViewById(R.id.password);
        Button button= ((Button)findViewById(R.id.login));
        button .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){

        System.out.println("Clicked");

        //FirebaseApp.initializeApp(this);

        FirebaseAuth auth = FirebaseAuth.getInstance(firebaseApp);
        auth.signInWithEmailAndPassword(editTextEmail.getText().toString(),
                editTextPassword.getText().toString())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("failed"+e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Login", "Added");
                }
                System.out.println("******************");
            }
        });
    }

}
