package com.maxapp.dansu.astraplayer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;

public class MainActivity extends AppCompatActivity {

    private final int permission = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPhoneStatePermission(); //Permission check

    }



    ////////////////////////////////////////////////////////////////////// PERMISSION REQUEST
    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanation("Permission Needed", "This app needs permission to read data on your phone", Manifest.permission.READ_EXTERNAL_STORAGE, permission);
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, permission);
            }
        } else {
            //Toast.makeText(MainActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            checkFirstLaunch();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case permission:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    checkFirstLaunch();
                } else {
                    //Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    finish();
                    System.exit(0);
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }


    ///////////////////////////////////////////////////////////////// CHECK FIRST LAUNCH
    private void checkFirstLaunch(){


        SharedPreferencesEditor editor = new SharedPreferencesEditor(this);
        boolean test = editor.GetBoolean("firstLaunch");
        if(test)
            return;
        startActivity(new Intent(MainActivity.this, FirstLaunch.class));
        editor.WriteBoolean("firstLaunch", true);
    }


    ///////////////////////////////////////////////////////////////// LOAD SAVED DATA

    private void loadData(){

    }
}
