package com.itworks.eventapp.helpers;

import android.content.Context;
import com.itworks.eventapp.helpers.comparators.TimetableListComparator;
import com.itworks.eventapp.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelsController {

    private final List<PlaceModel> places;
    private JSONRepository jsonRepository;

    public ModelsController(Context context) {
        jsonRepository = new JSONRepository(context);
        places = jsonRepository.getPlacesFromJSON();

    }

    public String convertPlaceIdToWhere(int id){
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.where;
            }
        }
        return "";
    }

    public String convertPlaceIdToTitle(int id){
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.name;
            }
        }
        return "";
    }

    public Double convertPlaceIdToLat(int id){
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.latitude;
            }
        }
        return 0.0;
    }

    public Double convertPlaceIdToLng(int id){
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.longitude;
            }
        }
        return 0.0;
    }

    public List<TimetableModel> getTimetableModelsByArtistId(int id){
        List<TimetableModel> result = new ArrayList<>();
        List<TimetableModel> timetableModels = jsonRepository.getTimetableFromJSON();
        for (TimetableModel timetableModel : timetableModels) {
            if (timetableModel.artistId == id) {
                result.add(timetableModel);
            }
        }
        return result;
    }

    public ArtistModel getArtistModelById(int id){
        List<ArtistModel> list = jsonRepository.getArtistsFromJSON();
        for(ArtistModel anArtistModel: list){
            if(anArtistModel.id == id){
                return anArtistModel;
            }
        }
        return new ArtistModel();
    }

    public FoodModel getFoodModelById(int id){
        List<FoodModel> list = jsonRepository.getFoodFromJSON();
        for(FoodModel foodModel: list){
            if(foodModel.id == id){
                return foodModel;
            }
        }
        return new FoodModel();
    }

    public PlaceModel getPlaceModelById(int id){
        List<PlaceModel> placeModels = jsonRepository.getPlacesFromJSON();
        for (PlaceModel placeModel : placeModels) {
            if (placeModel.id == id) {
                return placeModel;
            }
        }
        return new PlaceModel();
    }

    public List<GameTimetableModel> getGameTimetableModelsByGameId(int id) {
        List<GameTimetableModel> result = new ArrayList<>();
        List<GameTimetableModel> timetableModels = jsonRepository.getGameTimetableFromJSON();
        for (GameTimetableModel timetableModel : timetableModels) {
            if (timetableModel.gameId == id) {
                result.add(timetableModel);
            }
        }
        return result;
    }

    public GameModel getGameModelById(int id){
        List<GameModel> list = jsonRepository.getGamesFromJSON();
        for(GameModel gameModel: list){
            if(gameModel.id == id){
                return gameModel;
            }
        }
        return new GameModel();
    }

    public List<BaseTimetable> getTimetablesByStageIdAndByDay(int dayNumber, int stageId){
        List<BaseTimetable> stageTimetable = new ArrayList<>();
        List<TimetableModel> timetableModels = jsonRepository.getTimetableFromJSON();
        for (TimetableModel timetableModel : timetableModels) {
            if (timetableModel.stageId == stageId && timetableModel.day == dayNumber) {
                stageTimetable.add(timetableModel);
            }
        }
        List<GameTimetableModel> gameTimetableModels = jsonRepository.getGameTimetableFromJSON();
        for (GameTimetableModel timetableModel : gameTimetableModels) {
            GameModel gameModel = getGameModelById(timetableModel.gameId);
            if (gameModel.placeId == stageId && timetableModel.day == dayNumber) {
                stageTimetable.add(timetableModel);
            }
        }
        Collections.sort(stageTimetable, new TimetableListComparator());
        return stageTimetable;
    }

    public TimetableModel getTimetable(List<TimetableModel> timetableModels, int number){
        TimetableModel timetableModel = new TimetableModel();
        if(timetableModels.size() > number)
            timetableModel = timetableModels.get(number);
        return timetableModel;
    }

    public List<TimetableModel> findNowTimetables(int dayNumber, List<TimetableModel> timetables){
        List<TimetableModel> result = new ArrayList<>();
        for (TimetableModel timetable : timetables) {
            if (timetable.day == dayNumber) {
                if (DateController.calculateIsItNow(dayNumber, timetable.start_time, timetable.end_time)) {
                    result.add(timetable);
                }
            }
        }
        return result;
    }
}
