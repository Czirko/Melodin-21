package com.example.balazshollo.melodin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.balazshollo.melodin.R;
import com.example.balazshollo.melodin.api.model.Restaurant;

import java.util.ArrayList;

public class RestaurantNameAdapter extends RecyclerView.Adapter<RestaurantNameAdapter.RestaurantNameHolder> {

    private ArrayList<Restaurant> resturantList;

    public RestaurantNameAdapter(ArrayList<Restaurant> resturantList) {
        this.resturantList = resturantList;
    }

    @NonNull
    @Override
    public RestaurantNameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.restaurant_name_row,viewGroup,false);
        return new RestaurantNameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantNameHolder restaurantNameHolder, int i) {
        restaurantNameHolder.nameTextField.setText(resturantList.get(i).name);
    }

    @Override
    public int getItemCount() {
        return resturantList.size();
    }

    class RestaurantNameHolder extends RecyclerView.ViewHolder{
        TextView nameTextField;

        public RestaurantNameHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextField = itemView.findViewById(R.id.restaurant_name_textfield);
        }
    }
}

