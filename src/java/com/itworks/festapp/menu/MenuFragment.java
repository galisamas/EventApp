package com.itworks.festapp.menu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.itworks.festapp.R;
import com.itworks.festapp.artists.ArtistsActivity;
import com.itworks.festapp.food.FoodActivity;
import com.itworks.festapp.games.GamesActivity;
import com.itworks.festapp.helpers.PhotoController;
import com.itworks.festapp.helpers.TypefaceController;
import com.itworks.festapp.info.InfoActivity;
import com.itworks.festapp.map.TerritoryActivity;
import com.itworks.festapp.stages.StagesActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MenuFragment extends Fragment implements View.OnClickListener {

    RelativeLayout b1,b2,b3,b4,b5,b6;
    TextView b1t, b2t, b3t, b4t, b5t, b6t;
    ImageView logo, img;
    Space space1, space2, space3;
    private MenuBottomFragment element;
    boolean i = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_fragment, container, false);
        element = new MenuBottomFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.bottomLine, element);
        transaction.commit();
        img = (ImageView) v.findViewById(R.id.menu_background_logo);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage("drawable://" + R.drawable.bg, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                img.setImageDrawable(new BitmapDrawable(loadedImage));
            }
        });
        logo = (ImageView) v.findViewById(R.id.logo);
        imageLoader.displayImage("drawable://"+ R.drawable.logo, logo);
        b1 = (RelativeLayout) v.findViewById(R.id.button);
        b2 = (RelativeLayout) v.findViewById(R.id.button2);
        b3 = (RelativeLayout) v.findViewById(R.id.button3);
        b4 = (RelativeLayout) v.findViewById(R.id.button4);
        b5 = (RelativeLayout) v.findViewById(R.id.button5);
        b6 = (RelativeLayout) v.findViewById(R.id.button6);
        b4.setEnabled(true);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int id = bundle.getInt("id", -1);
            String text = bundle.getString("text");
            if(id!=-1 && text==null){
                Intent intent;
                if(bundle.getBoolean("isItArtist"))
                    intent = new Intent(getActivity(), ArtistsActivity.class);
                else
                    intent = new Intent(getActivity(), GamesActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }else if(text !=null && !text.equals("")){
                setAlertDialog(text);
            }
        }
        space1 = (Space) v.findViewById(R.id.space1);
        space2 = (Space) v.findViewById(R.id.space2);
        space3 = (Space) v.findViewById(R.id.space3);
        setTypefaces(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        identifySpaceHeight(calculateSpaceHeight(getActivity()));
    }

    private void setTypefaces(View v) {
        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        b1t = (TextView) v.findViewById(R.id.b1t);
        b2t = (TextView) v.findViewById(R.id.b2t);
        b3t = (TextView) v.findViewById(R.id.b3t);
        b4t = (TextView) v.findViewById(R.id.b4t);
        b5t = (TextView) v.findViewById(R.id.b5t);
        b6t = (TextView) v.findViewById(R.id.b6t);
        typefaceController.setFutura(b1t);
        typefaceController.setFutura(b2t);
        typefaceController.setFutura(b3t);
        typefaceController.setFutura(b4t);
        typefaceController.setFutura(b5t);
        typefaceController.setFutura(b6t);
    }

    private void identifySpaceHeight(int height) {
        space1.getLayoutParams().height = height;
        space2.getLayoutParams().height = height;
        space3.getLayoutParams().height = height;
        space1.requestLayout();
        space2.requestLayout();
        space3.requestLayout();
    }

    private int calculateSpaceHeight(FragmentActivity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int logoH = (int) getResources().getDimension(R.dimen.logo_height);
        int festH = (int) (getResources().getDimension(R.dimen.fest_height)*(PhotoController.isItSmallScreen(context)?1.5:(PhotoController.isItMediumScreen(context)?4:7)));
        int buttonsH = (int) (getResources().getDimension(R.dimen.button_width)*2);
        int fragmentH = (int) getResources().getDimension(R.dimen.button_width);
        return (size.y-logoH-festH-buttonsH-fragmentH)/3;
    }

    private void setAlertDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        b4.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if(view.getId() == R.id.button){
            intent = new Intent(getActivity(),ArtistsActivity.class);
        }
        else if (view.getId() == R.id.button2){
            intent = new Intent(getActivity(),StagesActivity.class);
        }
        else if (view.getId() == R.id.button3){
            intent = new Intent(getActivity(),FoodActivity.class);
        }
        else if (view.getId() == R.id.button4){
            intent = new Intent(getActivity(),TerritoryActivity.class);
            b4.setEnabled(false);
        }
        else if (view.getId() == R.id.button5){
            intent = new Intent(getActivity(),InfoActivity.class);
        }
        else if(view.getId() == R.id.button6){
            intent = new Intent(getActivity(),GamesActivity.class);
        }
        getActivity().startActivity(intent);
    }
}