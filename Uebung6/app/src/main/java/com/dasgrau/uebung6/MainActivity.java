package com.dasgrau.uebung6;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        onClickTimer(findViewById(R.id.buttonTimer));
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Permission SET_ALARM required to set a timer",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void onClickWeb(View v) {
        Intent intentWeb = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.provadis-hochschule.de"));
        startActivity(intentWeb);
    }

    public void onClickMaps(View v) {
        // Example from: https://developers.google.com/maps/documentation/urls/android-intents

        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=50.0994422,8.538269");
        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");
        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    /*
    Still not working as of now :-(
     */
    public void onClickTimer(View v) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.SET_ALARM) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, "Rrrring")
                    .putExtra(AlarmClock.EXTRA_LENGTH, 10)
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            startActivity(intent);
        } else{
            requestPermissionLauncher.launch(
                    Manifest.permission.SET_ALARM);
        }
    }
}