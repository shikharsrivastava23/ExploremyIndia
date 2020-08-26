package com.example.exploremyindia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
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
//
//                Map<String,String> tc_mp = new HashMap<String,String>();
//
//
//                tc_mp.put("Name","test_name");
//                tc_mp.put("Username","test_user");
//                tc_mp.put("Rating","4.5");
//                tc_mp.put("key_id",key);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String u_name = "";
                if (user != null) {
                    // Name, email address, and profile photo Url
                     u_name = user.getDisplayName();
                }
                TextInputEditText mTourName = findViewById(R.id.tour_name_txt);
                String tour_name = mTourName.getText().toString().trim();

//
                TourCardModel tourCardModel = new TourCardModel(tour_name,u_name.toString(),"0.0",key);

                DatabaseReference tourCard = db.getReference("Tour_cards").child(city_name);
                tourCard.child(key).setValue(tourCardModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(error == null){
                            Toast.makeText(AddTourDetailsActivity.this, "Published!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddTourDetailsActivity.this, ExploreMainActivity.class));
                        }
                    }
                });

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