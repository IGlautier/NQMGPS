package com.nqm.nqmgps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.auth);
        final EditText tokenName   = (EditText)findViewById(R.id.name);
        final EditText tokenSecret   = (EditText)findViewById(R.id.secret);
        final EditText assetName   = (EditText)findViewById(R.id.assetName);

        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String currentName = prefs.getString("tokenName","");
        String currentAsset = prefs.getString("assetName", "");
        if(!currentName.equals("")) tokenName.setText(currentName);
        if(!currentAsset.equals("")) assetName.setText(currentAsset);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                // Save token name
                SharedPreferences prefs = getSharedPreferences("UserData", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("tokenName", tokenName.getText().toString());
                editor.putString("assetName", assetName.getText().toString());
                editor.commit();

                // Start GPS service
                Intent gpsService = new Intent(MainActivity.this, GPSService.class);
                gpsService.setClassName("com.nqm.nqmgps", "com.nqm.nqmgps.GPSService");
                gpsService.putExtra("name", tokenName.getText().toString());
                gpsService.putExtra("secret", tokenSecret.getText().toString());
                gpsService.putExtra("asset", assetName.getText().toString());
                startService(gpsService);
            }
        });

    }
}
