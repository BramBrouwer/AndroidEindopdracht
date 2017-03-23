package com.android.brambrouwer.spare.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.brambrouwer.spare.R;
import com.android.brambrouwer.spare.View.SettingsFragment;

/**
 * Used by views to update to the preferred theme
 */

public final class PreferenceController {


    /*
    Sets background
     */
    public static void updatePreferredBackground(Context context, View root){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String prefTheme =  sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");

        if(prefTheme.equals("light")){
            root.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            root.setBackground(context.getDrawable(R.drawable.background_texture7));
        }
    }
    /*
    Manually update buttons. Theme has to be set before creating the view, view is only created once so not called again even if theme has changed
    Instead of using themes and forcing the screen to completely recreate, we will update text manually
     */
    public static void updatePreferredButtonTextColor(Button button, Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String prefTheme =  sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");

        if(prefTheme.equals("light")){
           button.setTextColor(Color.BLACK);
        }else{
            button.setTextColor(Color.LTGRAY);
        }
    }

    /*
     Manually update views
   */
    public static void updatePreferredViewColor(View view, Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String prefTheme =  sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");

        if(prefTheme.equals("light")){
            view.setBackgroundColor(Color.BLACK);
        }else{
            view.setBackgroundColor(Color.LTGRAY);
        }
    }

    /*
      Manually update textviews.
   */
    public static void updatePreferredTextViewColor(TextView tv, Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String prefTheme =  sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");

        if(prefTheme.equals("light")){
            tv.setTextColor(Color.BLACK);
        }else{
            tv.setTextColor(Color.LTGRAY);
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
