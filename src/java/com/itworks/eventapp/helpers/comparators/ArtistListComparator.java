package com.itworks.eventapp.helpers.comparators;


import com.itworks.eventapp.models.ArtistModel;

import java.util.Comparator;

public class ArtistListComparator implements Comparator<ArtistModel> {
    @Override
    public int compare(ArtistModel o1, ArtistModel o2) {
        return o1.title.compareTo(o2.title);
    }
}
