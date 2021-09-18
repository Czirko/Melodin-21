package com.example.balazshollo.melodin.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.balazshollo.melodin.R;
import com.example.balazshollo.melodin.api.model.RestaurantDisplay;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDisplayAdapter extends RecyclerView.Adapter<RestaurantDisplayAdapter.RestaurantDisplayHolder>  {

    /*private*/public List<RestaurantDisplay> displayArrayList;
    private ItemClickListener onItemClickListener;

    private int activatedIndex = 0;

    public RestaurantDisplayAdapter(List<RestaurantDisplay> displayArrayList, ItemClickListener onItemClickListener) {
        this.displayArrayList = displayArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    public void setActivatedIndex(int newActivatedIndex) {
        notifyItemChanged(activatedIndex);
        this.activatedIndex = newActivatedIndex;
        notifyItemChanged(activatedIndex);
    }

    public void clearAdapter(){
        this.activatedIndex = 0;
        displayArrayList.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantDisplayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View view = layoutInflater.inflate(R.layout.resturant_display_icon_list,viewGroup,false);

        RestaurantDisplayHolder holder = new RestaurantDisplayHolder(view, onItemClickListener);


        return holder;



        /*
        RecyclerView.ViewHoldre holder = new SomeViewHolder(v);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mClickListener.onClick(view);
        }
    });
    return holder;
        * */
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantDisplayHolder restaurantDisplayHolder, int i) {

        if (i == 0){
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor1_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor1);
            }
        }else if(i == 1){
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor2_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor2);
            }
        }else if(i == 2){
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor3_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor3);
            }
        }else if(i == 3){
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor4_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor4);
            }
        }else if(i == 4){
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor5_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor5);
            }
        }else if(i == 5){
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor6_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor6);
            }
        }else if(i == 6){
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor7_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor7);
            }
        }else{
            if(i == activatedIndex){
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor8_narancs);
            }else{
                restaurantDisplayHolder.display_image.setImageResource(R.drawable.monitor8);
            }
        }
    }

    @Override
    public int getItemCount() {
        return displayArrayList.size();
    }

    class RestaurantDisplayHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView display_image;
        private ItemClickListener onItemClickListener;

        public RestaurantDisplayHolder(@NonNull View itemView, ItemClickListener onItemClickListener) {
            super(itemView);
            this.display_image = (ImageView) itemView.findViewById(R.id.restaurant_display_imageview);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
