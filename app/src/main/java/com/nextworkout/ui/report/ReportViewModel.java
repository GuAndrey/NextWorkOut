package com.nextworkout.ui.report;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nextworkout.AppDatabase;
import com.nextworkout.R;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.Person;
import com.nextworkout.models.PersonDao;
import com.nextworkout.models.ReportDao;
import com.nextworkout.models.ReportExercises;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

public class ReportViewModel extends AndroidViewModel {
    private AppDatabase bd;
    private ReportDao reportDao;

    private LiveData<List<ReportExercises>> reportExercises;

    public ReportViewModel(@NonNull @NotNull Application application) {
        super(application);
        bd = AppDatabase.get(application);
        reportDao = bd.reportDao();

        reportExercises = reportDao.getAll();
    }

    public LiveData<List<ReportExercises>> getReportExercises() {
        return reportExercises;
    }

    public String getReport(List<ReportExercises> reportExercises, Resources resources, Context context) {
        String text = null;
        if (reportExercises == null || reportExercises.size() < 1){
            text = "Отчет ещё не сформирован";
        } else {

            reportExercises.sort(new Comparator<ReportExercises>() {
                @Override
                public int compare(ReportExercises o1, ReportExercises o2) {
                    int x = o1.getExercises().getId_weekday();
                    int y = o2.getExercises().getId_weekday();
                    return Integer.compare(x, y);
                }
            });

            float countToDO = 0;
            float countDone = 0;

            text = "На прошлой неделе вы выполнили: \n";
            int weekdayId = 0;
            for (int i = 0; i < reportExercises.size(); i++){

                //День недели
                if (weekdayId != reportExercises.get(i).getExercises().getId_weekday()){
                    weekdayId = reportExercises.get(i).getExercises().getId_weekday();
                    text += "\n" + resources.getStringArray(R.array.weekdays)[weekdayId - 1] + "\n";
                }

                countDone += reportExercises.get(i).getExercises().getCountDone();
                countToDO += reportExercises.get(i).getExercises().getToDo();

                int ex_id = reportExercises.get(i).getExercises().getId_exercise();
                final Exercise[] ex = new Exercise[1];
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    ex[0] = AppDatabase.get(context).exercisesDao().findById(ex_id);
                });
                while (ex[0] == null) Log.d("Preparing", "reportExercises");

                text += ex[0].getName();
                text += " " + reportExercises.get(i).getExercises().getCountDone();
                if (!ex[0].isTimeOrIteration()) text += " минут";
                else text += " раз";
                text += " из";
                text += " " + reportExercises.get(i).getExercises().getToDo();
                text += "\n";

            }

            int percent = Math.round(countDone/countToDO*100);

            text += String.format("\nВыполнено %d%% запланированного.\n", percent);

            if (percent > 90){
                text += "Отличный результат";
            } else if (percent > 80) {
                text += "Хорошо, но могло быть и лучше.";
            } else if (percent > 60) {
                text += "Не отлично, но и не ужасно.";
            } else {
                text += "Выидимо на этой неделе у Вас были дела поважнее.";
            }

        }

        return text;
    }
}