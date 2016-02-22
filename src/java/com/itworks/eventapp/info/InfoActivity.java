package com.itworks.eventapp.info;

import android.os.Bundle;

import com.itworks.eventapp.ActionBarActivity;

public class InfoActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(this);
        openFragmentWithoutBundle(new InfoListAdapterFragment());
    }
}