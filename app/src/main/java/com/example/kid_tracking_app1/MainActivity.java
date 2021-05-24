package com.example.kid_tracking_app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView Signup, txt_signin;
    EditText username, email, pin, confrim_pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = username.getText().toString().trim();
                String user_email = email.getText().toString();
                String user_pin = pin.getText().toString();
                String user_confirm_pin = confrim_pin.getText().toString();
                if (TextUtils.isEmpty(user_name)) {
                    username.setError("Please enter username");
                    return;
                } else if (TextUtils.isEmpty(user_email)) {
                    email.setError("Plaese enter your email");
                    return;
                } else if (TextUtils.isEmpty(user_pin)) {
                    pin.setError("Please enter your pin");
                    return;
                } else if (TextUtils.isEmpty(user_confirm_pin)) {
                    confrim_pin.setError("Please enter your confrim pin ");
                    return;
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(user_email, user_pin).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                addMore(user_name, user_email, user_pin, user_confirm_pin);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });
    }
    private void addMore(String user_name, String user_email, String user_pin, String user_confirm_pin) {
        KidModel kidModel = new KidModel(user_name, user_email, user_pin, user_confirm_pin);
        FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(kidModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init() {
        username = findViewById(R.id.et_username);
        email = findViewById(R.id.et_email);
        pin = findViewById(R.id.et_pin);
        confrim_pin = findViewById(R.id.et_confirm_pin);
        Signup = findViewById(R.id.btn_Signup);
        txt_signin = findViewById(R.id.txt_sign_in);

    }
}