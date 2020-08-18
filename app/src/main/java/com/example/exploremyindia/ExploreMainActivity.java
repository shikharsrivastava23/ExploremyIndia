package com.example.exploremyindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExploreMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_main);

        handleNavMenu();

    }

    void handleNavMenu(){
        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setSelectedItemId(R.id.item2);

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        startActivity(new Intent(ExploreMainActivity.this, AddNewTourActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.item2:
                        startActivity(new Intent(ExploreMainActivity.this, ExploreMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item3:
                        startActivity(new Intent(ExploreMainActivity.this, ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }

}