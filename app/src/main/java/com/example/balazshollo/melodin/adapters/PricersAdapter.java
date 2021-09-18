package com.example.balazshollo.melodin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balazshollo.melodin.MainActivity;
import com.example.balazshollo.melodin.PricerActivity;
import com.example.balazshollo.melodin.R;
import com.example.balazshollo.melodin.api.model.Food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricersAdapter extends RecyclerView.Adapter<PricersAdapter.ViewHolder> {

    private List<Integer> pricers;
    List<Food> foods;
    private Context context;
    private Map<Integer, Food> foodById;


    public PricersAdapter(List<Integer> pricers, List<Food> foods, Context context) {
        this.pricers = pricers;
        this.context = context;
        this.foods = foods;
        makeMap(foods);

    }

    public PricersAdapter(List<Integer> pricers, Context context) {
        this.pricers = pricers;
        this.context = context;
    }

    private void makeMap(List<Food> foods) {
        foodById = new HashMap<>();
        for (Food f : foods) {
            foodById.put(f.id, f);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pricers_list_item,
                viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ArrayAdapter<Food> adapter = new ArrayAdapter(context, R.layout.spinner_item, foods);
        viewHolder.tvItem.setText("P" + (position + 1));

        viewHolder.spinnerFood.setAdapter(adapter);
        if (pricers.get(position) != -1) {
            Food f = foodById.get(pricers.get(position));
            viewHolder.spinnerFood.setSelection(foods.indexOf(f));
            Log.d("menuCall", "Adapter");
        } else {

        }

       viewHolder.spinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
               Food selectedFood = (Food) adapterView.getSelectedItem();
               pricers.set(position,selectedFood.id);


           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

    }


    @Override
    public int getItemCount() {
        return pricers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;
        public Spinner spinnerFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_pricers_item_Id);
            spinnerFood = itemView.findViewById(R.id.spinner_pricer_listitem);


        }
    }

    public List<Integer> getPricers(){
        return pricers;
    }
}
