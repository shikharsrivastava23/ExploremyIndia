package com.example.exploremyindia;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TourDetailsAdapter extends RecyclerView.Adapter<TourDetailsAdapter.TourDetailsViewHolder> {

    private LayoutInflater inflater;
    public ArrayList<Tour> TourItemsArrayList;

    public TourDetailsAdapter(Context ctx, ArrayList<Tour> TourItemsArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.TourItemsArrayList = TourItemsArrayList;
    }

    @NonNull
    @Override
    public TourDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.tour_description_item,parent,false);
        TourDetailsViewHolder holder = new TourDetailsViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TourDetailsViewHolder holder, int position) {
        holder.place_title_text_view.setText(TourItemsArrayList.get(position).getPlace_name());
        holder.editText.setText(TourItemsArrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return TourItemsArrayList.size();
    }

    class TourDetailsViewHolder extends RecyclerView.ViewHolder{
        protected TextInputEditText editText;
        protected TextView place_title_text_view;

        public TourDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.description_edittext);
            place_title_text_view = itemView.findViewById(R.id.place_title);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    TourItemsArrayList.get(getAdapterPosition()).setDescription(editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
}
