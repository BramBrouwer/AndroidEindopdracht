package com.android.brambrouwer.spare.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    /*
        Update text color and background according to preferred theme
     */
    private void updateViewAccordingToPrefs(){
        PreferenceController.updatePreferredButtonTextColor((Button)findViewById(R.id.bAllChamps),this);
        PreferenceController.updatePreferredButtonTextColor((Button)findViewById(R.id.bSettings),this);
        PreferenceController.updatePreferredBackground(this,findViewById(R.id.activity_home));
        PreferenceController.updatePreferredViewColor((View)findViewById(R.id.homeDivider),this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateViewAccordingToPrefs();
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
