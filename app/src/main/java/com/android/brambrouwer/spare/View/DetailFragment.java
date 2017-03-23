package com.android.brambrouwer.spare.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.brambrouwer.spare.Controller.PreferenceController;
import com.android.brambrouwer.spare.Model.Champion;
import com.android.brambrouwer.spare.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class DetailFragment extends Fragment {

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
    Champion c;

    /*
        Check if a champion object was passed in extras (only when using small/normal screen)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            c = getArguments().getParcelable("champion_obj");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    /*
        If champion has been set already, update the view (only relevant for small/normal  screens)
        They will call this fragment passing a champion in extras. On large screens initview will be called directly
     */
    @Override
    public void onStart() {
        super.onStart();
        updateViewAccordingToPrefs();
        setLoreButtonListener();
        if (c != null) {
            initView(c);
        }
    }

    /*
    We cant define the listener in xml if we want to use it in the Fragment class instead of the activity
     */
    public void setLoreButtonListener() {
        Button button = (Button) getView().findViewById(R.id.lore_button_frag);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.equals("")) {
                    Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(wiki_url + name));
                    startActivity(browse);
                }
            }
        });
    }

    /*
     Update views
     */
    private void setValues(Champion c) {
        //References to all views
        nameView = (TextView) getActivity().findViewById(R.id.cData_name);
        titleView = (TextView) getActivity().findViewById(R.id.cData_title);
        attack_bar = (ProgressBar) getActivity().findViewById(R.id.attack_bar);
        defense_bar = (ProgressBar) getActivity().findViewById(R.id.defense_bar);
        magic_bar = (ProgressBar) getActivity().findViewById(R.id.magic_bar);
        difficulty_bar = (ProgressBar) getActivity().findViewById(R.id.difficulty_bar);
        champ_icon = (ImageView) getActivity().findViewById(R.id.champ_icon);

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
     Manually called when a champion is selected in master fragment on large screens.
     Updates all views and champ icon
    */
    public void initView(Champion c) {
        setValues(c);
        getChampIcon();
    }

    /*
        Update text color and background according to preferred theme
     */
    private void updateViewAccordingToPrefs(){
        PreferenceController.updatePreferredButtonTextColor((Button)getActivity().findViewById(R.id.lore_button_frag),getActivity());
        PreferenceController.updatePreferredTextViewColor((TextView)getActivity().findViewById(R.id.cData_name),getActivity());
        PreferenceController.updatePreferredTextViewColor((TextView)getActivity().findViewById(R.id.cData_title),getActivity());
        PreferenceController.updatePreferredBackground(getActivity(),getActivity().findViewById(R.id.activity_champ_data));
        PreferenceController.updatePreferredViewColor(getActivity().findViewById(R.id.cData_topdivider),getActivity());
        PreferenceController.updatePreferredViewColor(getActivity().findViewById(R.id.attack_divider),getActivity());
        PreferenceController.updatePreferredViewColor(getActivity().findViewById(R.id.defense_divider),getActivity());
        PreferenceController.updatePreferredViewColor(getActivity().findViewById(R.id.magic_divider),getActivity());
        PreferenceController.updatePreferredViewColor(getActivity().findViewById(R.id.bottom_bar),getActivity());
    }

    /*
Asynchronously get champion icon and display it in top left corner
*/
    private void getChampIcon() {
        if (name.equals("")) return;

        //Soms is de letter na een leesteken dat weggehaald wel een hoofdletter in de link, soms niet. De links zijn inconsistent
        //Hierdoor moet ik een paar namen individueel checken
        String val_name;

        switch (name) {
            case "Vel'Koz":
                val_name = "Velkoz";
                break;
            case "Cho'Gath":
                val_name = "Chogath";
                break;
            case "Kha'Zix":
                val_name = "Khazix";
                break;
            default :
                val_name = name.replaceAll("[^a-zA-Z]+", "");
                break;
        }

        String url = icon_url + val_name + ".png";

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

}
