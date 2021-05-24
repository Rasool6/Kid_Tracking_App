package com.example.kid_tracking_app1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    TextView txt_sign_up, btn_signin;
    EditText emaill, pinn;
    FirebaseAuth mAuth;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        NavigationView navigationView;
        mAuth = FirebaseAuth.getInstance();
        init();
        txt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAuth.getCurrentUser();
                String emailinput = emaill.getText().toString();
                String pininput = pinn.getText().toString();
                if (TextUtils.isEmpty(emailinput)) {
                    emaill.setError("field cann't be empty");
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
                    emaill.setError("Please enter a valid email address");
                    return;
                } else if (pininput.isEmpty()) {
                    pinn.setError("field cann't be empty");
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(emailinput, pininput)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignInActivity.this, "user successfully logined", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                        finish();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void init() {
        emaill = findViewById(R.id.etemail);
        pinn = findViewById(R.id.etpin);
        txt_sign_up = findViewById(R.id.txt_sign_up);
        btn_signin = findViewById(R.id.btn_SignIn);


    }
}