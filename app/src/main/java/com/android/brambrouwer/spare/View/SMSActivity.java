package com.android.brambrouwer.spare.View;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.brambrouwer.spare.Controller.PreferenceController;
import com.android.brambrouwer.spare.R;


public class SMSActivity extends AppCompatActivity {
    public String screenType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        updateViewAccordingToPrefs();
        screenType = PreferenceController.getScreenType(this);
        if(screenType.equals("normal")){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        }else if(screenType.equals("large")){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);     //  Fixed Portrait orientation
        }
    }

    private void updateViewAccordingToPrefs(){
        PreferenceController.updatePreferredButtonTextColor((Button)findViewById(R.id.sendSMS),this);
        PreferenceController.updatePreferredBackground(this,findViewById(R.id.activity_sms));
    }

    public void sendSMS(View view) {
        EditText phoneInput = (EditText)findViewById(R.id.phoneText);
        EditText messageInput = (EditText)findViewById(R.id.messageText);

        if(phoneInput.getText().toString().trim().length() > 0 && messageInput.getText().toString().trim().length() > 0){
            String phoneNumber = phoneInput.getText().toString();
            String message = messageInput.getText().toString();
            try {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phoneNumber, null, message, null, null);
            }catch(Exception e){
                Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please fill in the form", Toast.LENGTH_LONG).show();
        }
    }
}
