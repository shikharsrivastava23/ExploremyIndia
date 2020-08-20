package com.example.exploremyindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class AddNewTourActivity extends AppCompatActivity {

    TextInputEditText mCitySearch;
    Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tour);

        handleNavMenu();

        mCitySearch = findViewById(R.id.txt_city_search);
        mNext = findViewById(R.id.btn_city_next);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city_search = mCitySearch.getText().toString().trim();

                if(TextUtils.isEmpty(city_search)){
                    Toast.makeText(AddNewTourActivity.this, "Please Enter a City", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AddNewTourActivity.this, AddLocationsActivity.class);
                    intent.putExtra("City",city_search);
                    startActivity(intent);
                }
            }
        });
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