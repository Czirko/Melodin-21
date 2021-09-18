package com.example.balazshollo.melodin.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.balazshollo.melodin.R;
import com.example.balazshollo.melodin.api.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NavExpendableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> lstDataGroup;
    private Map<String, List<String>> lstDataChild;

    public NavExpendableListAdapter(Context context, List<String> lstDataGroup, Map<String, List<String>> lstDataChild) {
        this.context = context;
        this.lstDataGroup = lstDataGroup;
        this.lstDataChild = lstDataChild;
    }

    @Override
    public int getGroupCount() {
        return lstDataGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {

        return lstDataChild.get(lstDataGroup.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return lstDataGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return lstDataChild.get(lstDataGroup.get(i)).get(i1);
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

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.nav_list_group,null);
        }
        TextView tvGroup = view.findViewById(R.id.tvNav_list_group);
        tvGroup.setTypeface(null, Typeface.BOLD);

        tvGroup.setText(lstDataGroup.get(i));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String title = (String) getChild(i,i1);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.nav_list_child,null);
        }
        TextView tvGroup = view.findViewById(R.id.tvNav_list_item);
        tvGroup.setTypeface(null, Typeface.NORMAL);
        tvGroup.setText(title);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void setImage(final Context mContext, final ImageView imageView, int picture)
    {
        if (mContext != null && imageView != null)
        {
            try
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(picture, mContext.getApplicationContext().getTheme()));
                } else
                {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(picture));
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


}
