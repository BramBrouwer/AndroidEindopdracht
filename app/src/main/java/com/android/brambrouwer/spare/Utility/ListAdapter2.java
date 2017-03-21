package com.android.brambrouwer.spare.Utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.brambrouwer.spare.MasterFragment;
import com.android.brambrouwer.spare.R;
import com.android.brambrouwer.spare.allChampActivity;

/**
 * Created by bram on 30-1-2017.
 */

public class ListAdapter2 extends BaseAdapter{

    //Activity using the listadapter
    private MasterFragment masterFragment;

    //Constructor
    public ListAdapter2(MasterFragment masterFragment) {
        this.masterFragment = masterFragment;
    }

    //This methods returns the amount of items in the arraylist in listactivity. All data that we want to display in the list has to be saved in this arraylist
    @Override
    public int getCount(){return masterFragment.champs.size();}

    //Create a view for each listitem
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Create temp holder
        ViewHolderItem holder = new ViewHolderItem();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) masterFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.champlist_item, null);

            //Reference views in listitem layout using their ID
            holder.name = (TextView) convertView.findViewById(R.id.c_name);
            holder.title = (TextView) convertView.findViewById(R.id.c_title);
            holder.wrapper = (RelativeLayout) convertView.findViewById(R.id.listItemWrapper);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        //Get values we want to display from list
        String firstLineStr = this.masterFragment.champs.get(position).name;
        String secondLineStr = this.masterFragment.champs.get(position).title;
        String preferredTheme = this.masterFragment.preferredListtheme;

        //Set values
        holder.name.setText(firstLineStr);
        holder.title.setText(secondLineStr);

        if(preferredTheme.equals("dark"))
        {
            holder.wrapper.setBackgroundColor(Color.parseColor("#000000"));

        }else{
            holder.wrapper.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }

        return convertView;
    }

    //Not implemented
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Static class that holds all listitem views
    private static class ViewHolderItem {
        TextView name;
        TextView title;
        RelativeLayout wrapper;
    }

}
