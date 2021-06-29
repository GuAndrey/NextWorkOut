package com.nextworkout.ui.today;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nextworkout.AppDatabase;
import com.nextworkout.R;
import com.nextworkout.databinding.ItemTodayExercisesBinding;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.ExercisesDao;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.WeekdayWithExercises;
import com.nextworkout.ui.exercise.ExercisesInDayAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;

public class TodayExercisesAdapter extends ListAdapter<ExercisesWithWeekdaysEntity, TodayExercisesAdapter.ItemViewHolder> {

    protected TodayExercisesAdapter(@NonNull DiffUtil.ItemCallback<ExercisesWithWeekdaysEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_today_exercises, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ExercisesDao exercisesDao = AppDatabase.get(holder.itemView.getContext()).exercisesDao();

        final Exercise[] ex = new Exercise[1];
        AppDatabase.databaseWriteExecutor.execute(() ->
                ex[0] = exercisesDao.findById(getItem(position).getId_exercise()));
        while (ex[0] == null) Log.w("Preparing", "today exercise");
        holder.label.setText(ex[0].getName());

        String stringCount;
        if (ex[0].isTimeOrIteration()){
            stringCount = "раз";
        }else {
            stringCount= "мин";
        }
        stringCount = "из " + getItem(position).getToDo() + " " + stringCount;
        holder.countToDo.setText(stringCount);

        holder.count.setText("" + getItem(position).getCountDone());

        holder.count.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_ENTER))
            {
                holder.count.clearFocus();
                return true;
            }
            return false;
        });

        holder.count.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){

                if (holder.count.getText().toString().equals("")){
                    holder.count.setText("0");
                }

                getItem(position).setCountDone(Integer.parseInt(holder.count.getText().toString()));

                AppDatabase.databaseWriteExecutor.execute(() ->
                        exercisesDao.updateExercisesWithWeekdays(getItem(position)));

                if (getItem(position).getToDo()<=getItem(position).getCountDone()){
                    getItem(position).setDone(true);
                } else {
                    getItem(position).setDone(false);
                }
                holder.checkDone.setChecked(getItem(position).isDone());
            }
        });

        holder.checkDone.setChecked(getItem(position).isDone());

        holder.checkDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && getItem(position).getCountDone() < getItem(position).getToDo()){
                getItem(position).setCountDone(getItem(position).getToDo());
                holder.count.setText("" + getItem(position).getCountDone());
            } else if (!isChecked && getItem(position).getCountDone() >= getItem(position).getToDo()){
                getItem(position).setCountDone(0);
                holder.count.setText("" + getItem(position).getCountDone());
            }

            getItem(position).setDone(isChecked);
            AppDatabase.databaseWriteExecutor.execute(() ->
                    exercisesDao.updateExercisesWithWeekdays(getItem(position)));
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        CheckBox checkDone;
        TextView countToDo;
        EditText count;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.today_exercise);
            checkDone = itemView.findViewById(R.id.check_ex_done);
            countToDo = itemView.findViewById(R.id.type_in_today);
            TextInputLayout _count = itemView.findViewById(R.id.edit_count_in_today);
            count = _count.getEditText();
        }

    }
}
