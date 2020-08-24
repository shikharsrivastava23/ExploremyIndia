package com.example.exploremyindia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTourDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour_details);

        Intent intent = getIntent();
        final ArrayList<Tour> detail_list = intent.getParcelableArrayListExtra("tourlist");

        for(Tour it: detail_list){
            System.out.println(it.getPlace_name());
        }
    }
}