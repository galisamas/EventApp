package com.itworks.eventapp.food;

import android.os.Bundle;
import com.itworks.eventapp.ActionBarActivity;

public class FoodActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(this);
        openFragmentWithoutBundle(new FoodListAdapterFragment());
    }
}
