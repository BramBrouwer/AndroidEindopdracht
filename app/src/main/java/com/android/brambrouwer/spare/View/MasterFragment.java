package com.android.brambrouwer.spare.View;


import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.brambrouwer.spare.Controller.ListController;
import com.android.brambrouwer.spare.Controller.ApiController;
import com.android.brambrouwer.spare.Controller.PreferenceController;
import com.android.brambrouwer.spare.Model.Champion;
import com.android.brambrouwer.spare.R;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class MasterFragment extends Fragment {

    public ArrayList<Champion> champs;
    public ListController adapter;
    public ListView list;
    public OnMasterItemSelectedListener onMasterItemSelectedListener;
    public ProgressDialog pDialog;

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
        PreferenceController.updatePreferredBackground(getActivity(),getActivity().findViewById(R.id.listWrapper));

        if(champs == null){
            champs = new ArrayList<>();
            list = (ListView) getActivity().findViewById(R.id.listview);
            adapter = new ListController(this);
            list.setAdapter(adapter);
            setOnClickListener();
            setLongClickListener();
            getAllChamps();
        }
    else{

            list = (ListView) getActivity().findViewById(R.id.listview);
            list.setAdapter(adapter);
            setOnClickListener();
            setLongClickListener();
            adapter.notifyDataSetChanged();
        }
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
        showProgressDialog();
        final ApiController apiController = new ApiController();
        apiController.get(ApiController.allChampsUrl,getActivity(), new ApiController.VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                JSONObject j = new JSONObject(result);      //Get data object from result
                JSONObject data = j.getJSONObject("data");
                champs = iterateKeys(data);                 //List keys and alphabetize them
                dismissProgressDialog();
                adapter.notifyDataSetChanged();             //Update list
            }

            @Override
            public void onError(VolleyError errorMessage) {
                dismissProgressDialog();
                apiController.generateErrorMessage(getView().getContext(), errorMessage);
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

        //Create champion objects
        for (String s : sublist) {
            JSONObject obj = json.getJSONObject(s);
            Champion c = new Champion(obj);
            retChamps.add(c);
        }
        return retChamps;
    }

    //-----------------Listeners
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
    Interface used for commincation between master fragment and hostactivity
    */
    public interface OnMasterItemSelectedListener {
        void onMasterItemSelected(Champion c);
    }

    /*
      On long click toggle the champ as a favourite
     */
    private void setLongClickListener(){

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                String selectedChampName = champs.get(pos).name;
                SharedPreferences settings = getActivity().getSharedPreferences("FavsFile", 0);
                SharedPreferences.Editor editor = settings.edit();

                if(!settings.contains(selectedChampName)){
                    editor.putBoolean(selectedChampName, true);
                    adapter.notifyDataSetChanged();
                    showToast(selectedChampName + " favourited");
                }else{
                    editor.remove(selectedChampName);
                    adapter.notifyDataSetChanged();
                    showToast(selectedChampName + " unfavourited");

                }

                editor.apply();
                return true;
            }
        });
    }
    //--------------- End listeners

    //----------------Dialog
    /*
    Progress dialog shown while loading
     */
    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    /*
    Close dialog
     */
    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
    /*
    If view is destroyed make sure we dissmiss the dialog
     */
    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    public  void showToast(String message){
        Context context = getActivity().getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    //----------------- End dialog




}


