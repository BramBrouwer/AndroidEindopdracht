package com.android.brambrouwer.spare.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.brambrouwer.spare.R;
import com.android.brambrouwer.spare.allChampActivity;

/**
 * Created by bram on 30-1-2017.
 */

public class ListAdapter extends BaseAdapter{

    //Activity using the listadapter
    private com.android.brambrouwer.spare.allChampActivity allChampActivity;

    //Constructor
    ListAdapter(allChampActivity allChampActivity) {
        this.allChampActivity = allChampActivity;
    }

    //This methods returns the amount of items in the arraylist in listactivity. All data that we want to display in the list has to be saved in this arraylist
    @Override
    public int getCount(){return allChampActivity.champs.size();}

    //Static class that holds all listitem views
    private static class ViewHolderItem {
        TextView name;
        TextView title;
    }

    //Create a view for each listitem
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Create temp holder
        ViewHolderItem holder = new ViewHolderItem();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) allChampActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.champlist_item, null);

            //Reference views in listitem layout using their ID
            holder.name = (TextView) convertView.findViewById(R.id.c_name);
            holder.title = (TextView) convertView.findViewById(R.id.c_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        //Get values we want to display from list
        String firstLineStr = this.allChampActivity.champs.get(position).name;
        String secondLineStr = this.allChampActivity.champs.get(position).title;


        //Set values
        holder.name.setText(firstLineStr);
        holder.title.setText(secondLineStr);


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

}
