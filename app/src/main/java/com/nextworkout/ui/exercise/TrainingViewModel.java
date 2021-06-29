package com.nextworkout.ui.exercise;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nextworkout.AppDatabase;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.ExercisesDao;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.Person;
import com.nextworkout.models.PersonDao;
import com.nextworkout.models.ReportDao;
import com.nextworkout.models.Weekday;
import com.nextworkout.models.WeekdayDao;
import com.nextworkout.models.WeekdayWithExercises;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

public class TrainingViewModel extends AndroidViewModel {
    private AppDatabase bd;
    private WeekdayDao weekdayDao;
    private ExercisesDao exercisesDao;
    private ReportDao reportDao;


    public LiveData<List<WeekdayWithExercises>> weekdayWithExercise;

    public TrainingViewModel(@NonNull @NotNull Application application) {
        super(application);

        bd = AppDatabase.get(application);
        weekdayDao = bd.weekdayDao();
        exercisesDao = bd.exercisesDao();
        reportDao = bd.reportDao();
        weekdayWithExercise = weekdayDao.getWeekdayWithExercises();
    }

    public String getInfo() {
        HashMap<String, Integer> counter = new HashMap<>();
        final boolean[] flag = {false};
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<ExercisesWithWeekdaysEntity> ewwe = reportDao.getAllExercises();
            for (int i = 0; i < ewwe.size(); i++){
                 String muscle = exercisesDao.findById(ewwe.get(i).getId_exercise()).getMuscle();
                 if (!counter.containsKey(muscle)){
                     counter.put(muscle, 1);
                 } else {
                     counter.put(muscle, counter.get(muscle)+1);
                 }
            }
            flag[0] = true;
        });
        while (!flag[0]) Log.d("Preparing", "counter++");

        String text = "Задействованы группы мышц:\n";

        for (String key: counter.keySet()) {
            text += String.format("%s -- %d упр.\n", key, counter.get(key));
        }

        Log.d("MusInfo", counter.toString());
        return text;
    }
}