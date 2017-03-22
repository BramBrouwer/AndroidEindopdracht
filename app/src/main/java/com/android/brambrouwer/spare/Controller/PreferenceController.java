package com.android.brambrouwer.spare.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;

import com.android.brambrouwer.spare.R;
import com.android.brambrouwer.spare.View.SettingsFragment;

/**
 * Created by bram on 22-3-2017.
 */

public final class PreferenceController {

    /*
    Returns "dark" or "light"
     */
    public static void getPreferredTheme(Context context, View root){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String prefTheme =  sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");

        if(prefTheme.equals("light")){
            root.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            root.setBackground(context.getDrawable(R.drawable.background_texture7));
        }
    }

    /*
   Determine screentype. Returned as either normal or large
    */
    public static String getScreenType(Context context){

        String screensize = "";

        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE ) {
            screensize = "large";
        }
        else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            screensize = "normal";
        }
        else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            screensize = "normal";
        }else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            screensize = "large";
        }
        return screensize;
    }

}
