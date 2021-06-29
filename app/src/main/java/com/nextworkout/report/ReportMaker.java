package com.nextworkout.report;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nextworkout.alarm.MyNotificationManager;
import com.nextworkout.alarm.NotificationReceiver;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ReportMaker {
    private static AlarmManager alarmManager;

    public static void makeReport(Context context){
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        calendar.add(Calendar.DAY_OF_WEEK, 7);

        Intent myIntent = new Intent(context, ReportReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 111, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
        Log.d("CheckAlarm", "set report on " + calendar.getTime());

    }

    public static void setAlarmManager(AlarmManager alarmManager) {
        ReportMaker.alarmManager = alarmManager;
    }
}
