package com.android.brambrouwer.spare.View;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.brambrouwer.spare.Controller.PreferenceController;

public class SettingsActivity extends AppCompatActivity {

    private String screenType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        screenType = PreferenceController.getScreenType(this);

        if(screenType.equals("normal")){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        }else if(screenType.equals("large")){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);     //  Fixed Portrait orientation
        }
    }
}
