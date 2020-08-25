package com.example.exploremyindia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CityTourMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_tour_main);

        String key_id = getIntent().getStringExtra("key_id");
        TextView tv = findViewById(R.id.textView7);
        tv.setText(key_id);
    }
}