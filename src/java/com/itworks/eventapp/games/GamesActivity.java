package com.itworks.eventapp.games;

import android.content.Intent;
import android.os.Bundle;
import com.itworks.eventapp.ActionBarActivity;

public class GamesActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(this);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putInt("id", intent.getIntExtra("id", -1));
        openFragment(bundle, new GamesListAdapterFragment());
    }
}
