package com.example.exploremyindia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddTourDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TourDetailsAdapter tourDetailsAdapter;
    public ArrayList<Tour> detail_list;
    public String city_name;
    private MaterialButton mPublish_btn;

    private FirebaseDatabase db;
    private DatabaseReference tour_itenary_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour_details);

        Intent intent = getIntent();
        detail_list = intent.getParcelableArrayListExtra("tourlist");
        city_name = intent.getStringExtra("City");


//        for(Tour it: detail_list){
//            System.out.println(it.getPlace_name());
//        }

        recyclerView = findViewById(R.id.detail_recycler_view);
        mPublish_btn = findViewById(R.id.btn_publish);

        set_up_recycler_view();

        mPublish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance();
                tour_itenary_ref = db.getReference("Tour_Itinerary");

                Map<String,Tour> mp = new HashMap<String,Tour>();
                int id = 0;

                for(Tour ti: detail_list){
                    mp.put(Integer.toString(id),ti);
                    id++;
                }
                DatabaseReference newTourref = tour_itenary_ref.push();
                String key = newTourref.getKey();
                tour_itenary_ref.child(key).setValue(mp);

                Map<String,String> tc_mp = new HashMap<String,String>();

                tc_mp.put("Name","test_name");
                tc_mp.put("Username","test_user");
                tc_mp.put("Rating","4.5");

                DatabaseReference tourCard = db.getReference("Tour_cards").child(city_name);
                tourCard.child(key).setValue(tc_mp);

            }
        });

    }



    private void set_up_recycler_view() {
        tourDetailsAdapter = new TourDetailsAdapter(this,detail_list);
        recyclerView.setAdapter(tourDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    private void update_description_db() {



    }
}