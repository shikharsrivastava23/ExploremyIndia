package com.example.exploremyindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class CityTourMainActivity extends AppCompatActivity implements OnMapReadyCallback {

    DatabaseReference mItinRef;

    private MapboxMap mapmyIndiaMap;
    private MapView mapView;
    private List<Tour> list;
    private String key_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_tour_main);

        key_id = getIntent().getStringExtra("key_id");

        mItinRef = FirebaseDatabase.getInstance().getReference("Tour_Itinerary");
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        list = new ArrayList<>();
    }

    public void onMapReady(MapboxMap mapmyIndiaMap) {
        this.mapmyIndiaMap = mapmyIndiaMap;


        mapmyIndiaMap.setPadding(20, 20, 20, 20);
//        profileTabLayout.setVisibility(View.VISIBLE);

//        mapmyIndiaMap.setCameraPosition(setCameraAndTilt());
//        if (CheckInternet.isNetworkAvailable(DirectionActivity.this)) {
//            getDirections();
//        } else {
//            Toast.makeText(this, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show();
//        }

        mItinRef.child(key_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Tour touritem = postSnapshot.getValue(Tour.class);
                    list.add(touritem);
                }

                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(
                        list.get(0).getLatitude(), list.get(0).getLongitude())).zoom(10).tilt(0).build();
                mapmyIndiaMap.setCameraPosition(cameraPosition);

                addMarkers(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addMarkers(List<Tour> list) {

        for(Tour item: list){
            mapmyIndiaMap.addMarker(new MarkerOptions().position(new LatLng(
                    item.getLatitude(), item.getLongitude())).title(item.getPlace_name()));
        }
    }



    @Override
    public void onMapError(int i, String s) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}