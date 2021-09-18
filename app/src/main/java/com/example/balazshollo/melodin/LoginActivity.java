package com.example.balazshollo.melodin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.balazshollo.melodin.api.MelodinApi;
import com.example.balazshollo.melodin.api.MelodinApiInterface;
import com.example.balazshollo.melodin.api.model.Auth;
import com.example.balazshollo.melodin.api.model.Member;
import com.example.balazshollo.melodin.api.model.User;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    MelodinApiInterface apiInterface;

    String LoginPreferences = "LoginPreferences";

    Button login_button;
    EditText eamilField,passField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //api
        //apiInterface = MelodinApi.getRetrofitInstance().create(MelodinApiInterface.class);

        login_button = (Button) findViewById(R.id.login_button);
        eamilField = findViewById(R.id.email_field);
        passField = findViewById(R.id.password_field);


        /*SharedPreferences prefs = getSharedPreferences(LoginPreferences, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String emailShared = prefs.getString("email", "hgy@iworkshop.hu");//"majd üresre hagyni a def valuet (második érték).
            String passShared = prefs.getString("password", "melodin"); //"majd üresre hagyni a def valuet (második érték).

            eamilField.setText(emailShared);
            passField.setText(passShared);
        }*/

        //final String email = "hgy@iworkshop.hu";

        //final String password = "melodin";

        //final User loginUser = new User(email, password);




        final Intent intentMain = new Intent(this, MainActivity.class);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = eamilField.getText().toString();
                final String password = passField.getText().toString();

/*
                SharedPreferences.Editor editor = getSharedPreferences(LoginPreferences, MODE_PRIVATE).edit();
                editor.putString("email", email);
                editor.putString("password", password);
                //editor.putInt("idName", 12);
                editor.apply();

*/
               // startActivity(intentMain);





                Log.d("testTag",email);
                Log.d("testTag",password);
                User loginUser = new User(email,password);
                Call<Auth> call = MelodinApi.getInstance().getApi().loginUser(loginUser);
                call.enqueue(new Callback<Auth>() {
                    @Override
                    public void onResponse(Call<Auth> call, Response<Auth> response) {
                        Log.d("testTag",response.toString());
                        if(response.isSuccessful()){
                            Log.d("testTag","siker");
                            Auth myAuth = response.body();

                            Log.d("testTag",myAuth.getToken());
                           SharedPreferences preferences = getSharedPreferences("TOKKEN_PREF", MODE_PRIVATE);
                            preferences.edit().putString("token", myAuth.getToken()).commit();

                            MelodinApi.getInstance().token = myAuth.getToken();
                            MelodinApi.getInstance().changeToken(myAuth.getToken());


                            Call<Member> memberCall = MelodinApi.getInstance().member().currentLoggedMember("Bearer " + myAuth.getToken());
                            memberCall.enqueue(new Callback<Member>() {
                                @Override
                                public void onResponse(Call<Member> call, Response<Member> response) {
                                    if (response.isSuccessful()){
                                        Member member = response.body();
                                        intentMain.putExtra("member_data_name", member.getFirstName());
                                        intentMain.putExtra("member_data_email", member.getEmail());
                                        startActivity(intentMain);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Member> call, Throwable t) {

                                }
                            });


                        }else {
                            Toast.makeText(getApplicationContext(), "Wrong Login",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Auth> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                        call.cancel();

                    }
                });




            }
        });
    }

}
