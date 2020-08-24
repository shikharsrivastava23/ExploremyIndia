package com.example.exploremyindia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTourDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TourDetailsAdapter tourDetailsAdapter;
    public ArrayList<Tour> detail_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour_details);

        Intent intent = getIntent();
        detail_list = intent.getParcelableArrayListExtra("tourlist");

//        for(Tour it: detail_list){
//            System.out.println(it.getPlace_name());
//        }

        recyclerView = findViewById(R.id.detail_recycler_view);
        tourDetailsAdapter = new TourDetailsAdapter(this,detail_list);
        recyclerView.setAdapter(tourDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));


    }
}