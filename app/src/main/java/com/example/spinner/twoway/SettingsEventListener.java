package com.example.spinner.twoway;

import android.view.View;

public interface SettingsEventListener {
    public void onClickSaveSettings(View v);
    public void onCommunicationMode(String commMode);
}