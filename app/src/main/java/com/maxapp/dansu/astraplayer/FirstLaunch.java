package com.maxapp.dansu.astraplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.maxapp.dansu.astraplayer.folder_browser.FolderBrowser;
import com.maxapp.dansu.astraplayer.folder_browser_activity.FolderBrowserActivity;

public class FirstLaunch extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);

        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstLaunch.this, FolderBrowserActivity.class));
            }
        });
    }

}
