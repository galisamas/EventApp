package com.itworks.festapp.helpers;

import android.util.Log;

import com.itworks.festapp.DayName;
import com.itworks.festapp.models.BaseTimetable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateController {

    private static final int DAY_HOURS = 24;
    private static final int FESTIVAL_DAY_1 = getDay();
    private static final int FESTIVAL_DAY_2 = FESTIVAL_DAY_1+1;
//    private static final int FESTIVAL_DAY_3 = FESTIVAL_DAY_2+1;
    public static final int FESTIVAL_MONTH = Calendar.getInstance().get(Calendar.MONTH); //Calendar.JULY;


    private static int getDay(){
        int yearDays = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        int answer = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if(yearDays % 2 == 0)
            answer -= 1;
        return answer;
    }
    public static String convertDate(BaseTimetable timetableModel){
        String start = timetableModel.day + "/" + timetableModel.start_time;
        String end = timetableModel.day + "/" + timetableModel.end_time;
        String day = start.substring(0, 1);
        String dayName = "";
        switch(Integer.parseInt(day)){
            case 1:
                dayName = DayName.Day1.toString();
            break;
            case 2:
                dayName = DayName.Day2.toString();
                break;
//            case 3:
//                dayName = DayName.Day3.toString();
//                break;
        }
        return dayName + ", " + convertTimeFWD(start.substring(2), end.substring(2));
    }

    public static String convertTimeFWD(String start, String end){
        String[] startArray = start.split("/");
        String[] endArray = end.split("/");
        return getFullDayHour(startArray[0]) + ":" + startArray[1] + " - " + getFullDayHour(endArray[0]) + ":" + endArray[1];
    }

    public static String convertDateFWE(String start){
        String day = start.substring(0, 1);
        String dayName = "";
        switch(Integer.parseInt(day)){
            case 1:
                dayName = DayName.Day1.toString();
                break;
            case 2:
                dayName = DayName.Day2.toString();
                break;
//            case 3:
//                dayName = DayName.Day3.toString();
//                break;
        }
        return dayName + ", " + convertTimeFWDAndE(start.substring(2));
    }

    public static String convertTimeFWDAndE(String start){
        String[] startArray = start.split("/");
        return getFullDayHour(startArray[0]) + ":" + startArray[1];
    }

    public static int getHourFWD(String time){
        String[] timeArray = time.split("/");
        return Integer.parseInt(timeArray[0]);
    }

    public static int getMinutesFWD(String time){
        String[] timeArray = time.split("/");
        return Integer.parseInt(timeArray[1]);
    }

    public static int getMinutes(String time){
        String[] timeArray = time.split("/");
        return Integer.parseInt(timeArray[2]);
    }

    public static int getHour(String time){
        String[] timeArray = time.split("/");
        return Integer.parseInt(timeArray[1]);
    }

    public static int getDay(String time){
        String[] timeArray = time.split("/");
        return Integer.parseInt(timeArray[0]);
    }

    public static String minusTenMinutes(String time){
        int day = getDay(time);
        Calendar calendar = defaultCalendar(day);
        calendar.set(Calendar.HOUR_OF_DAY, getHour(time));
        calendar.set(Calendar.MINUTE, getMinutes(time));
        calendar.add(Calendar.MINUTE, -10);
        SimpleDateFormat dateFormatter = new SimpleDateFormat(day + "/HH/mm");
        return dateFormatter.format(calendar.getTime());
    }

    public static Calendar defaultCalendar(int dayNumber) {
        int day = (dayNumber == 1)?FESTIVAL_DAY_1:FESTIVAL_DAY_2;
//        switch (dayNumber){
//            case 1:
//                day = FESTIVAL_DAY_1;
//                break;
//            case 2:
//                day = FESTIVAL_DAY_2;
//                break;
//            case 3:
//                day = FESTIVAL_DAY_3;
//                break;
//        }
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(currentDate.get(Calendar.YEAR),FESTIVAL_MONTH,day);
        return currentDate;
    }

    public static int getFestivalDay(){
        int day = -1;
        Calendar today = Calendar.getInstance();
        if(today.get(Calendar.MONTH) == FESTIVAL_MONTH) {
            int todayValue = today.get(Calendar.DAY_OF_MONTH);
            if ((todayValue == FESTIVAL_DAY_1 && today.get(Calendar.HOUR_OF_DAY) > 7 ) ||
                    (todayValue == FESTIVAL_DAY_2 && today.get(Calendar.HOUR_OF_DAY) < 7))
                day = 1;
            else //if(todayValue == FESTIVAL_DAY_2 || (todayValue == FESTIVAL_DAY_1 && today.get(Calendar.HOUR_OF_DAY) < 7))
                day = 2;

//                switch (today.get(Calendar.DAY_OF_MONTH)) {
//                case FESTIVAL_DAY_1:
//                    day = 1;
//                    break;
//                case FESTIVAL_DAY_2:
//                    if (today.get(Calendar.HOUR_OF_DAY) < 7)
//                        day = 1;
//                    else
//                        day = 2;
//                    break;
//                case FESTIVAL_DAY_3:
//                    if (today.get(Calendar.HOUR_OF_DAY) < 7)
//                        day = 2;
//                    break;
//            }
        }
        return day;
    }

    public static int getDayForStage(){
        int day = 1;
        Calendar today = Calendar.getInstance();
        if(today.get(Calendar.MONTH) == DateController.FESTIVAL_MONTH){
            if((today.get(Calendar.DAY_OF_MONTH) == DateController.defaultCalendar(2).get(Calendar.DAY_OF_MONTH) &&
                    today.get(Calendar.HOUR_OF_DAY) > 7 )||
                    today.get(Calendar.DAY_OF_MONTH) -1 == DateController.defaultCalendar(2).get(Calendar.DAY_OF_MONTH)){
                day = 2;
            }
        }
        return day;
    }

    public static boolean calculateIsItNow(int dayNumber, String startTime, String endTime){
        Calendar today = Calendar.getInstance();
        Calendar shownDate = defaultCalendar(dayNumber);
        boolean result = false;
        if (today.get(Calendar.MONTH) == shownDate.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == shownDate.get(Calendar.DAY_OF_MONTH)) {
            if (getHourFWD(startTime) == today.get(Calendar.HOUR_OF_DAY) &&
                    today.get(Calendar.HOUR_OF_DAY) < getHourFWD(endTime) &&
                    getMinutesFWD(startTime) < today.get(Calendar.MINUTE)) {
                result = true;
            } else if (getHourFWD(startTime) < today.get(Calendar.HOUR_OF_DAY) &&
                    today.get(Calendar.HOUR_OF_DAY) == getHourFWD(endTime) &&
                    getMinutesFWD(endTime) > today.get(Calendar.MINUTE)) {
                result = true;
            } else if (getHourFWD(startTime) < today.get(Calendar.HOUR_OF_DAY) &&
                    today.get(Calendar.HOUR_OF_DAY) < getHourFWD(endTime)) {
                result = true;
            }

        } else if (today.get(Calendar.MONTH) == shownDate.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) - 1 == shownDate.get(Calendar.DAY_OF_MONTH)) {
            if (getHourFWD(startTime) == getTodaysHour(today) &&
                    getTodaysHour(today) < getHourFWD(endTime) &&
                    getMinutesFWD(startTime) < today.get(Calendar.MINUTE)) {
                result = true;
            } else if (getHourFWD(startTime) < getTodaysHour(today) &&
                    getTodaysHour(today) == getHourFWD(endTime) &&
                    getMinutesFWD(endTime) > today.get(Calendar.MINUTE)) {
                result = true;
            } else if (getHourFWD(startTime) < getTodaysHour(today) &&
                    getTodaysHour(today) < getHourFWD(endTime)) {
                result = true;
            }
        }
        return result;
    }

    private static int getTodaysHour(Calendar today) {
        return today.get(Calendar.HOUR_OF_DAY) + 24;
    }

    private static String getFullDayHour(String day){
        int hours = Integer.parseInt(day);
        if(hours >= DAY_HOURS){
            hours -= DAY_HOURS;
        }
        return String.format("%d",hours);
    }

    public static boolean timeIsBeforeStart(String start_time) {
        int nowHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return nowHours <= getHourFWD(start_time);
    }
}