package com.android.brambrouwer.spare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.brambrouwer.spare.models.Champion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;

public class ChampDataActivity extends AppCompatActivity {

    TextView nameView;
    TextView titleView;
    ProgressBar attack_bar;
    ProgressBar defense_bar;
    ProgressBar magic_bar;
    ProgressBar difficulty_bar;
    String wiki_url = "http://leagueoflegends.wikia.com/wiki/";
    String name = "";
    String icon_url = "http://ddragon.leagueoflegends.com/cdn/7.3.1/img/champion/";
    ImageView champ_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champ_data_activiy);
        readPrefs();                                                //Check preferred theme
        Intent intent = getIntent();
        Champion c =  intent.getParcelableExtra("champion_obj");    //Get champ data from extras
        setValues(c);                                               //Update textviews/bars
        getChampIcon();                                             //Get champ icon (async)
    }

    @Override
    protected void onResume() {
        super.onResume();
        readPrefs();
    }

    /*
        Update all views to match values passed on from previous activity
         */
    private void setValues(Champion c){

        //References to all views
        nameView = (TextView)findViewById(R.id.cData_name);
        titleView = (TextView)findViewById(R.id.cData_title);
        attack_bar = (ProgressBar)findViewById(R.id.attack_bar);
        defense_bar = (ProgressBar)findViewById(R.id.defense_bar);
        magic_bar = (ProgressBar)findViewById(R.id.magic_bar);
        difficulty_bar = (ProgressBar)findViewById(R.id.difficulty_bar);
        champ_icon = (ImageView)findViewById(R.id.champ_icon);

        //Update values & set bar thickness
        this.name = c.name;
        nameView.setText(name);
        titleView.setText(c.title);
        attack_bar.setProgress(c.attack);
        attack_bar.setScaleY(3f);
        defense_bar.setProgress(c.defense);
        defense_bar.setScaleY(3f);
        magic_bar.setProgress(c.magic);
        magic_bar.setScaleY(3f);
        difficulty_bar.setProgress(c.difficulty);
        difficulty_bar.setScaleY(3f);
    }

    /*
    Navigate to champions wikipage on click
     */
    public void onLoreButtonClick(View view){

        if(!name.equals("")){
        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(wiki_url+name) );
        startActivity( browse );
        }
    }
    /*
    Asynchronously get champion icon and display it in top left corner
     */
    private void getChampIcon() {

    //2 opties, of hardcoded 2 namen hier checken die een afwijkedende image url hebben, of bij iedere naam een api call maken naar champion{id} om de image attr op te halen
        // Dit omdat de icon links inconsistent zijn. Soms wordt een 'in de naam wel vertaald, soms wordt ie simpelweg verwijderd
        String val_name = name.replaceAll("\\s+","");
        val_name = val_name.replaceAll("\'+","");

        if(name.equals("Vel'Koz")){
            val_name = "Velkoz";
        }else if(name.equals("Cho'Gath")){
            val_name = "Chogath";
        }


        String url = icon_url+val_name+".png";


        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                URL url = null;
                try {
                    url = new URL(params[0]);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    champ_icon.invalidate();
                    champ_icon.setImageBitmap(bitmap);
            }
        }.execute(url);
    }

    //Get current value of the theme pref & set backroung accordingly
    public void readPrefs(){

        //Get current value of the shared preference with key pref_theme (key declared in settingsfragment)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String prefTheme = sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");
        RelativeLayout root = (RelativeLayout) findViewById(R.id.activity_champ_data);

        if(prefTheme.equals("dark")){
            root.setBackground(getDrawable(R.drawable.background_texture7));
        }else{
            root.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }
}
