package com.maxapp.dansu.astraplayer.folder_browser_activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.maxapp.dansu.astraplayer.R;
import com.maxapp.dansu.astraplayer.SharedPreferencesEditor;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;
import java.util.ArrayList;
import java.util.List;

public class FolderBrowserActivity extends AppCompatActivity implements LocalFoldersFragment.OnFragmentInteractionListener, SDfoldersFragment.OnFragmentInteractionListener {


    private Fragment local = new LocalFoldersFragment();
    private Fragment Sd;
    private List<MyDirectory> localDirectories;
    private List<MyDirectory> sdDirectories;
    SharedPreferencesEditor sp;
    Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    private int tabs = 1;
    Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_browser);

        returnIntent = new Intent();

        setResult(Activity.RESULT_CANCELED, returnIntent);



        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        sp = new SharedPreferencesEditor(this);

        localDirectories = sp.ReadDirectories("localDirectories");
        if(localDirectories == null)
            localDirectories = new ArrayList<>();

        sdDirectories = sp.ReadDirectories("sdDirectories");
        if(sdDirectories == null)
            sdDirectories = new ArrayList<>();

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                sp.WriteDirectories(localDirectories, "localDirectories");
                sp.WriteDirectories(sdDirectories, "sdDirectories");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_folder_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void Add(MyDirectory dir, boolean isSd) {
        if(localDirectories.indexOf(dir) == -1 && sdDirectories.indexOf(dir) == -1){
            if(isSd){
                sdDirectories.add(dir);
            }
            else{
                localDirectories.add(dir);
            }
        }

    }

    @Override
    public void Del(MyDirectory dir, boolean isSd) {
        if(localDirectories.indexOf(dir) != -1 || sdDirectories.indexOf(dir) != -1){
            if(isSd){
                sdDirectories.remove(sdDirectories.indexOf(dir));
            }
            else{
                localDirectories.remove(localDirectories.indexOf(dir));
            }
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_folder_browser, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);
            assert getArguments() != null;
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment tmp = null;

            switch (position){
                case 0:
                    tmp = local;
                    break;
                case 1:
                    tmp = Sd;
                    break;
            }

            return tmp;

        }

        @Override
        public int getCount() {
            if(isSDPresent){
                Sd = new SDfoldersFragment();
                tabs = 2;

            }
            return tabs;
        }
    }
}
