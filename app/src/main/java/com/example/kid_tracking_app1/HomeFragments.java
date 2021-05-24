package com.example.kid_tracking_app1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragments extends Fragment implements LocationSource.OnLocationChangedListener {
    double str_lati, str_longi;
    String str_pin;
    GoogleMap googleMap1;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap1 = googleMap;
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap1.setMyLocationEnabled(true);
            fetchData();


//            LatLng islamabad = new LatLng(33.6996614, 72.9744281);
//            googleMap.addMarker(new MarkerOptions().position(islamabad).title("Islamabad"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(islamabad,10f));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragments, container, false);
        return view;
    }

    private void fetchData() {
        FirebaseDatabase.getInstance().getReference().child("Child").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

//                    if (snapshot1.child("pin_genrate").getValue().toString().equals("6318")){

                    str_pin = snapshot1.child("pin_genrate").getValue().toString();
                    str_lati = Double.parseDouble(snapshot1.child("latitutde").getValue().toString());
                    str_longi = Double.parseDouble(snapshot1.child("longitude").getValue().toString());
                    LatLng islamabad = new LatLng(str_lati, str_longi);
                    googleMap1.addMarker(new MarkerOptions().position(islamabad));
                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(islamabad, 21f));
                    googleMap1.addMarker(new MarkerOptions().position(islamabad).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.userlocation))));

//                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {
        try {
            View customMarkerView = this.getLayoutInflater().inflate(R.layout.child_marker, null);
            ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
            markerImageView.setImageResource(resId);
            customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
            customMarkerView.buildDrawingCache();
            Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(returnedBitmap);
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
            Drawable drawable = customMarkerView.getBackground();
            if (drawable != null)
                drawable.draw(canvas);
            customMarkerView.draw(canvas);
            return returnedBitmap;
        }catch ( RuntimeException e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
         return null;


    }

    @Override
    public void onLocationChanged(Location location) {
        fetchData();
    }
}