package com.itworks.festapp.artists;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.models.ArtistListItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.*;

/**
 * Created by Naglis on 2015-02-01.
 */
public class ArtistsListAdapter extends ArrayAdapter<ArtistListItem> implements SectionIndexer {

    private final Context context;
    HashMap<String, Integer> azIndexer;
    String[] sections;

    public ArtistsListAdapter(Context context, List<ArtistListItem> items) {
        super(context, R.layout.artists_list_item, items);
        this.context = context;

        azIndexer = new HashMap<>(); //stores the positions for the start of each letter

        int size = items.size();
        for (int i = size - 1; i >= 0; i--) {
            String element = items.get(i).name;
            //We store the first letter of the word, and its index.
            azIndexer.put(element.substring(0, 1), i);
        }

        Set<String> keys = azIndexer.keySet(); // set of letters

        Iterator<String> it = keys.iterator();
        ArrayList<String> keyList = new ArrayList<>();

        while (it.hasNext()) {
            String key = it.next();
            keyList.add(key);
        }
        Collections.sort(keyList);//sort the keylist
        sections = new String[keyList.size()]; // simple conversion to array
        keyList.toArray(sections);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.artists_list_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.item_image);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_title);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ArtistListItem item = getItem(position);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + item.photoId ,viewHolder.ivIcon);
        viewHolder.tvTitle.setText(item.name);
        Typeface futura = Typeface.createFromAsset(context.getAssets(), "fonts/futura_condensed_medium.ttf"); // TODO refactor neturetu jo cia buti
        viewHolder.tvTitle.setTypeface(futura);
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        String letter = sections[section];
        return azIndexer.get(letter);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
    }

}