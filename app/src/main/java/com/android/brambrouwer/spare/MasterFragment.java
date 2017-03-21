package com.android.brambrouwer.spare;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.brambrouwer.spare.Utility.ListAdapter2;
import com.android.brambrouwer.spare.Utility.LolApiController;
import com.android.brambrouwer.spare.models.Champion;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class MasterFragment extends Fragment {

    public ArrayList<Champion> champs;
    public ListAdapter2 adapter;
    public ListView list;
    public String preferredListtheme;
    public OnMasterItemSelectedListener onMasterItemSelectedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    /*
    Called in onstart so we can reference views via hostactivity
     */
    @Override
    public void onStart() {
        super.onStart();
        preferredListtheme = readPrefs();

        //Init champlist, adapter and clicklistener
        champs = new ArrayList<>();
        list = (ListView) getActivity().findViewById(R.id.listview);
        adapter = new ListAdapter2(this);
        list.setAdapter(adapter);
        setOnClickListener();

        getAllChamps();
    }

    /*
    Checks if the host activity has implemented the Onmasteritemselected interface, if it has reference it in the onMasteritemSelectedListener
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a; //Cast context to activity
        a = (Activity) context;

        try {
            onMasterItemSelectedListener = (OnMasterItemSelectedListener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + " must implement OnArticleSelectedListener");
        }
    }

    /*
    Get all champions from api
     */
    public void getAllChamps() {

        final LolApiController lolApiController = new LolApiController();
        lolApiController.get(LolApiController.allChampsUrl, getActivity(), new LolApiController.VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject j = new JSONObject(result);
                JSONObject data = j.getJSONObject("data");
                champs = iterateKeys(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError errorMessage) {
                lolApiController.generateErrorMessage(getView().getContext(), errorMessage);
            }
        });

    }

    /*
    Create list filled with all champ keys - used to sort all champs by name
     */
    private ArrayList<Champion> iterateKeys(JSONObject json) throws JSONException {

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Champion> retChamps = new ArrayList<>();

        //Get all keys/champnames so we can sort them
        Iterator<String> iter2 = json.keys();
        while (iter2.hasNext()) {
            String key2 = iter2.next();
            keys.add(key2);
        }

        //Sort list of all champnames
        List<String> sublist = keys.subList(1, keys.size());
        Collections.sort(sublist);

        //Convert to jsonobjects
        for (String s : sublist) {
            JSONObject obj = json.getJSONObject(s);
            int c_id = obj.getInt("id");
            String c_name = obj.getString("name");
            String c_title = obj.getString("title");
            JSONObject info = obj.getJSONObject("info");
            String c_attack = info.getString("attack");
            String c_defense = info.getString("defense");
            String c_magic = info.getString("magic");
            String c_difficulty = info.getString("difficulty");
            Champion c = new Champion(c_id, c_name, c_title, c_attack, c_defense, c_magic, c_difficulty);
            retChamps.add(c);
        }
        return retChamps;
    }

    /*
    On list item click, pass champion object to host activity
     */
    private void setOnClickListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Champion champ = champs.get(position);
                onMasterItemSelectedListener.onMasterItemSelected(champ);
            }
        });
    }

    /*
    Get current value of the theme pref & set backroung accordingly (should be done before filling the list
     */
    public String readPrefs() {
        //Get current value of the shared preference with key pref_theme (key declared in settingsfragment)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return sharedPref.getString(SettingsFragment.KEY_PREF_THEME, "");
    }

    /*
   Interface used for commincation between master fragment and hostactivity
   */
    public interface OnMasterItemSelectedListener {
        void onMasterItemSelected(Champion c);
    }
}


