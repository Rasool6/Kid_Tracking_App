package com.example.kid_tracking_app1;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;


public class
AddKidFragment extends Fragment {
    EditText et_child_name, et_pin_generate, et_child_age;
    TextView btn_generate, btn_save;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_kid, container, false);
        et_child_name = view.findViewById(R.id.et_child_name);
        et_pin_generate = view.findViewById(R.id.et_generate_pin);
        et_child_age = view.findViewById(R.id.et_chil_age);
        btn_generate = view.findViewById(R.id.btn_generate_pin);
        btn_save = view.findViewById(R.id.btn_save);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String child_name = et_child_name.getText().toString();
                String genrate_pin = et_pin_generate.getText().toString();
                String child_age = et_child_age.getText().toString();

                if (TextUtils.isEmpty(child_name)) {
                    et_child_name.setError("Please enter ur name");
                    return;
                } else if (TextUtils.isEmpty(genrate_pin)) {
                    et_pin_generate.setError("PLease Enter ur pin");
                    return;
                } else if (TextUtils.isEmpty(child_age)) {
                    et_child_age.setError("Please Enter ur child age");
                    return;
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    addData(child_name, genrate_pin, child_age);
                }
            }
        });
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        int random_number = random.nextInt(99999);
                        et_pin_generate.setText(String.valueOf(random_number));

                    }
                }, 2000);
            }
        });

    }

    private void addData(String childname, String generatepin, String childage) {

        ChildModel childModel = new ChildModel(childname, generatepin, childage, "0.0", "0.0");
        FirebaseDatabase.getInstance().getReference().child("Child").push().setValue(childModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Child Register", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}