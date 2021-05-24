package com.example.kid_tracking_app1;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class HistroyFragment extends Fragment {

    String lat, lon, pin_id, time;
    FirebaseAuth mAuth;
    TextView address, timee;
    LinearLayout linearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        address = view.findViewById(R.id.txtaddress);
        timee = view.findViewById(R.id.timee);
        linearLayout=view.findViewById(R.id.dynamicLy);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histroy, container, false);
        histroyData(String.valueOf(lat), String.valueOf(lon));


        return view;
    }

    private void histroyData(String s, String valueOf) {
        FirebaseDatabase.getInstance().getReference().child("History_Data").addValueEventListener(new ValueEventListener() {

            @SuppressLint("WrongConstant")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {


                    try {
                        double lat = Double.parseDouble(snapshot1.child("lat").getValue().toString());
                        double lon = Double.parseDouble(snapshot1.child("lon").getValue().toString());
                        pin_id = snapshot1.child("pinId").getValue().toString();
                        time = snapshot1.child("time_ofAddingHistory").getValue().toString();

                        Log.d("lati", String.valueOf(lat));
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addresses = null;
                        addresses = geocoder.getFromLocation(lat, lon, 1);
                        String cityName = addresses.get(0).getLocality();
                        String stateName = addresses.get(0).getAddressLine(1);
                        String countryName = addresses.get(0).getCountryName();
                        Log.d("city Name",cityName);
                        Log.d("country Name",countryName);
                        LinearLayout linearLayout1=new LinearLayout(getContext());
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                        params2.setMargins(8,8,8,8);

                        TextView tv_address=new TextView(getContext());
                        TextView tv_time=new TextView(getContext());
                        TextView tv=new TextView(getContext());
                        tv.setText("                                              ");
                        tv_address.setText(cityName+","+countryName);
                        tv_time.setText(time);
                        tv_time.setGravity(View.TEXT_ALIGNMENT_TEXT_END);
                        linearLayout1.setLayoutParams(params2);
                        linearLayout1.addView(tv_address);
                        linearLayout1.addView(tv);
                        linearLayout1.addView(tv_time);
                        linearLayout.addView(linearLayout1);

                    } catch (IOException | RuntimeException e ) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    }

