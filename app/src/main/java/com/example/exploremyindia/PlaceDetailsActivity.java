package com.example.exploremyindia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PlaceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Tour ob = getIntent().getParcelableExtra("tour_item");

        TextView tv = findViewById(R.id.temp_txt);

        tv.setText(ob.getDescription());

    }
}