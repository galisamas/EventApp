package com.itworks.festapp.info;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.itworks.festapp.ActionBarActivity;

public class InfoActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(this);
        openFragmentWithoutBundle(new InfoListAdapterFragment());
    }
}