package com.example.spinner.twoway;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.spinner.R;
import com.example.spinner.databinding.ActivityTwowayBinding;

import java.util.UUID;


public class Twoway extends AppCompatActivity implements SettingsEventListener{
    private static final String TAG = "MainActivity";
//    private FirebaseFirestore firestoreDB;
    private String uniqueIdentifier;
    private ActivityTwowayBinding binding;
    private AccountSetting accountSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_twoway);
        binding.setEventListener(this);
        String[] paymentOptions = getResources().getStringArray(R.array.payment_options);
        binding.setSpinAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, paymentOptions));
        accountSetting = new AccountSetting();
        binding.setAccountSettings(accountSetting);

//        firestoreDB = FirebaseFirestore.getInstance();
//        loadSettings();
    }
    /*private void loadSettings(){
        firestoreDB.collection("accountConfig").document(getInstallationIdentifier())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().exists()) {
                                accountSetting =
                                        task.getResult().toObject(AccountSetting.class);
                                if(accountSetting.getCommunicationMode() == null){
                                    accountSetting.setCommunicationMode("");
                                }
                            }else {
                                accountSetting = new AccountSetting();
                            }
                            //bind data
                            binding.setAccountSettings(accountSetting);
                        }else {
                            Log.d(TAG, "Error loading settings", task.getException());
                        }
                    }
                });

    }*/
    public void onClickSaveSettings(View v) {
        accountSetting.setAccountName(accountSetting.getAccountName().toUpperCase());
        Log.d(TAG, "onCreate: "+accountSetting.getAccountName());
        Log.d(TAG, "onCreate: "+accountSetting.getCommunicationMode());
        Log.d(TAG, "onCreate: "+accountSetting.getDefaultPaymentOption());
//        saveSettings(accountSetting);
    }
    public void onCommunicationMode(String commMode){
        accountSetting.setCommunicationMode(commMode);
    }
    /*private void saveSettings(AccountSetting accountSetting){
        firestoreDB.collection("accountConfig").document(getInstallationIdentifier())
                .set(accountSetting)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,
                                    "settings have been saved",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "settings could not be saved",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
    public synchronized String getInstallationIdentifier() {
        if (uniqueIdentifier == null) {
            SharedPreferences sharedPrefs = this.getSharedPreferences(
                    "UNIQUE_ID", Context.MODE_PRIVATE);
            uniqueIdentifier = sharedPrefs.getString("UNIQUE_ID", null);
            if (uniqueIdentifier == null) {
                uniqueIdentifier = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("UNIQUE_ID", uniqueIdentifier);
                editor.commit();
            }
        }
        return uniqueIdentifier;
    }
}