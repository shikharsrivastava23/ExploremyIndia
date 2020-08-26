package com.example.exploremyindia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.mmi.services.api.autosuggest.AutoSuggestCriteria;
import com.mmi.services.api.autosuggest.MapmyIndiaAutoSuggest;
import com.mmi.services.api.autosuggest.model.AutoSuggestAtlasResponse;
import com.mmi.services.api.autosuggest.model.ELocation;
import com.mmi.services.api.geocoding.GeoCode;
import com.mmi.services.api.geocoding.GeoCodeResponse;
import com.mmi.services.api.geocoding.MapmyIndiaGeoCoding;

import java.util.ArrayList;
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
    private Button mNextButton;

    private ArrayList<Tour> mTourList;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Handler handler;


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
        mNextButton = findViewById(R.id.btn_place_next);

        mTourList = new ArrayList<>();

        handleAutoSuggest();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLocationsActivity.this, AddTourDetailsActivity.class);
                intent.putParcelableArrayListExtra("tourlist" , (ArrayList<? extends Parcelable>)mTourList);
                intent.putExtra("City",city);

                startActivity(intent);

            }
        });
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
                        addTourItem(geocodeText, place.latitude, place.longitude);
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

    private void handleAutoSuggest() {

        recyclerView = findViewById(R.id.location_search_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(AddLocationsActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        //recyclerView.setZ(1.0);
        recyclerView.setVisibility(View.GONE);
        handler = new Handler();

        mPlaceTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //handler.postDelayed(() -> {
                    recyclerView.setVisibility(View.GONE);
                    if (s.length() < 3)
                        recyclerView.setAdapter(null);

                    if (s != null && s.toString().trim().length() < 2) {
                        recyclerView.setAdapter(null);
                        return;
                    }

                    if (s.length() > 2) {
                        if (CheckInternet.isNetworkAvailable(AddLocationsActivity.this)) {
                            callAutoSuggestApi(s.toString());
                        } else {
                            //showToast(getString(R.string.pleaseCheckInternetConnection));
                        }
                    }
                //}, 300);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void callAutoSuggestApi(String searchString) {
        MapmyIndiaAutoSuggest.builder()
                .query(searchString)
                .build()
                .enqueueCall(new Callback<AutoSuggestAtlasResponse>() {
                    @Override
                    public void onResponse(Call<AutoSuggestAtlasResponse> call, Response<AutoSuggestAtlasResponse> response) {
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                ArrayList<ELocation> suggestedList = response.body().getSuggestedLocations();
                                if (suggestedList.size() > 0) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    AutoSuggestAdapter autoSuggestAdapter = new AutoSuggestAdapter(suggestedList, eLocation -> {
                                        selectedPlace(eLocation);
                                        recyclerView.setVisibility(View.GONE);
                                    });
                                    recyclerView.setAdapter(autoSuggestAdapter);
                                }
                            } else {
                                //showToast("Not able to get value, Try again.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AutoSuggestAtlasResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

    }


    public void selectedPlace(ELocation eloc){
        mPlaceTextSearch.setText(eloc.placeName.toString());
        mPlaceTextSearch.clearFocus();
        recyclerView.setVisibility(View.GONE);
    }



    private void addMarker(double latitude, double longitude) {
        mapmyIndiaMap.addMarker(new MarkerOptions().position(new LatLng(
                latitude, longitude)));
    }

    private void addTourItem(String name, double latitude, double longitude){
        mTourList.add(new Tour(name,"",latitude,longitude));
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