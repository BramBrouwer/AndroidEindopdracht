package com.android.brambrouwer.spare.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import com.android.brambrouwer.spare.Controller.PreferenceController;
import com.android.brambrouwer.spare.R;

public class HomeActivity extends AppCompatActivity {
    public String screenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        screenType = PreferenceController.getScreenType(this);

        if(screenType.equals("normal")){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        }else if(screenType.equals("large")){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);     //  Fixed Portrait orientation
        }

    }

    //Check if background has changed when returning from settings
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceController.getPreferredTheme(this,findViewById(R.id.activity_home));
    }

    public void openAllChamps(View view){
        Intent intent = new Intent(HomeActivity.this, HostActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view){
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


}
