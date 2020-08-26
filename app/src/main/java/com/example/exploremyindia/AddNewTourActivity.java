package com.example.exploremyindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.mmi.services.api.autosuggest.AutoSuggestCriteria;
import com.mmi.services.api.autosuggest.MapmyIndiaAutoSuggest;
import com.mmi.services.api.autosuggest.model.AutoSuggestAtlasResponse;
import com.mmi.services.api.autosuggest.model.ELocation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTourActivity extends AppCompatActivity {

    TextInputEditText mCitySearch;
    Button mNext;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Handler handler;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tour);

        handleNavMenu();

        mCitySearch = findViewById(R.id.txt_city_search);
        mNext = findViewById(R.id.btn_city_next);

        intent = new Intent(AddNewTourActivity.this, AddLocationsActivity.class);

        handleAutoSuggest();

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city_search = mCitySearch.getText().toString().trim().toLowerCase();

                if(TextUtils.isEmpty(city_search)){
                    Toast.makeText(AddNewTourActivity.this, "Please Enter a City", Toast.LENGTH_SHORT).show();
                }else{

                    intent.putExtra("City",city_search);
                    startActivity(intent);
                }
            }
        });
    }

    private void handleAutoSuggest() {

        recyclerView = findViewById(R.id.city_search_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(AddNewTourActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setVisibility(View.GONE);
        handler = new Handler();

        mCitySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.postDelayed(() -> {
                    recyclerView.setVisibility(View.GONE);
                    if (s.length() < 3)
                        recyclerView.setAdapter(null);

                    if (s != null && s.toString().trim().length() < 2) {
                        recyclerView.setAdapter(null);
                        return;
                    }

                    if (s.length() > 2) {
                        if (CheckInternet.isNetworkAvailable(AddNewTourActivity.this)) {
                            callAutoSuggestApi(s.toString());
                        } else {
                            //showToast(getString(R.string.pleaseCheckInternetConnection));
                        }
                    }
                }, 300);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void callAutoSuggestApi(String searchString) {
        MapmyIndiaAutoSuggest.builder()
                .query(searchString)
                .pod(AutoSuggestCriteria.POD_CITY)
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
        mCitySearch.setText(eloc.placeName.toString());
        //intent.putExtra("City_Eloc",eloc.);
    }

    void handleNavMenu(){
        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setSelectedItemId(R.id.item1);

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        startActivity(new Intent(AddNewTourActivity.this, AddNewTourActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.item2:
                        startActivity(new Intent(AddNewTourActivity.this, ExploreMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item3:
                        startActivity(new Intent(AddNewTourActivity.this, ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
}