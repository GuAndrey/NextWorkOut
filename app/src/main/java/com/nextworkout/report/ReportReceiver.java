package com.nextworkout.report;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.nextworkout.AppDatabase;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.ExercisesDao;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.ReportDao;
import com.nextworkout.models.ReportExercises;

import java.util.List;

public class ReportReceiver extends BroadcastReceiver {
    private AppDatabase bd;
    private ReportDao reportDao;
    private List<ExercisesWithWeekdaysEntity> data;


    @Override
    public void onReceive(Context context, Intent intent) {
        bd = AppDatabase.get(context);
        reportDao = bd.reportDao();

        Log.d("CheckAlarm", "start making report");
        AppDatabase.databaseWriteExecutor.execute(() -> {
            reportDao.deleteAll();
            data = reportDao.getAllExercises();
            for (int i = 0; i < data.size(); i++) {
                reportDao.insertOne(new ReportExercises(data.get(i)));
                data.get(i).setCountDone(0);
                data.get(i).setDone(false);
                bd.exercisesDao().updateExercisesWithWeekdays(data.get(i));
            }
        });
        Log.d("CheckAlarm", "made report");

    }
}
