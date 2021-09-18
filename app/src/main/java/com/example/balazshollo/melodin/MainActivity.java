package com.example.balazshollo.melodin;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.balazshollo.melodin.adapters.DeleteChildrowInterface;
import com.example.balazshollo.melodin.adapters.ExpandableListAdapter;
import com.example.balazshollo.melodin.adapters.FoodAddAutocompleteAdapter;
import com.example.balazshollo.melodin.adapters.ItemClickListener;
import com.example.balazshollo.melodin.adapters.NavExpendableListAdapter;
import com.example.balazshollo.melodin.adapters.RestaurantDisplayAdapter;
import com.example.balazshollo.melodin.api.MelodinApi;
import com.example.balazshollo.melodin.api.model.Category;
import com.example.balazshollo.melodin.api.model.DisplayDailyMenu;
import com.example.balazshollo.melodin.api.model.Food;
import com.example.balazshollo.melodin.api.model.FoodAutoComplete;
import com.example.balazshollo.melodin.api.model.FoodUpload;
import com.example.balazshollo.melodin.api.model.MenuItemWithFoods;
import com.example.balazshollo.melodin.api.model.PriceChangeUpload;
import com.example.balazshollo.melodin.api.model.Restaurant;
import com.example.balazshollo.melodin.api.model.RestaurantDisplay;
import com.example.balazshollo.melodin.api.model.SpecMenuIdFoods;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DeleteChildrowInterface {


    RecyclerView recyclerView;
    NavigationView navigationView;


    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;

    private ExpandableListView navListView;
    private NavExpendableListAdapter navAdapter;
    private String acceptLanguage;


    TextView resturantDataTextView;
    TextView resturantLocationTextView;
    TextView tvRestaurantPhone;
    TextView resturantDisplayTextView;

    ImageView navListLastSelectedChildIv;

    MenuItem addFood;
    //Button addFoodPopUpButton;

    String userToken = "";

    // letöltött éttermek főadatait tároljuk
    List<Restaurant> restaurants;

    // selected restaurant's id
    int selectedRestaurantId = -1;


    // displayeket rakjuk bele
    List<RestaurantDisplay> restaurantDisplayListArray;

    // menüket pakoljuk bele
    List<DisplayDailyMenu> displayDailyMenuArrayList;
     List<Food> lstDailyFood;

    // displayek adaptere
    RestaurantDisplayAdapter restaurantDisplayAdapter;


    // -- food addView and adapter
    Dialog foodAddDialog;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handlerFood;
    private FoodAddAutocompleteAdapter autoSuggestAdapter;
    private Button btnPricers;



    // category endpointtal lekért kategoriákat rakjuk bele
    Category categoriesForUpload;
    // ha választottunk kategoriát ide kerül ha normál akkor null
    Integer categoryIdorNot = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intentMain = getIntent();
        String member_data_name = intentMain.getStringExtra("member_data_name");
        String member_data_email = intentMain.getStringExtra("member_data_email");

        if (categoriesForUpload == null) {
            Log.d("testTag", "üres category");
        } else {
            Log.d("testTag", "van benne valami category");
        }


        SharedPreferences preferences = getSharedPreferences("TOKKEN_PREF", MODE_PRIVATE);
        userToken = preferences.getString("token", null);


        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        acceptLanguage="hu";


        restaurantDisplayListArray = new ArrayList<>();
        displayDailyMenuArrayList = new ArrayList<>();

        listAdapter = new ExpandableListAdapter(this, displayDailyMenuArrayList, this);

        expandableListView.setAdapter(listAdapter);
        View header = getLayoutInflater().inflate(R.layout.restorant_data_and_location_list_header, null);
        expandableListView.addHeaderView(header);


        // feliratkozunk ha változik az expanded list adapterében az adat és akkor az összes groupot kinyitjuk
        listAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                for (int i = 0; i < listAdapter.getGroupCount(); i++)
                    expandableListView.expandGroup(i);
            }
        });


        // ezzel nem lehet kinyitni se becsukni
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true; // This way the expander cannot be collapsed
            }
        });


        //------------navigation, slide menu -------------------

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        resturantDataTextView = findViewById(R.id.restaurant_data_textfield);
        tvRestaurantPhone=findViewById(R.id.tv_phonenumber_restorant);
        resturantLocationTextView = findViewById(R.id.resturant_location_textView);
        resturantDisplayTextView = findViewById(R.id.resturant_displayname_textView);

        btnPricers=findViewById(R.id.btn_pricers);


        View headerview = navigationView.getHeaderView(0);

        Button logoutButton = (Button) headerview.findViewById(R.id.logout_button);
        TextView loggedUserText = headerview.findViewById(R.id.logged_user_name);
        ImageView loggedUserImageView = headerview.findViewById(R.id.imageView);

        String hashsss = MD5Util.md5Hex(member_data_email);
        String urlString = "http://www.gravatar.com/avatar/" + hashsss + "?s=204&d=404";

        Picasso.get()
                .load(urlString)
                .placeholder(R.drawable.user_feher)
                .into(loggedUserImageView);

        loggedUserText.setText(member_data_name);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //------------navigation slide menu vége -------------------


        // kaja hozzáadás kezelése
        //addFoodPopUpButton = (Button) findViewById(R.id.food_add_popup);
        //addFoodPopUpButton.setVisibility(View.INVISIBLE);


        // download all restaurants
        Call<ArrayList<Restaurant>> listCall = MelodinApi.getInstance().restaurant().restaurantsAll("Bearer " + userToken);
        listCall.enqueue(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, retrofit2.Response<ArrayList<Restaurant>> response) {
                if (response.isSuccessful()) {
                    restaurants = response.body();
                    generateNamesForMenu2(response.body());
                    navMenuList(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.d("testTag", "resturant error");
            }
        });


        ItemClickListener listener = (view, position) -> {
            Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            Log.d("testTag", "Position " + position);
            resturantDisplayTextView.setText(restaurantDisplayListArray.get(position).name);

            restaurantDisplayAdapter.setActivatedIndex(position);

            listAdapter.setWhichDisplay(position);
            listAdapter.notifyDataSetChanged();

        };
        btnPricers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPricers = new Intent(getApplicationContext(), PricerActivity.class);
                intentPricers.putExtra("id",selectedRestaurantId);
                intentPricers.putExtra("token",userToken);
                //intentPricers.putExtra("foods",lstDailyFood);
                startActivity(intentPricers);
            }
        });

        restaurantDisplayAdapter = new RestaurantDisplayAdapter(restaurantDisplayListArray, listener);
        recyclerView = findViewById(R.id.restaurant_recycler_display_view);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(restaurantDisplayAdapter);
        recyclerView.setPadding(5, 0, 5, 0);


        foodAddDialog = new Dialog(this);


    }


    private void generateNamesForMenu2(ArrayList<Restaurant> restaurants) {
        /*Menu menu = navigationView.getMenu();
        for (int i = 0; i < restaurants.size(); i++) {
            menu.add(R.id.test_menu_s, restaurants.get(i).id, 0, restaurants.get(i).name).setIcon(R.drawable.ic_menu_camera);
        }*/
    }


    // legenerálom a feltöltendő kaják objektumát ha sima kaja ami carte akkor category id null
    private void generateUploadFood(Integer categoryId, int foodId) {
        // ebbe gyűjtjük a feltöltendő kajákat
        FoodUpload foodUpload = new FoodUpload();

        Boolean shouldReload = false;

        HashMap<Integer, List<Integer>> listHash;
        listHash = new HashMap<>();


        for (int i = 0; i < displayDailyMenuArrayList.size(); i++) {
            if (displayDailyMenuArrayList.get(i).customType.equals("carte")) {
                Log.d("testTag", "micsoda carte bem");
                for (int j = 0; j < displayDailyMenuArrayList.get(i).data.size(); j++) {

                    for (int ik = 0; ik < displayDailyMenuArrayList.get(i).data.get(j).foods.size(); ik++) {
                        if (!foodUpload.foodIds.contains(displayDailyMenuArrayList.get(i).data.get(j).foods.get(ik).id)) {
                            foodUpload.foodIds.add(displayDailyMenuArrayList.get(i).data.get(j).foods.get(ik).id);
                        }
                    }


                }
            } else if (displayDailyMenuArrayList.get(i).customType.equals("special")) {
                for (int hs = 0; hs < displayDailyMenuArrayList.get(i).data.size(); hs++) {
                    List<Integer> listFoods = listHash.get(displayDailyMenuArrayList.get(i).data.get(hs).id);
                    if (listFoods != null) {
                        // ide azt kell megvalositani hogy már egy adott kategoria létezik azt kivesszük és megnézzük szerepel e id benne ha nem hozzáadjuk

                        for (int belso = 0; belso < displayDailyMenuArrayList.get(i).data.get(hs).foods.size(); belso++) {
                            if (!listFoods.contains(displayDailyMenuArrayList.get(i).data.get(hs).foods.get(belso).id)) {
                                listFoods.add(displayDailyMenuArrayList.get(i).data.get(hs).foods.get(belso).id);
                            }
                        }

                    } else {
                        List<Integer> foodIdArray = new ArrayList<>();
                        for (int a = 0; a < displayDailyMenuArrayList.get(i).data.get(hs).foods.size(); a++) {
                            foodIdArray.add(displayDailyMenuArrayList.get(i).data.get(hs).foods.get(a).id);
                        }
                        listHash.put(displayDailyMenuArrayList.get(i).data.get(hs).id, foodIdArray);
                    }
                }


            }
        }


        // ellenörizzük benne van e a kiválasztott id a régiekben

        // sima categoriátlan foodIdsben
        if (categoryId == null) {
            if (!foodUpload.foodIds.contains(foodId)) {
                foodUpload.foodIds.add(foodId);
                shouldReload = true;
            }
        } else {
            // special esetén

            List<Integer> someCategory = listHash.get(categoryId);
            if (someCategory == null) {
                List<Integer> foodnewArray = new ArrayList<>();
                foodnewArray.add(foodId);
                listHash.put(categoryId, foodnewArray);
                shouldReload = true;
            } else {
                if (!someCategory.contains(foodId)) {
                    someCategory.add(foodId);
                    shouldReload = true;
                }
            }
        }

        // specmenöket hozzáadom a feltöltendő uploadfoodhoz

        for (Map.Entry<Integer, List<Integer>> map : listHash.entrySet()) {
            SpecMenuIdFoods specmenu = new SpecMenuIdFoods();
            specmenu.id = map.getKey();
            specmenu.foods = map.getValue();
            foodUpload.specMenus.add(specmenu);
            Log.d("testTag", "hash ciklusban " + map.getKey() + " --- " + map.getValue());
        }

        Gson gson = new Gson();
        String toJson = gson.toJson(foodUpload);
        Log.d("testTag", "kaja kiloggolas " + toJson);

        uploadFoodString(foodUpload, shouldReload);
    }


    public void uploadFoodString(FoodUpload foodUpload, Boolean shouldRefresh) {
        // amig nem megy dátumra addig hozzáadjuk a dailymenuid -t a feltöltéshez
        int dailyMenuId = -0;
        if (displayDailyMenuArrayList.size() > 0) {
            dailyMenuId = displayDailyMenuArrayList.get(0).dailyMenuId;
        }

        Call<Void> foodUpdateServer = MelodinApi.getInstance().foods().foodChanges(selectedRestaurantId, "Bearer " + userToken, "hu", foodUpload/*foods*/, dailyMenuId, "application/json", "application/json");
        foodUpdateServer.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                Log.d("testTag", "upload siker");

                foodAddDialog.dismiss();
                //               refreshRedownloadRestauranDisplays();
                Log.d("testTag", "upload messsage: " + response.toString());
                if (shouldRefresh == true) {
                    refreshEachDisplay();
                }


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("testTag", "upload hib");
            }
        });
    }

    public void refreshEachDisplay() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(calendar.getTime());

        for (int i = 0; i < restaurantDisplayListArray.size(); i++) {
            downloadRefreshedDailymenu(restaurantDisplayListArray.get(i), i, format);
        }


    }

    private void downloadRefreshedDailymenu( RestaurantDisplay restaurantDisplay, int whichIs, String format) {
        Call<DisplayDailyMenu> displayDailyMenuCall = MelodinApi.getInstance().restaurant().showDisplayMenuAtDate(format, "Bearer " + restaurantDisplay.accessToken);

        displayDailyMenuCall.enqueue(new Callback<DisplayDailyMenu>() {
            @Override
            public void onResponse(Call<DisplayDailyMenu> call, retrofit2.Response<DisplayDailyMenu> response) {
                if (response.isSuccessful()) {
                    DisplayDailyMenu displayDailyMenu = response.body();

                    displayDailyMenu.customType = restaurantDisplay.type;
                    displayDailyMenuArrayList.set(whichIs, displayDailyMenu);
                    //makeFoodList(displayDailyMenu);
                    // frissitjük hogy megjelenjenek az adatok
                    listAdapter.notifyDataSetChanged();
                } else {
                    //dataModelCreateForJustDisplay(restaurantDisplay);
                }
            }

            @Override
            public void onFailure(Call<DisplayDailyMenu> call, Throwable t) {
                Log.d("testTag", "displayDailyMenuCall valami nem jó");
                Log.d("testTag", t.getLocalizedMessage());
            }
        });
    }

    /*public void makeFoodList(DisplayDailyMenu dMenu){

            for(MenuItemWithFoods m : dMenu.data){
                lstDailyFood.addAll(m.foods);

            }

        Log.d("lstAdd", "csak : )"+lstDailyFood.size());
    }*/


    // kaja hozzáado menü létrehozása megjelenitése
    public void openAddFoodDialog(View view) {
        createAddDialogFuncs();
        foodAddDialog.show();
    }


    private void createAddDialogFuncs() {
        foodAddDialog.setContentView(R.layout.food_add_in_main);

        // választható melyik categoriába rakjuk az adott kaját legelső elem normál
        // ilyenkor sima foodId t adunk nincs categoria
        ArrayList<String> displayNames = new ArrayList<>();
        displayNames.add("Normal");

        if (categoriesForUpload != null) {
            for (int i = 0; i < categoriesForUpload.available.size(); i++) {
                displayNames.add(categoriesForUpload.available.get(i).name);
            }
        }


        final ArrayAdapter<String> spinnerCategoryAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, displayNames);

        final Spinner categorySpinner = (Spinner) foodAddDialog.findViewById(R.id.category_spinner);
        categorySpinner.setAdapter(spinnerCategoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    categoryIdorNot = categoriesForUpload.available.get(position - 1).id;
                } else {
                    categoryIdorNot = null;
                }
                Log.d("testTag", "pzikaaa " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final AppCompatAutoCompleteTextView autoCompleteTextView =
                foodAddDialog.findViewById(R.id.auto_complete_edit_text);
        final TextView selectedText = foodAddDialog.findViewById(R.id.selected_item);

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

                        selectedText.setText(autoSuggestAdapter.getObject(position).name);
                        int newId = autoSuggestAdapter.getObject(position).id;

                        generateUploadFood(categoryIdorNot, newId);


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
                handlerFood.removeMessages(TRIGGER_AUTO_COMPLETE);
                handlerFood.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handlerFood = new Handler(new Handler.Callback() {
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

        String restaurantIdString = String.valueOf(selectedRestaurantId);
        Call<List<FoodAutoComplete>> autoFoods = MelodinApi.getInstance().foods().autoCompleteFood(restaurantIdString, text, "Bearer " + userToken, acceptLanguage);
        autoFoods.enqueue(new Callback<List<FoodAutoComplete>>() {
            @Override
            public void onResponse(Call<List<FoodAutoComplete>> call, retrofit2.Response<List<FoodAutoComplete>> response) {
                if (response.isSuccessful()) {
                    List<FoodAutoComplete> foodList = response.body();

                    List<String> stringList = new ArrayList<>();
                    for (int i = 0; i < foodList.size(); i++) {
                        stringList.add(foodList.get(i).name);
                    }

                    //IMPORTANT: set data here and notify
                    //autoSuggestAdapter.setData(stringList);
                    autoSuggestAdapter.setData(foodList);
                    autoSuggestAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<FoodAutoComplete>> call, Throwable t) {
                Log.d("testTag", "auto food complete error");
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        addFood = menu.findItem(R.id.menu_add_food);
        addFood.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:

                return true;
            case R.id.menu_add_food:
                createAddDialogFuncs();
                foodAddDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navMenuList(List<Restaurant> restaurants) {

        List<String> lstNavGroup = Arrays.asList("Éttermek", "Ételek");
        List<String> lstSecondMenuChilds = Arrays.asList("Új Felvétele", "Export", "Import","akármi");
        List<String> restaurantsNames = new ArrayList<>();
        for (Restaurant r : restaurants)
            restaurantsNames.add(r.name);

        Map<String, List<String>> lstChild = new TreeMap<>();
        lstChild.put(lstNavGroup.get(0), restaurantsNames);
        lstChild.put(lstNavGroup.get(1), lstSecondMenuChilds);

        navAdapter = new NavExpendableListAdapter(this, lstNavGroup, lstChild);
        navListView = (ExpandableListView) findViewById(R.id.my_expandableList_view);
        navListView.setAdapter(navAdapter);
        Map<String, Restaurant> getRestaurantByName = new HashMap<>();
        for (Restaurant r : restaurants)
            getRestaurantByName.put(r.name, r);
        navListLastSelectedChildIv =null;


        navListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                // reseteljük a képernyőn levő írásokat
                resetRestaurantMainData();

                // reseteljük a letöltött displayeket
                resetAdaptersWhenChangeRestaurant();

                // reseteljük categoriákat és a kiválasztott kategoriát
                categoriesForUpload = null;
                categoryIdorNot = null;


                ImageView iv =(ImageView)view.findViewById(R.id.ivNavChild);
                 findViewById(R.id.rootLayout_main_list_header).setVisibility(View.VISIBLE);

                navAdapter.setImage(getApplicationContext(),iv,R.drawable.ic_nav_child);
                if(navListLastSelectedChildIv !=null)
                    navAdapter.setImage(getApplicationContext(), navListLastSelectedChildIv,R.drawable.ic_nav_child_nonchoosed);
                navListLastSelectedChildIv =iv;


                if (i == 0) {
                    Restaurant selectedRestaurant = restaurants.get(i1);
                    showRestaurantMainData(selectedRestaurant);
                    selectedRestaurantId=selectedRestaurant.id;
                    downloadRestaurantWithDisplay(String.valueOf(selectedRestaurantId));


                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                }else if(i==1) {
                    switch (i1){
                        case 0:
                            Intent intent = new Intent(getApplicationContext(), NewElementActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intentexp = new Intent(getApplicationContext(),ExportActivity.class);
                            startActivity(intentexp);
                    }

                }
                return true;
            }
        });




    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       /* // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d("testTag", "id amit valasztok: " + id);


        // reseteljük a képernyőn levő írásokat
        resetRestaurantMainData();

        // reseteljük a letöltött displayeket
        resetAdaptersWhenChangeRestaurant();

        // reseteljük categoriákat és a kiválasztott kategoriát
        categoriesForUpload = null;
        categoryIdorNot = null;


        // megnézzük hogy a választott étterem id ja hanyadik a tömbben ha meg van akkor beállítjuk választott
        // étterem idnak és megjelenitjük az adatokat
        for (int i = 0; i < this.restaurants.size(); i++) {
            Restaurant selectedRestaurantMenu = this.restaurants.get(i);
            if (id == selectedRestaurantMenu.id) {
                showRestaurantMainData(selectedRestaurantMenu);
                this.selectedRestaurantId = selectedRestaurantMenu.id;
            }
        }

        // elinditjuk az éttermek displayeinek letöltését
        downloadRestaurantWithDisplay(String.valueOf(id));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    private void resetAdaptersWhenChangeRestaurant() {
        displayDailyMenuArrayList.clear();
        restaurantDisplayListArray.clear();
        listAdapter.notifyDataSetChanged();
        restaurantDisplayAdapter.clearAdapter();
    }

    // ki reseteli a képernyőn a ui elemeket (főleg új étterem választásánál)
    private void resetRestaurantMainData() {
        //addFoodPopUpButton.setVisibility(View.INVISIBLE);
        addFood.setVisible(false);
        resturantDataTextView.setText("");
        resturantLocationTextView.setText("");
        selectedRestaurantId = -1;
    }

    // kiirja a főbb adatokat egy restaurantrol
    private void showRestaurantMainData(Restaurant restaurant) {
        resturantDataTextView.setText(restaurant.name);


        resturantLocationTextView.setText(restaurant.getLocation());
        tvRestaurantPhone.setText(restaurant.contactPhone);

        selectedRestaurantId = restaurant.id;
    }




    private void downloadRestaurantWithDisplay(String restaurantId) {
        lstDailyFood= new ArrayList<>();
        Call<List<RestaurantDisplay>> display = MelodinApi.getInstance().restaurant().restaurantDisplay(restaurantId, "Bearer " + userToken);
        display.enqueue(new Callback<List<RestaurantDisplay>>() {
            @Override
            public void onResponse(Call<List<RestaurantDisplay>> call, retrofit2.Response<List<RestaurantDisplay>> response) {
                if (response.isSuccessful()) {
                    List<RestaurantDisplay> restaurantDisplayList = response.body();


                    Log.d("testTag", "mennyit kapok " + restaurantDisplayList.size());

                    for (int i = 0; i < restaurantDisplayList.size(); i++) {
                        Log.d("testTag", "******************");
                        Log.d("testTag", restaurantDisplayList.get(i).toString());
                        Log.d("testTag", "******************");

                        // letöltünk egy display t
                        downloadDisplayMenu(restaurantDisplayList.get(i));
                    }

                }
            }

            @Override
            public void onFailure(Call<List<RestaurantDisplay>> call, Throwable t) {
                Log.d("testTag", "valami nem jó");
            }
        });
    }


    //
    private void downloadDisplayMenu(RestaurantDisplay restaurantDisplay) {
        String displayId = String.valueOf(restaurantDisplay.id);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(calendar.getTime());

        Log.d("testTag", "bjött ide");
        Log.d("testTag", format);
        Log.d("testTag", displayId);


        if (restaurantDisplay.type.equals("special") && categoriesForUpload == null) {
            Log.d("testTag", "mar megint ott a fsfasda");
            downloadCategoryForSpecialmenu(displayId);
        }

        Log.d("testTag", "vaze token" + restaurantDisplay.accessToken);
        Call<DisplayDailyMenu> displayDailyMenuCall = MelodinApi.getInstance().restaurant().showDisplayMenuAtDate(/**/format/*"2019-08-18"*/, "Bearer " + restaurantDisplay.accessToken);
        displayDailyMenuCall.enqueue(new Callback<DisplayDailyMenu>() {
            @Override
            public void onResponse(Call<DisplayDailyMenu> call, retrofit2.Response<DisplayDailyMenu> response) {
                if (response.isSuccessful()) {
                    DisplayDailyMenu displayDailyMenu = response.body();

                    Log.d("testTag", "---------------");
                    Log.d("testTag", displayDailyMenu.toString());
                    Log.d("testTag", "------------------");

                    dataModelCreateFor(displayDailyMenu, restaurantDisplay);
                    Log.d("testTag", "ide vissza");
                   // makeFoodList(displayDailyMenu);
                } else {
                    dataModelCreateForJustDisplay(restaurantDisplay);


                    Log.d("testTag", "itt a probléma uzinek " + response.code());
                    Log.d("testTag", "itt a probléma uzinek222222 " + response.message());
/*                    restaurantDisplayListArray.add(restaurantDisplay);
                    DisplayDailyMenu displayDailyMenuEmpty = new DisplayDailyMenu();
                    displayDailyMenuEmpty.data = new ArrayList<MenuItemWithFoods>();
                    displayDailyMenuArrayList.add(displayDailyMenuEmpty);

                    Log.d("testTag", response.toString());

*/
                    Log.d("testTag", "itt a probléma downloadDisplayMenu");
                    Log.d("testTag", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<DisplayDailyMenu> call, Throwable t) {
                Log.d("testTag", "displayDailyMenuCall valami nem jó");
                Log.d("testTag", t.getLocalizedMessage());
            }
        });
    }


    // kategoriákat töltjük le a special menükhöz majd ezeket jelenitjük meg a fooduploadnál
    private void downloadCategoryForSpecialmenu(String displayId) {
        Call<Category> categoryCall = MelodinApi.getInstance().restaurant().categoryGet(displayId, "Bearer " + userToken);
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, retrofit2.Response<Category> response) {
                if (response.isSuccessful()) {
                    Category category = response.body();
                    categoriesForUpload = category;
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }

    // itt az adapterhez valo objektumot rakom össze
    private void dataModelCreateFor(DisplayDailyMenu displayDailyMenu, RestaurantDisplay restaurantDisplay) {
        displayDailyMenu.customType = restaurantDisplay.type;

        // hozzáadunk egy restaurantot megjeleniteni a képernyőt meg a nevet
        restaurantDisplayListArray.add(restaurantDisplay);

        // dailymenut adunk hozzá
        displayDailyMenuArrayList.add(displayDailyMenu);

        //addFoodPopUpButton.setVisibility(View.VISIBLE);
        addFood.setVisible(true);

        // egyenlőre rejtsem el amig nincs daily menu id
        //Log.d("testTag", "daily menu id ddddddd " + displayDailyMenu.dailyMenuId);
        /*if(displayDailyMenu.dailyMenuId == null){
            addFoodPopUpButton.setVisibility(View.INVISIBLE);
        }*/


        // frissitjük hogy megjelenjenek az adatok
        listAdapter.notifyDataSetChanged();
        restaurantDisplayAdapter.notifyDataSetChanged();
    }

    // itt az adapterhez adjuk hozzá csak a restaurant displayet pl.: ha nincs a displayen menu
    private void dataModelCreateForJustDisplay(RestaurantDisplay restaurantDisplay) {
        // hozzáadunk egy restaurantot megjeleniteni a képernyőt meg a nevet
        restaurantDisplayListArray.add(restaurantDisplay);


        // egyenlőre rejtsem el amig nincs daily menu id
        //addFoodPopUpButton.setVisibility(View.VISIBLE);//(átköltöztetve)
        //addFoodPopUpButton.setVisibility(View.INVISIBLE);(átköltöztetve)
        addFood.setVisible(false);


        // frissitjük hogy megjelenjenek az adatok
        listAdapter.notifyDataSetChanged();
        restaurantDisplayAdapter.notifyDataSetChanged();
    }

    // protocols from the exapnd listázosbol

    @Override
    public void deleteOneFood(Boolean menuOrNormal, int WhichDisplay, int category, int subelem) {

    }

    @Override
    public void deleteFoodFromDailyMenu(Boolean isMenu, int categoryId, int foodId) {
        generateDeleteUploadFood(isMenu, categoryId, foodId);
    }

    @Override
    public void changeFoodPrices(Food food) {
        initPopupViewControls(food);
    }

    private void initPopupViewControls(Food food) {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

        // Inflate the popup dialog from a layout xml file.
        View popupPriceInputDialogView = layoutInflater.inflate(R.layout.price_change_popup_menu, null);

        // Get user input edittext and button ui controls in the popup dialog.
        EditText smallPriceEditText = (EditText) popupPriceInputDialogView.findViewById(R.id.smallPriceText_change_price);
        EditText normlaPriceEditText = (EditText) popupPriceInputDialogView.findViewById(R.id.normalPriceText_change_price);
        Button sendPriceDataButton = popupPriceInputDialogView.findViewById(R.id.button_send_price_change_popup);
        Button cancelPriceDataButton = popupPriceInputDialogView.findViewById(R.id.button_cancel_price_change_popup);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle("Fill this food priceses: " + food.name);
        alertDialogBuilder.setCancelable(true);

        smallPriceEditText.setHint(food.defaultPriceSmall+" Ft");
        normlaPriceEditText.setHint(food.defaultPriceNormal+" Ft");


        // Set the inflated layout view object to the AlertDialog builder.
        alertDialogBuilder.setView(popupPriceInputDialogView);

        // Create AlertDialog and show.
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        cancelPriceDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        sendPriceDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double smallPrice = 0;
                double normalPrice = 0;

                Boolean isSuccesPriceAdded = false;
                try {
                    smallPrice = Double.parseDouble(smallPriceEditText.getText().toString());
                    normalPrice = Double.parseDouble(normlaPriceEditText.getText().toString());
                    isSuccesPriceAdded = true;
                    Log.d("testTag", "smalll " + smallPrice + " normal " + normalPrice + "foodId " + food.id);
                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                    Toast.makeText(getApplicationContext(), "You did not add correct number. ", Toast.LENGTH_SHORT).show();
                    isSuccesPriceAdded = false;
                }

                if (isSuccesPriceAdded == true) {
                    PriceChangeUpload priceChangeUpload = new PriceChangeUpload();
                    priceChangeUpload.price = normalPrice;
                    priceChangeUpload.reducedPrice = smallPrice;
                    priceChangeUpload.restaurantId = selectedRestaurantId;


                    Call<Void> foodPriceChangesApiCall = MelodinApi.getInstance().foods().foodPriceChanges(food.id, "Bearer " + userToken, priceChangeUpload);
                    foodPriceChangesApiCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {

                            if (response.isSuccessful()) {
                                refreshEachDisplay();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error, Not added price change. ", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("testTag", "upload hib");
                            Toast.makeText(getApplicationContext(), "Error, Not added price change. ", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Not added price change. ", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }

            }
        });
    }


    // legenerálom a feltöltendő kaják objektumát és kiszedem belőle ami nem kell
    private void generateDeleteUploadFood(Boolean isMenu, Integer categoryId, int foodId) {
        // ebbe gyűjtjük a feltöltendő kajákat
        FoodUpload foodUpload = new FoodUpload();

        Boolean shouldReload = false;

        HashMap<Integer, List<Integer>> listHash;
        listHash = new HashMap<>();


        for (int i = 0; i < displayDailyMenuArrayList.size(); i++) {
            if (displayDailyMenuArrayList.get(i).customType.equals("carte")) {
                for (int j = 0; j < displayDailyMenuArrayList.get(i).data.size(); j++) {

                    for (int ik = 0; ik < displayDailyMenuArrayList.get(i).data.get(j).foods.size(); ik++) {
                        if (!foodUpload.foodIds.contains(displayDailyMenuArrayList.get(i).data.get(j).foods.get(ik).id)) {
                            foodUpload.foodIds.add(displayDailyMenuArrayList.get(i).data.get(j).foods.get(ik).id);
                        }
                    }
                }
            } else if (displayDailyMenuArrayList.get(i).customType.equals("special")) {
                for (int hs = 0; hs < displayDailyMenuArrayList.get(i).data.size(); hs++) {
                    List<Integer> listFoods = listHash.get(displayDailyMenuArrayList.get(i).data.get(hs).id);
                    if (listFoods != null) {
                        for (int belso = 0; belso < displayDailyMenuArrayList.get(i).data.get(hs).foods.size(); belso++) {
                            if (!listFoods.contains(displayDailyMenuArrayList.get(i).data.get(hs).foods.get(belso).id)) {
                                listFoods.add(displayDailyMenuArrayList.get(i).data.get(hs).foods.get(belso).id);
                            }
                        }
                    } else {
                        List<Integer> foodIdArray = new ArrayList<>();
                        for (int a = 0; a < displayDailyMenuArrayList.get(i).data.get(hs).foods.size(); a++) {
                            foodIdArray.add(displayDailyMenuArrayList.get(i).data.get(hs).foods.get(a).id);
                        }
                        listHash.put(displayDailyMenuArrayList.get(i).data.get(hs).id, foodIdArray);
                    }
                }
            }
        }


        // specmenöket hozzáadom a feltöltendő uploadfoodhoz

        for (Map.Entry<Integer, List<Integer>> map : listHash.entrySet()) {
            SpecMenuIdFoods specmenu = new SpecMenuIdFoods();
            specmenu.id = map.getKey();
            specmenu.foods = map.getValue();
            foodUpload.specMenus.add(specmenu);
            Log.d("testTag", "hash ciklusban " + map.getKey() + " --- " + map.getValue());
        }

        if (isMenu == false) {
            int foodIndex = foodUpload.foodIds.indexOf(foodId);
            foodUpload.foodIds.remove(foodIndex);
        } else {
            for (int g = 0; g < foodUpload.specMenus.size(); g++) {
                if (foodUpload.specMenus.get(g).id == categoryId) {
                    int foodIndex = foodUpload.specMenus.get(g).foods.indexOf(foodId);
                    foodUpload.specMenus.get(g).foods.remove(foodIndex);
                }
            }
        }

        uploadFoodString(foodUpload, true);
    }



}
