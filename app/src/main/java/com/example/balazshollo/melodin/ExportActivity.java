package com.example.balazshollo.melodin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.balazshollo.melodin.R;

import java.util.ArrayList;
import java.util.List;

public class ExportActivity extends AppCompatActivity {

    private Spinner menuSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        menuSpinner=findViewById(R.id.spinner_export_daily_menu);

        List<String> menus = new ArrayList<>();
        String s1 ="menu1";
        menus.add(s1);
        String s2 ="menu2";
        menus.add(s2);

        String s3 ="menu3";
        menus.add(s3);

        String s4 ="menu4";
        menus.add(s4);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,menus);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        menuSpinner.setAdapter(adapter);



    }
}
