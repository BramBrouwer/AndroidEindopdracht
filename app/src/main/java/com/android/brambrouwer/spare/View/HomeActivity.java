package com.android.brambrouwer.spare.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.brambrouwer.spare.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        readPrefs();
    }

    //Check if background has changed when returning from settings
    @Override
    protected void onResume() {
        super.onResume();
        readPrefs();
    }

    public void openAllChamps(View view){
        Intent intent = new Intent(HomeActivity.this, HostActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view){
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }



    //Get current value of the theme pref & set backroung accordingly
    public void readPrefs(){

        //Get current value of the shared preference with key pref_theme (key declared in settingsfragment)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String prefTheme = sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");
        LinearLayout root = (LinearLayout)findViewById(R.id.activity_home);

        if(prefTheme.equals("dark")){
            root.setBackground(getDrawable(R.drawable.background_texture7));
        }else{
            root.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }
}
