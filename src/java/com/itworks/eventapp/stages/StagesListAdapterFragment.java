package com.itworks.eventapp.stages;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.itworks.eventapp.BaseListFragment;
import com.itworks.eventapp.R;
import com.itworks.eventapp.artists.ArtistInfoFragment;
import com.itworks.eventapp.games.GameInfoFragment;
import com.itworks.eventapp.helpers.DateController;
import com.itworks.eventapp.helpers.ModelsController;
import com.itworks.eventapp.models.*;

import java.util.ArrayList;
import java.util.List;

public class StagesListAdapterFragment extends BaseListFragment {

    private int stageNumber;
    private int dayNumber;
    private List<BaseTimetable> timetable;
    private Resources resources;
    private ModelsController modelsController;

    public void setStage(int stageNumber){
        this.stageNumber = stageNumber;
    }
    public void setDay(int dayNumber){
        this.dayNumber = dayNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setListAsync(activity);
    }

    private void setListAsync(final Activity activity) {
        new AsyncTask<Void, Void, Void>() {
            List<StageListItem> mItems;

            @Override
            protected Void doInBackground(Void... params) {
                mItems = new ArrayList<>();
                modelsController = new ModelsController(activity);
                timetable = modelsController.getTimetablesByStageIdAndByDay(dayNumber, stageNumber);
                for (BaseTimetable aTimetable : timetable) {
                    String title, photoIdentif;
                    PlaceModel place;
                    if (aTimetable instanceof TimetableModel) {
                        ArtistModel artist = modelsController.getArtistModelById(((TimetableModel) aTimetable).artistId);
                        title = artist.title;
                        photoIdentif = "m"+(artist.id+1);
                        place = modelsController.getPlaceModelById(((TimetableModel)aTimetable).stageId);
                    } else{
                        GameModel gameModel = modelsController.getGameModelById(((GameTimetableModel) aTimetable).gameId);
                        place = modelsController.getPlaceModelById(gameModel.placeId);
                        photoIdentif = "n"+(gameModel.id+1);
                        title = gameModel.title;
                    }
                    String date = DateController.convertTimeFWD(aTimetable.start_time, aTimetable.end_time);
                    mItems.add(new StageListItem(getColorByDate(aTimetable), photoIdentif, title, date, place.name));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setListAdapter(new StagesListAdapter(activity, mItems));
            }
        }.execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(timetable.get(position) instanceof TimetableModel)
            openInfo(modelsController.getArtistModelById(((TimetableModel)timetable.get(position)).artistId), new ArtistInfoFragment());
        else if(timetable.get(position) instanceof GameTimetableModel)
            openInfo(modelsController.getGameModelById(((GameTimetableModel) timetable.get(position)).gameId), new GameInfoFragment());
    }

    private int getColorByDate(BaseTimetable timetable){
        if(DateController.calculateIsItNow(dayNumber, timetable.start_time, timetable.end_time))
            return resources.getColor(R.color.light_yellow);
        return resources.getColor(R.color.gunmetal_blue);
    }


}