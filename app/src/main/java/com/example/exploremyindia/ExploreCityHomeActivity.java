package com.example.exploremyindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ExploreCityHomeActivity extends AppCompatActivity {

    TextView mPageTitle;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TourCardsAdapter adapter;

    private DatabaseReference mCardRef;
    String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_city_home);

        city = getIntent().getStringExtra("city");
        mCardRef = FirebaseDatabase.getInstance().getReference("Tour_cards");

        handleNavMenu();

        mPageTitle = findViewById(R.id.city_name_page_title);
        mPageTitle.setText(city.toUpperCase());

        setUpRecycler();



    }

    void handleNavMenu(){
        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setSelectedItemId(R.id.item2);

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        startActivity(new Intent(ExploreCityHomeActivity.this, AddNewTourActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.item2:
                        startActivity(new Intent(ExploreCityHomeActivity.this, ExploreMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item3:
                        startActivity(new Intent(ExploreCityHomeActivity.this, ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }

    private void setUpRecycler(){
        recyclerView = findViewById(R.id.city_tour_cards_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        FirebaseRecyclerOptions<TourCardModel> options = new FirebaseRecyclerOptions.Builder<TourCardModel>()
                .setQuery(mCardRef.child(city) , TourCardModel.class )
                .build();

        adapter = new TourCardsAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}