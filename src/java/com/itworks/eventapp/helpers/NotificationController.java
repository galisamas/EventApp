package com.itworks.eventapp.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.itworks.eventapp.models.BaseTimetable;
import com.itworks.eventapp.models.NotificationModel;

import java.util.Calendar;

public class NotificationController {

    public static void setAlarm(Context context, String title, int id, boolean isItArtist, String where, BaseTimetable timetable) {

        String timeFormat;
        if(timetable instanceof NotificationModel){
            timeFormat = timetable.day + "/" + timetable.start_time;
        }else{
            timeFormat = DateController.minusTenMinutes(timetable.day + "/" + timetable.start_time);
        }
        int hour = DateController.getHour(timeFormat);
        int minute = DateController.getMinutes(timeFormat);
        int day = DateController.defaultCalendar(DateController.getDay(timeFormat)).get(Calendar.DAY_OF_MONTH);
        if(hour<7){
            day++;
        }
        Calendar today = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, DateController.FESTIVAL_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if(calendar.after(today)) {
            Intent intent = new Intent(context , NotificationReceiver.class);
            intent.putExtra("name", title);
            intent.putExtra("where", where);
            intent.putExtra("id", id);
            intent.putExtra("isItArtist", isItArtist);
            if(!isItArtist){
                id += 200;
            }
            if(timetable instanceof NotificationModel){
                intent.putExtra("isItGeneral", true);
                id += 100;
            }
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }
    }

    public static void cancelAlarm(Context context, int id){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, new Intent(context , NotificationReceiver.class), 0);
        alarmManager.cancel(pendingIntent);
    }
}
