package com.itworks.eventapp;

import android.os.Bundle;
import android.widget.TextView;
import com.itworks.eventapp.helpers.TypefaceController;

public class ComingSoonActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coming_soon_activity);
        setActionBar(this);
        TextView textView = (TextView) findViewById(R.id.item_title);
        TypefaceController typefaceController = new TypefaceController(getAssets());
        typefaceController.setFutura(textView);
    }
}
