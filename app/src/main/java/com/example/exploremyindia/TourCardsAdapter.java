package com.example.exploremyindia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class TourCardsAdapter extends FirebaseRecyclerAdapter<TourCardModel , TourCardsAdapter.TourCardsViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TourCardsAdapter(@NonNull FirebaseRecyclerOptions<TourCardModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TourCardsViewHolder holder, int position, @NonNull TourCardModel model) {
        holder.mCardTitle.setText(model.getName());
        holder.mCardUName.setText(model.getUsername());
        holder.mCardRating.setText(model.getRating());
    }

    @NonNull
    @Override
    public TourCardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_card,parent,false);

        TourCardsViewHolder holder = new TourCardsViewHolder(view);

        return holder;
    }

    public class TourCardsViewHolder extends RecyclerView.ViewHolder {
        private TextView mCardTitle;
        private TextView mCardUName;
        private TextView mCardRating;
        private MaterialButton mViewItinerary;

        public TourCardsViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardTitle = itemView.findViewById(R.id.tour_card_title);
            mCardUName = itemView.findViewById(R.id.tour_card_uname);
            mCardRating = itemView.findViewById(R.id.tour_card_rating);
            mViewItinerary = itemView.findViewById(R.id.tour_card_next_btn);
        }
    }


}
