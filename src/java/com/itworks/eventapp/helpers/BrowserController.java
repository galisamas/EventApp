package com.itworks.eventapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;
import com.itworks.eventapp.R;

public class BrowserController {

    private final Context context;
    private final String no_internet;

    public BrowserController(Context context) {
        this.context = context;
        no_internet = context.getString(R.string.noCon);
    }

    public void openBrowser(String url){
        if(isNetworkAvailable()){
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        }else{
            Toast.makeText(context.getApplicationContext(), no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
