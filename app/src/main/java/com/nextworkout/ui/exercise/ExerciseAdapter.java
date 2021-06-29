package com.nextworkout.ui.exercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.nextworkout.AppDatabase;
import com.nextworkout.MainActivity;
import com.nextworkout.R;
import com.nextworkout.TempDatabase;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.WeekdayDao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ItemViewHolder> {

    List<Exercise> exerciseList;
    private static Exercise exercise;

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_exercise, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.name.setText(exercise.getName());

        if (exercise.isTimeOrIteration()){
            holder.type.setText("раз.");
        } else {
            holder.type.setText("мин.");
        }

        holder.btnAdd.setOnClickListener(v -> {
            int count;

            try{
                count = Integer.parseInt(holder.count.getText().toString());
            } catch (NumberFormatException e){
                e.printStackTrace();
                count = 0;
            }

            if (count > 0) {
                ExercisesWithWeekdaysEntity ewwe = new ExercisesWithWeekdaysEntity(DayAdapter.getWeekday().get_id(), exercise.get_id());
                ewwe.setToDo(count);

                WeekdayDao weekdayDao = AppDatabase.get(holder.itemView.getContext()).weekdayDao();
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    weekdayDao.insertExercise(ewwe);
                });
            }
        });

        holder.itemView.setOnClickListener(v -> {
            ExerciseAdapter.exercise = exercise;
            MainActivity.navController.navigate(R.id.action_nav_exercises_to_nav_detail);
        });

    }

    @Override
    public int getItemCount() {
        if (exerciseList == null) return 0;
        return exerciseList.size();
    }

    public void setData(List<Exercise> data) {
        this.exerciseList = data;
        notifyDataSetChanged();
    }

    public static Exercise getExercise() {
        return exercise;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView type;
        EditText count;
        Button btnAdd;
        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rc_exercise_name);
            type = itemView.findViewById(R.id.type_in_exercise);
            TextInputLayout _count = itemView.findViewById(R.id.edit_count_in_exercise);
            count = _count.getEditText();
            btnAdd = itemView.findViewById(R.id.btn_add_exercise_in_list);
        }
    }
}
