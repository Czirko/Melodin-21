package com.example.balazshollo.melodin;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.balazshollo.melodin.adapters.FoodAddAutocompleteAdapter;
import com.example.balazshollo.melodin.api.MelodinApi;
import com.example.balazshollo.melodin.api.model.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodAddActivity extends AppCompatActivity {
/*
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private FoodAddAutocompleteAdapter autoSuggestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);


        final AppCompatAutoCompleteTextView autoCompleteTextView =
                findViewById(R.id.auto_complete_edit_text);
        final TextView selectedText = findViewById(R.id.selected_item);

        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = new FoodAddAutocompleteAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        selectedText.setText(autoSuggestAdapter.getObject(position));
                    }
                });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });
    }



    private void makeApiCall(String text) {

        Call<List<Food>> autoFoods = MelodinApi.getInstance().foods().autoCompleteFood("2", text);
        autoFoods.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if(response.isSuccessful()){
                    List<Food> foodList = response.body();

                    List<String> stringList = new ArrayList<>();
                    for (int i = 0; i< foodList.size(); i++){
                        stringList.add(foodList.get(i).name);
                    }

                    //IMPORTANT: set data here and notify
                    autoSuggestAdapter.setData(stringList);
                    autoSuggestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.d("testTag", "auto food complete error");
            }
        });


    }

*/
}
