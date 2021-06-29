package com.nextworkout.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nextworkout.models.ExercisesWithWeekdaysEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MyNotificationManager {
    private static AlarmManager alarmManager;

    public static void setNotification(Context context, int dayOfWeek, int hourOfDay, int minute, List<ExercisesWithWeekdaysEntity> exercises){
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek + 1);//с воскресенья отсчёт
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Intent myIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, dayOfWeek, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (calendar.before(Calendar.getInstance())) {
            Log.d("CheckAlarm", "add");
            calendar.add(Calendar.DAY_OF_WEEK, 7);
        }

        if (exercises == null || exercises.size() < 1){
            alarmManager.cancel(pendingIntent);
            Log.d("CheckAlarm", "del from " + calendar.getTime());
        } else {
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
            Log.d("CheckAlarm", "set on " + calendar.getTime());
        }

    }

    public static void setAlarmManager(AlarmManager alarmManager) {
        MyNotificationManager.alarmManager = alarmManager;
    }
}

