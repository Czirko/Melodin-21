package com.example.balazshollo.melodin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.balazshollo.melodin.api.model.Food;

public class NewElementActivity extends AppCompatActivity {

    public NewElementActivity() {
    }

    private EditText etName;
    private EditText etDesc;
    private EditText etHoreca;
    private EditText etFoodOwner;
    private Spinner spinnerCategory;
    private Button btnBrowse;
    private EditText etIngradiens;
    private EditText etDefPrice;
    private EditText etRedPrice;
    private EditText etFat;
    private EditText etCcal;
    private EditText etCarb;
    private EditText etProtein;
    private Button btnCancel;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_element_root);

        etName=findViewById(R.id.et_new_element_name);
        etDesc=findViewById(R.id.et_new_element_desc);
        etHoreca=findViewById(R.id.et_new_element_horeca);
        etFoodOwner=findViewById(R.id.et_new_element_food_owner);
        spinnerCategory=findViewById(R.id.spinner_new_element_categories);
        btnBrowse=findViewById(R.id.btn_new_element_browse);
        etIngradiens=findViewById(R.id.et_new_element_ingradiens);
        etDefPrice=findViewById(R.id.et_new_element_def_price);
        etRedPrice=findViewById(R.id.et_new_element_reduced_price);
        etFat=findViewById(R.id.et_new_element_fat);
        etCcal=findViewById(R.id.et_new_element_calories);
        etCarb=findViewById(R.id.et_new_element_carb);
        etProtein=findViewById(R.id.et_new_element_protein);
        btnCancel=findViewById(R.id.btn_new_element_cancel);
        btnSave=findViewById(R.id.btn_new_element_save);

       /* btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food food = new Food();
            }
        });*/


    }
}
