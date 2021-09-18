package com.example.balazshollo.melodin.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.balazshollo.melodin.R;
import com.example.balazshollo.melodin.api.model.DisplayDailyMenu;
import com.example.balazshollo.melodin.api.model.Food;
import com.example.balazshollo.melodin.api.model.MenuItemWithFoods;
import com.example.balazshollo.melodin.main_modells.HashStashDisplay;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    /*private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;

    private List<HashStashDisplay> dataList;*/

    private int WhichDisplay = 0;


    List<DisplayDailyMenu> displayDailyMenus;

    DeleteChildrowInterface deleteChildrowInterface;


    public ExpandableListAdapter(Context context, List<DisplayDailyMenu> dataList, DeleteChildrowInterface deleteChildrowInterface) {
        this.context = context;
        this.displayDailyMenus = dataList;
        this.deleteChildrowInterface = deleteChildrowInterface;
    }

    public void setWhichDisplay(int whichDisplay){
        this.WhichDisplay = whichDisplay;
        this.notifyDataSetChanged();


    }

    public void clearDisplay(){
        this.WhichDisplay = 0;
        displayDailyMenus.clear();
        this.notifyDataSetChanged();
    }

    /*
    public void updateReceiptsList(List<HashStashDisplay> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);
        this.notifyDataSetChanged();
        Log.d("testTag", "change hivoo");
    }*/

    @Override
    public int getGroupCount() {
        if(displayDailyMenus.isEmpty() || WhichDisplay >= displayDailyMenus.size()){
            return 0;
        }else{
            // megvizsgáljuk hogy nutris e mert akkor spec data meg sima data összege kell
            if(displayDailyMenus.get(WhichDisplay).customType.equals("nutritions")){
                return displayDailyMenus.get(WhichDisplay).data.size() + displayDailyMenus.get(WhichDisplay).specialData.size();
            }
            return displayDailyMenus.get(WhichDisplay).data.size();
        }

    }

    @Override
    public int getChildrenCount(int i) {
        if(displayDailyMenus.isEmpty() || WhichDisplay >= displayDailyMenus.size()){
            return 0;
        }else{
            if(displayDailyMenus.get(WhichDisplay).customType.equals("nutritions")){
                if(i < displayDailyMenus.get(WhichDisplay).data.size()){
                    if(displayDailyMenus.get(WhichDisplay).data.get(i).foods == null){
                        return 0;
                    }else{
                        return displayDailyMenus.get(WhichDisplay).data.get(i).foods.size();
                    }
                }else{
                    int specIndex = i - displayDailyMenus.get(WhichDisplay).data.size();
                    if(displayDailyMenus.get(WhichDisplay).specialData.get(specIndex).foods == null){
                        return 0;
                    }else{
                        return displayDailyMenus.get(WhichDisplay).specialData.get(specIndex).foods.size();
                    }
                }



            }else{
                if(displayDailyMenus.get(WhichDisplay).data.get(i).foods == null){
                    return 0;
                }else{
                    return displayDailyMenus.get(WhichDisplay).data.get(i).foods.size();
                }
            }



        }


    }

    @Override
    public Object getGroup(int i) {
        if(displayDailyMenus.get(WhichDisplay).customType.equals("nutritions")){
            if(i < displayDailyMenus.get(WhichDisplay).data.size()){
                return displayDailyMenus.get(WhichDisplay).data.get(i);
            }else {
                int specIndex = i - displayDailyMenus.get(WhichDisplay).data.size();
                return displayDailyMenus.get(WhichDisplay).specialData.get(specIndex);
            }
        }else{
            return displayDailyMenus.get(WhichDisplay).data.get(i);
        }

    }

    @Override
    public Object getChild(int i, int i1) {
        // i = Group Item , i1 = ChildItem

        if(displayDailyMenus.get(WhichDisplay).customType.equals("nutritions")){
            if(i < displayDailyMenus.get(WhichDisplay).data.size()){
                return displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1);
            }else {
                int specIndex = i - displayDailyMenus.get(WhichDisplay).data.size();
                return displayDailyMenus.get(WhichDisplay).specialData.get(specIndex).foods.get(i1);
            }

        }else{
            return displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1);
        }
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {


        MenuItemWithFoods foodCategory = (MenuItemWithFoods)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group,null);
        }
        TextView lblListHeader = (TextView)view.findViewById(R.id.lblListHeader);
        TextView categoryPricField = (TextView)view.findViewById(R.id.category_price_textfield);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText((CharSequence) foodCategory.name);


        if(displayDailyMenus.get(WhichDisplay).customType.equals("special")){
            categoryPricField.setText(foodCategory.price + " Ft");
            Log.d("logTest", "pozit: ");
        }else if(displayDailyMenus.get(WhichDisplay).customType.equals("carte")) {
            categoryPricField.setText("");
            Log.d("logTest", "negativ: ");
        }else if(displayDailyMenus.get(WhichDisplay).customType.equals("nutritions")){
            if(i < displayDailyMenus.get(WhichDisplay).data.size()){
                categoryPricField.setText("");
            }else {
                categoryPricField.setText(foodCategory.price + " Ft");
            }
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Food childText = (Food)getChild(i,i1);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,null);
        }

        TextView txtListChild = (TextView)view.findViewById(R.id.lblListItem);

        // sima texthez volt jó
        // TextView price_text = (TextView)view.findViewById(R.id.list_item_price);
        //Button price_text = (Button)view.findViewById(R.id.list_item_price);

        Button highPriceText = view.findViewById(R.id.list_item_high_price);
        Button lowPriceText=view.findViewById(R.id.list_item_low_price);


       /* price_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("testTag","sikerült");
                deleteChildrowInterface.changeFoodPrices(displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1));
            }
        });*/

        highPriceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("testTag","sikerült");
                deleteChildrowInterface.changeFoodPrices(displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1));
            }
        });

        lowPriceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("testTag","sikerült");
                deleteChildrowInterface.changeFoodPrices(displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1));
            }
        });


        txtListChild.setText((CharSequence) childText.name);

        //Button delteRowButton = (Button)view.findViewById(R.id.deleteRowButton);
        ImageButton delteRowButton = (ImageButton)view.findViewById(R.id.deleteRowButton);


        if(displayDailyMenus.get(WhichDisplay).customType.equals("nutritions")) {
            delteRowButton.setEnabled(false);
            /*price_text.setEnabled(false);*/

            lowPriceText.setEnabled(false);
            highPriceText.setEnabled(false);
        }else{
            delteRowButton.setEnabled(true);
           /* price_text.setEnabled(true);*/
            lowPriceText.setEnabled(true);
            highPriceText.setEnabled(true);
        }


        delteRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete this food?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Boolean menuOrNot = true;
                                if(displayDailyMenus.get(WhichDisplay).customType.equals("carte")){
                                    menuOrNot = false;
                                }else {
                                    menuOrNot = true;
                                }

                                Log.d("testTag", "deletelni valo "+ menuOrNot + " " + WhichDisplay + " " + i + " " + i1 + "name: " + displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1).name);


                                //displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1) // kaja amit törölni kell
                                //displayDailyMenus.get(WhichDisplay).data.get(i).id //cetgory idja
                                deleteChildrowInterface.deleteFoodFromDailyMenu(menuOrNot, displayDailyMenus.get(WhichDisplay).data.get(i).id, displayDailyMenus.get(WhichDisplay).data.get(i).foods.get(i1).id);
                                //deleteChildrowInterface.deleteOneFood(menuOrNot,WhichDisplay,i,i1);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


                /*displayDailyMenus.get(WhichDisplay).data.get(i).foods.remove(i1);
                notifyDataSetChanged();*/


                // ezzel volt jó
                /*
                Boolean menuOrNot = true;
                if(displayDailyMenus.get(WhichDisplay).display.type.equals("carte")){
                    menuOrNot = false;
                }else {
                    menuOrNot = true;
                }
                deleteChildrowInterface.deleteOneFood(menuOrNot,WhichDisplay,i,i1);*/
            }
        });


        if(displayDailyMenus.get(WhichDisplay).customType.equals("carte")){
            String lowPriceString = childText.defaultPriceSmall + " Ft ";
            String highPriceString =childText.defaultPriceNormal + " Ft";
            lowPriceText.setText(lowPriceString);
            highPriceText.setText(highPriceString);

            // csak a normál árat irta ki de azt jól
            //price_text.setText(String.valueOf(childText.defaultPriceNormal) + " Ft");
        }else if(displayDailyMenus.get(WhichDisplay).customType.equals("special")) {
            //.setText("");
            lowPriceText.setText("");
            highPriceText.setText("");

        }else if(displayDailyMenus.get(WhichDisplay).customType.equals("nutritions")){
            if(i < displayDailyMenus.get(WhichDisplay).data.size()){
               /* String priceString = childText.defaultPriceSmall + " Ft " + childText.defaultPriceNormal + " Ft";
                price_text.setText(priceString);*/

                String lowPriceString = childText.defaultPriceSmall + " Ft ";
                String highPriceString =childText.defaultPriceNormal + " Ft";
                lowPriceText.setText(lowPriceString);
                highPriceText.setText(highPriceString);
            }else {
                //price_text.setText("");
                lowPriceText.setText("");
                highPriceText.setText("");
            }
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }



}