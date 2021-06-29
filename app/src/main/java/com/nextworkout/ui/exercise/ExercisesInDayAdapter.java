package com.nextworkout.ui.exercise;

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
import com.nextworkout.R;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.ExercisesDao;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.WeekdayDao;

import org.jetbrains.annotations.NotNull;


public class ExercisesInDayAdapter extends ListAdapter<ExercisesWithWeekdaysEntity, ExercisesInDayAdapter.ItemViewHolder> {

    protected ExercisesInDayAdapter(@NonNull DiffUtil.ItemCallback<ExercisesWithWeekdaysEntity> diffCallback) {
        super(diffCallback);
    }

    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_active_exercise, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ExercisesDao exercisesDao = AppDatabase.get(holder.itemView.getContext()).exercisesDao();
        final Exercise[] exercise = new Exercise[1];

        AppDatabase.databaseWriteExecutor.execute(() -> {
            Log.e("Executor_test", "i started");
            exercise[0] = exercisesDao.findById(getItem(position).getId_exercise());
            Log.e("Executor_test", "i finished");
        });

        while (exercise[0] == null){
            Log.e("Executor_test", "finding an exercise");
        }

        holder.name.setText(exercise[0].getName());

        String count;
        if (exercise[0].isTimeOrIteration()){
            count = "раз.";
        }else {
            count= "мин.";
        }
        count = "" + getItem(position).getToDo() + " " + count;
        holder.count.setText(count);

        holder.btn_del.setOnClickListener(v -> {
            WeekdayDao weekdayDao = AppDatabase.get(holder.itemView.getContext()).weekdayDao();
            ExercisesWithWeekdaysEntity ewwe = getItem(position);
            AppDatabase.databaseWriteExecutor.execute(() -> {
                weekdayDao.delete(ewwe);
            });
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Button btn_del;
        TextView count;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rc_in_day_exercise);
            btn_del = itemView.findViewById(R.id.btn_to_del_exercise);
            count = itemView.findViewById(R.id.count_in_active_exercise);
        }
    }
}
