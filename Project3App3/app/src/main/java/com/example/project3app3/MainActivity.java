package com.example.project3app3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements ShowsFragment.ListSelectionListener {

    public static String[] mShowsArray;
    public static String[] mImagesArray;
    public static String[] mShowsURLArray;
    public static Context context;

    int mShownIndex = -1;

    private final static String VIEW_WEBSITE = "edu.uic.cs478.s19.showTVWebsite";
    private final static String KABOOM_PERMISSION = "edu.uic.cs478.s19.kaboom";

    private ShowsFragment mShowsFragment;
    private ImagesFragment mImagesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        // Create the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get values from string resource file
        mShowsArray = getResources().getStringArray(R.array.Shows);
        mImagesArray = getResources().getStringArray(R.array.Images);
        mShowsURLArray = getResources().getStringArray(R.array.URL);

        // Retrieve or create fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Add the shows fragment and commit the transaction since this fragment is visible by default on both orientations
        mShowsFragment = (ShowsFragment) fragmentManager.findFragmentByTag("ShowsFragment");
        if (mShowsFragment == null) {
            mShowsFragment = new ShowsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer1, mShowsFragment, "ShowsFragment")
                    .commit();
        }

        //Initialize images fragment if it hasn't been already
        mImagesFragment = (ImagesFragment) fragmentManager.findFragmentByTag("ImagesFragment");
        if (mImagesFragment == null) {
            mImagesFragment = new ImagesFragment();
        }

        //Custom handler for back stack changed to configure fragment visibilities
        fragmentManager.addOnBackStackChangedListener(this::layoutConfigurator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Check which option selected
        if (item.getItemId() == R.id.broadcast) {
            // Check that an item has been selected
            if (mShownIndex == -1)
                Toast.makeText(this, "Select a show first!", Toast.LENGTH_SHORT).show();
            else {
                // Send a broadcast intent
                Intent intent = new Intent();
                intent.setAction(VIEW_WEBSITE);
                intent.putExtra("tvURL", mShowsURLArray[mShownIndex]);
                sendOrderedBroadcast(intent, KABOOM_PERMISSION);
            }
        } else if (item.getItemId() == R.id.exit) {
            finish();
        }
        return true;
    }

    @Override
    public void onListSelection(int selected) {
        mShownIndex = selected;
        // Add image fragment if not added
        if (!mImagesFragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer2, mImagesFragment, "ImagesFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        // Set the image and adjust the layout
        mImagesFragment.showImageAtIndex(selected);
        layoutConfigurator();
    }

    public void layoutConfigurator() {
        // Get fragment containers
        FrameLayout fragmentContainer1 = findViewById(R.id.fragmentContainer1);
        FrameLayout fragmentContainer2 = findViewById(R.id.fragmentContainer2);

        if (mImagesFragment.isAdded()) {
            // Show the respective FrameLayout and hide the other if in portrait
            fragmentContainer2.setVisibility(View.VISIBLE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                fragmentContainer1.setVisibility(View.GONE);
        } else {
            // Hide the corresponding FrameLayout and show the other if in portrait
            fragmentContainer2.setVisibility(View.GONE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                fragmentContainer1.setVisibility(View.VISIBLE);
        }
    }

}