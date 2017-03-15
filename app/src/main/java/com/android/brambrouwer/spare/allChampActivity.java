package com.android.brambrouwer.spare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.brambrouwer.spare.Utility.ListAdapter;
import com.android.brambrouwer.spare.Utility.LolApiController;
import com.android.brambrouwer.spare.models.Champion;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class allChampActivity extends AppCompatActivity {


    public ArrayList<Champion> champs;
    public ListAdapter adapter;
    public ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_champ);

        //Init champlist, adapter and clicklistener
        champs = new ArrayList<>();
        list = (ListView) findViewById(R.id.listview);
        adapter = new ListAdapter(this);
        list.setAdapter(adapter);
        setOnClickListener();

        //Get champs from api
        getAllChamps();
    }

    public void getAllChamps(){

        final LolApiController lolApiController = new LolApiController();
        lolApiController.get(LolApiController.allChampsUrl, this, new LolApiController.VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {

                JSONObject j = new JSONObject(result);
                JSONObject data = j.getJSONObject("data");
                champs = iterateKeys(data);                 //Create list filled with all champ keys - used to sort all champs by name
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(VolleyError errorMessage) {
                lolApiController.generateErrorMessage(allChampActivity.this,errorMessage);
            }
        });

    }


    private ArrayList<Champion> iterateKeys(JSONObject json) throws JSONException {

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Champion> retChamps = new ArrayList<>();

        //Get all keys/champnames so we can sort them
        Iterator<String> iter2 = json.keys();
        while(iter2.hasNext()){
            String key2 = iter2.next();
            keys.add(key2);
        }

        //Sort list of all champnames
        List<String> sublist = keys.subList(1, keys.size()); Collections.sort(sublist);

        //Convert to jsonobjects
        for(String s : sublist){
                JSONObject obj = json.getJSONObject(s);
                int c_id = obj.getInt("id");
                String c_name = obj.getString("name");
                String c_title = obj.getString("title");
                JSONObject info = obj.getJSONObject("info");
                String c_attack = info.getString("attack");
                String c_defense = info.getString("defense");
                String c_magic = info.getString("magic");
                String c_difficulty = info.getString("difficulty");
                Champion c = new Champion(c_id,c_name,c_title,c_attack,c_defense,c_magic,c_difficulty);
                retChamps.add(c);
            }
        return retChamps;
    }

    private void setOnClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Use position to get champ data from champlist and pass it to the next activity as a parcelable extra
                Intent intent = new Intent(allChampActivity.this, ChampDataActivity.class);
                Champion champ = champs.get(position);
                intent.putExtra("champion_obj", champ);
                startActivity(intent);
            }
        });
    }
}
