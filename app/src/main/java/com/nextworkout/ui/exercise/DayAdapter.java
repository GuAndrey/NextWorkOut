package com.nextworkout.ui.exercise;

import android.app.TimePickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nextworkout.AppDatabase;
import com.nextworkout.MainActivity;
import com.nextworkout.R;

import com.nextworkout.alarm.MyNotificationManager;
import com.nextworkout.models.Weekday;
import com.nextworkout.models.WeekdayWithExercises;
import com.nextworkout.ui.diffItems.DiffItemEWWE;


import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class DayAdapter extends ListAdapter<WeekdayWithExercises, DayAdapter.ItemViewHolder> {

    private static WeekdayWithExercises weekday;

    protected DayAdapter(@NonNull @NotNull DiffUtil.ItemCallback<WeekdayWithExercises> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_day, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        WeekdayWithExercises weekdayWithExercises = getItem(position);
        holder.bindAll(weekdayWithExercises);
    }


    public static WeekdayWithExercises getWeekday() {
        return weekday;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final RecyclerView rv;
        private final Button bntAdd;
        private final TextView time;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dayName);
            rv = itemView.findViewById(R.id.rv_in_day);
            bntAdd = itemView.findViewById(R.id.btn_add_exercise);
            time = itemView.findViewById(R.id.et_time);
        }

        private void bindAll(WeekdayWithExercises weekdayWithExercises) {
            bindText(weekdayWithExercises);
            bindTime(weekdayWithExercises);
            bindRV(weekdayWithExercises);
            bindBtnAdd(weekdayWithExercises);
        }

        private void bindText(WeekdayWithExercises weekdayWithExercises) {
            name.setText(weekdayWithExercises.getName());
        }

        private void bindBtnAdd(WeekdayWithExercises weekdayWithExercises) {
            bntAdd.setOnClickListener(v -> {
                weekday = weekdayWithExercises;
                MainActivity.navController.navigate(R.id.action_nav_training_to_nav_exercises);
            });
        }

        private void bindRV(WeekdayWithExercises weekdayWithExercises) {
            ExercisesInDayAdapter exercisesInDayAdapter = new ExercisesInDayAdapter(new DiffItemEWWE());
            rv.setAdapter(exercisesInDayAdapter);
            exercisesInDayAdapter.submitList(weekdayWithExercises.getExercises());
        }

        private void bindTime(WeekdayWithExercises weekdayWithExercises) {

            final Weekday[] w = new Weekday[1];
            AppDatabase.databaseWriteExecutor.execute(() ->
                    w[0] = AppDatabase.get(itemView.getContext()).weekdayDao().findById(weekdayWithExercises.get_id()));
            while (w[0] == null){
                Log.e("Executor_test", "finding a week");
            }
            time.setText(w[0].getTimeString());

            Date date = new Date(1,1,1,12,0);

            time.setOnClickListener(v -> new TimePickerDialog(itemView.getContext(), (view, hourOfDay, minute) -> {
                date.setHours(hourOfDay);
                date.setMinutes(minute);
                w[0].setTime(date.getTime());
                AppDatabase.databaseWriteExecutor.execute(() ->
                        AppDatabase.get(itemView.getContext()).weekdayDao().update(w[0]));
            }, 12, 0, true).show());

            Date date1 = new Date(w[0].getTime());

            MyNotificationManager.setNotification(itemView.getContext().getApplicationContext(),
                    weekdayWithExercises.get_id(),
                    date1.getHours(),
                    date1.getMinutes(),
                    weekdayWithExercises.getExercises());
            Log.d("CheckAlarm", "set " + weekdayWithExercises.getExercises().size());
        }
    }
}
