package com.itworks.eventapp.helpers.comparators;

import com.itworks.eventapp.models.GameModel;

import java.util.Comparator;

public class GameListComparator implements Comparator<GameModel> {
    @Override
    public int compare(GameModel o1, GameModel o2) {
        return o1.title.compareTo(o2.title);
    }
}
