package com.itworks.festapp.info;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.BrowserController;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.TypefaceController;
import com.itworks.festapp.models.PlaceModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;

public class InfoDriveMeThereFragment extends Fragment implements View.OnClickListener {

    TextView about, link;
    ImageView header;
    private List<String> infoList;
    private final String destinationName = "Event place";
    private PlaceModel place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_drive_me_there_fragment, container, false);
        Bundle bundle = this.getArguments();
        int photoId = bundle.getInt("photoId", -1);
        int jsonId = bundle.getInt("jsonId", -1);
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        List<PlaceModel> places = jsonRepository.getPlacesFromJSON();
        place = places.get(0); // FIX parkingo vieta (prototype nera tai del to main stage)
        about = (TextView) v.findViewById(R.id.about);
        link = (TextView) v.findViewById(R.id.link);
        header = (ImageView) v.findViewById(R.id.imageView3);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + photoId, header);
        infoList = jsonRepository.getInfoFromJSON(jsonId);

        about.setText(infoList.get(0));
        link.setText(infoList.get(1));
        link.setOnClickListener(this);

        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setArial(about);
        typefaceController.setArial(link);

        return v;
    }

    @Override
    public void onClick(View v) {
        driveMeThere();
    }

    private void driveMeThere(){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", place.latitude,
                place.longitude, destinationName);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(getActivity(), getString(R.string.toast_warning), Toast.LENGTH_LONG).show();
            }
        }
    }

}