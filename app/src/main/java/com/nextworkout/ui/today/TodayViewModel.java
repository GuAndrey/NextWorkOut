package com.nextworkout.ui.today;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nextworkout.AppDatabase;
import com.nextworkout.models.WeekdayDao;
import com.nextworkout.models.WeekdayWithExercises;

import java.util.Date;
import java.util.List;

public class TodayViewModel extends AndroidViewModel {
    private AppDatabase bd;
    private WeekdayDao weekdayDao;

    public LiveData<WeekdayWithExercises> weekdayWithExercise;

    public TodayViewModel(Application application) {
        super(application);

        bd = AppDatabase.get(application);
        weekdayDao = bd.weekdayDao();

        Date date = new Date();
        int id;
        if (date.getDay() == 0) id=7; else id =date.getDay();
        weekdayWithExercise = weekdayDao.getWeekdayWithExercisesById(id);
    }
}