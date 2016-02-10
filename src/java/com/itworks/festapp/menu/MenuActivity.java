package com.itworks.festapp.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.itworks.festapp.ActionBarActivity;
import com.itworks.festapp.R;

public class MenuActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putInt("id", intent.getIntExtra("id", -1));
        bundle.putString("text", intent.getStringExtra("text"));
        bundle.putBoolean("isItArtist", intent.getBooleanExtra("isItArtist", true));
        MenuFragment fragment = new MenuFragment();
        openFragment(bundle, fragment);
        if (isFirstTime()) {
            new AlertDialog.Builder(this)
                    .setTitle("Welcome!")
                    .setMessage(getString(R.string.welcomeMessage))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    /***
     * Checks that application runs first time and write flag at SharedPreferences
     * @return true if 1st time
     */
    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }
}