package com.example.balazshollo.melodin;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.balazshollo.melodin.ViewModel.ApiViewModel;
import com.example.balazshollo.melodin.adapters.PricersAdapter;
import com.example.balazshollo.melodin.api.MelodinApi;
import com.example.balazshollo.melodin.api.model.DisplayDailyMenu;
import com.example.balazshollo.melodin.api.model.Food;
import com.example.balazshollo.melodin.api.model.MenuItemWithFoods;
import com.example.balazshollo.melodin.api.model.PricerUpload;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PricerActivity extends AppCompatActivity {
    RecyclerView rvPricers;
    Object pricersJsonObject;
    List<Integer> listOfPrisers;
    List<Food> lstDailyFood;
    PricersAdapter prAdapter;
    private Button btnSavePricers;
    private Integer restaurantId;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricer);
        rvPricers = findViewById(R.id.recyclerVpricers);
        btnSavePricers = findViewById(R.id.btn_save_pricers);
        restaurantId = (int) getIntent().getSerializableExtra("id");
        userToken = (String) getIntent().getSerializableExtra("token");

        rvPricers.setHasFixedSize(false);
        rvPricers.setLayoutManager(new LinearLayoutManager(this));
        /*final PricersAdapter adapter=new PricersAdapter(listOfPrisers,PricerActivity.this);
        this.prAdapter=adapter;
        rvPricers.setAdapter(adapter);*/


        ApiViewModel viewModel = ViewModelProviders.of(this).get(ApiViewModel.class);
        viewModel.getDailyMenu(restaurantId, userToken).observe(this, new Observer<DisplayDailyMenu>() {
            @Override
            public void onChanged(@Nullable DisplayDailyMenu displayDailyMenu) {

                prAdapter = new PricersAdapter(listOfPrisers, makeFoodList(displayDailyMenu), PricerActivity.this);
                rvPricers.setAdapter(prAdapter);

            }
        });

        btnSavePricers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prAdapter != null) {
                    listOfPrisers = prAdapter.getPricers();
                    JSONObject backToApi = makeJsonFromList(listOfPrisers);

                    Log.d("JsonToBack", backToApi.toString());

                    updatePricersOnServer(backToApi);
                    PricerActivity.super.onBackPressed();

                }
            }
        });


        Call<Object> listCall = MelodinApi.getInstance().restaurant().pricerAll(restaurantId, "Bearer " + userToken);
        listCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                pricersJsonObject = response.body();
                Log.d("pricertest", "yeah: " + pricersJsonObject.toString());
                listOfPrisers = parceTheJson(pricersJsonObject);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("pricertest", "no yeah" + t);
            }
        });

        btnSavePricers.setClickable(true);

    }

    public PricerUpload upload(){
        PricerUpload p = new PricerUpload(listOfPrisers);
        return p;
    }

    private void updatePricersOnServer(JSONObject backToApi) {
        //userToken="78b7ba18-d5f0-465a-b705-daeb5c3745d9";
        Call<Object> update= MelodinApi.getInstance().foods().pricerUpdate(restaurantId,"application/json","Bearer " +userToken,backToApi);
        Log.d("updateApi","request: "+update.request().toString());
        update.enqueue(new Callback<Object>() {
        Object res;
        String resp;


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful())
                    //String re=response.body();
                if(res!=null)
                    resp=res.toString();

                Log.d("updateApi", "Call "+call.request().toString());
                Log.d("updateApi", "CallBody "+call.request().body().toString());


                Log.d("updateApi", "response "+response.toString());
                Log.d("updateApi", "Body: "+response.body().toString());

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("updateApi", "fuck :"+t);

            }
        });

    }

    private JSONObject makeJsonFromList(List<Integer> listOfPrisers) {
        String json = "{";
        int index = 1;
        for (Integer i : listOfPrisers) {
            json += "\"" +index + "\" : ";
            if (i != -1) {
                json += i + ",";
            } else {
                json += "null,";
            }
            index++;
        }
        json = json.substring(0, json.length() - 1);
        json += "}";
        JSONObject obj=null;
       /* JsonArray obj=null;
        obj= new JsonArray();
        obj.add(json);
        Log.d("updateApi", "json: "+obj.toString()+obj);*/
        try {
             obj= new JSONObject(json);
        Log.d("updateApi", obj.toString());
        } catch (JSONException e) {
            Log.d("updateApi", "eh"+e);
        }
        return obj;

    }

    public List<Food> makeFoodList(DisplayDailyMenu dMenu) {
        lstDailyFood = new ArrayList<>();
        for (MenuItemWithFoods m : dMenu.data) {
            lstDailyFood.addAll(m.foods);

        }
        Food default4spinner = new Food();
        default4spinner.name = "Kérlek válassz";
        default4spinner.id = -1;
        lstDailyFood.add(0, default4spinner);

        Log.d("menuCall", "csak : )" + lstDailyFood.size());
        return lstDailyFood;
    }


    private List<Integer> parceTheJson(Object object) {
        List<Integer> foodIds = new ArrayList<>();
        int foodId;
        String p = object.toString();
        p = p.replace("{", "");
        p = p.replace("{", "");
        String[] pricersArray = p.split(",");

        for (String s : pricersArray) {
            String[] innerArray = s.split("=");
            foodId=-1;
            for (String d : innerArray)
                Log.d("pricertest", "innerArray: " + d);

                if(innerArray.length>1) {
            try {

                    double foodIdDouble = Double.parseDouble(innerArray[1]);
                    foodId = (int) foodIdDouble;
            } catch (NumberFormatException e) {
                foodId = -1;
            }
                }
            foodIds.add(foodId);
            Log.d("pricertest", "yeah: " + foodId + " listSize:" + foodIds.size());
        }
        return foodIds;
    }


}
