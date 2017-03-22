package com.android.brambrouwer.spare.View;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.android.brambrouwer.spare.Controller.PreferenceController;
import com.android.brambrouwer.spare.Model.Champion;
import com.android.brambrouwer.spare.R;

public class HostActivity extends AppCompatActivity implements MasterFragment.OnMasterItemSelectedListener {


    public String screenType;

    /*
    Get the screen type and initialize view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        screenType = PreferenceController.getScreenType(this);

        if(screenType.equals("normal")){
            normalScreen();
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        }else if(screenType.equals("large")){
            largeScreen();
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);     //  Fixed Portrait orientation
        }
    }

    /*
        Callback method which is called when an item is clicked in the master fragment
     */
    @Override
    public void onMasterItemSelected(Champion c) {

        FragmentManager fm = getSupportFragmentManager();

        if(screenType.equals("large")){
            DetailFragment fragment = (DetailFragment) fm.findFragmentById(R.id.detailfragment);
            fragment.initView(c);
        }else{
            //Because we dont know when the new fragment is done inializing in this activity, we pass the champion as a parcelable extra
            DetailFragment newFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putParcelable("champion_obj",c);
            newFragment.setArguments(args);

            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_placeholder, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /*
       Callback method for the android back button which checks if there any existing fragments in the backstack
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /*
     Show both fragments next to eachother by replacing the framelayouts with fragments (might need  to change FL back to fragment)
      */
    public void largeScreen(){
        // Create new fragments and transaction
        Fragment masterFragment = new MasterFragment();
        Fragment detailFragment = new DetailFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.list, masterFragment);
        transaction.replace(R.id.detailfragment, detailFragment);
        transaction.commit();
    }

    /*
     Show master view. Will be replace by detail view on click
     */
    public void normalScreen(){
        Fragment newFragment = new MasterFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_placeholder, newFragment);
        transaction.commit();
    }

}
