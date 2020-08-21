package com.example.exploremyindia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mmi.services.api.geocoding.GeoCode;
import com.mmi.services.api.geocoding.GeoCodeResponse;
import com.mmi.services.api.geocoding.MapmyIndiaGeoCoding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLocationsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapboxMap mapmyIndiaMap;
    private MapView mapView;
    String city;

    private ConstraintLayout mBottomSheet;
    private BottomSheetBehavior mBottomSheetBehaviour;

    private TextInputEditText mPlaceTextSearch;
    private Button mAddPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_locations);

        city = getIntent().getStringExtra("City").toString();

        mapView = findViewById(R.id.map_view_city);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mBottomSheet = findViewById(R.id.add_places_bottom_sheet);
        mBottomSheetBehaviour = BottomSheetBehavior.from(mBottomSheet);

        mPlaceTextSearch = findViewById(R.id.txt_place_search);
        mAddPlace = findViewById(R.id.btn_add_place);
    }



    @Override
    public void onMapReady(MapboxMap mapmyIndiaMap) {
        this.mapmyIndiaMap = mapmyIndiaMap;


        mapmyIndiaMap.setPadding(20, 20, 20, 20);


        mapmyIndiaMap.setCameraPosition(setCameraAndTilt());
        if (CheckInternet.isNetworkAvailable(AddLocationsActivity.this)) {
            getGeoCode(city);
        } else {
            Toast.makeText(this, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show();
        }

        mAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = mPlaceTextSearch.getText().toString().trim();
                getGeoCodePlace(place);
            }
        });
    }

    protected CameraPosition setCameraAndTilt() {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(
                28.551087, 77.257373)).zoom(14).tilt(0).build();
        return cameraPosition;
    }



    private void getGeoCode(String geocodeText) {
        MapmyIndiaGeoCoding.builder()
                .setAddress(geocodeText)
                .build().enqueueCall(new Callback<GeoCodeResponse>() {
            @Override
            public void onResponse(Call<GeoCodeResponse> call, Response<GeoCodeResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        List<GeoCode> placesList = response.body().getResults();
                        GeoCode place = placesList.get(0);
                        String add = "Latitude: " + place.latitude + " longitude: " + place.longitude;
                        //addMarker(place.latitude, place.longitude);
                        Toast.makeText(AddLocationsActivity.this, add, Toast.LENGTH_SHORT).show();

                        mapmyIndiaMap.setCameraPosition(new CameraPosition.Builder().target(new LatLng(
                                place.latitude, place.longitude)).zoom(10).tilt(0).build());



                    } else {
                        Toast.makeText(AddLocationsActivity.this, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddLocationsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeoCodeResponse> call, Throwable t) {
                Toast.makeText(AddLocationsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGeoCodePlace(String geocodeText) {
        MapmyIndiaGeoCoding.builder()
                .setAddress(geocodeText)
                .build().enqueueCall(new Callback<GeoCodeResponse>() {
            @Override
            public void onResponse(Call<GeoCodeResponse> call, Response<GeoCodeResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        List<GeoCode> placesList = response.body().getResults();
                        GeoCode place = placesList.get(0);
                        String add = "Latitude: " + place.latitude + " longitude: " + place.longitude;
                        addMarker(place.latitude, place.longitude);
                        Toast.makeText(AddLocationsActivity.this, add, Toast.LENGTH_SHORT).show();

//                        mapmyIndiaMap.setCameraPosition(new CameraPosition.Builder().target(new LatLng(
//                                place.latitude, place.longitude)).zoom(10).tilt(0).build());

                    } else {
                        Toast.makeText(AddLocationsActivity.this, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddLocationsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeoCodeResponse> call, Throwable t) {
                Toast.makeText(AddLocationsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void addMarker(double latitude, double longitude) {
        mapmyIndiaMap.addMarker(new MarkerOptions().position(new LatLng(
                latitude, longitude)));
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