package com.nextworkout.ui.diffItems;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.WeekdayWithExercises;


public class DiffItemWeekdayWithExercises extends DiffUtil.ItemCallback<WeekdayWithExercises>{
    @Override
    public boolean areItemsTheSame(@NonNull WeekdayWithExercises oldItem, @NonNull WeekdayWithExercises newItem) {
        return oldItem.get_id() == newItem.get_id();
    }

    @Override
    public boolean areContentsTheSame(@NonNull WeekdayWithExercises oldItem, @NonNull WeekdayWithExercises newItem) {
        return oldItem.equals(newItem);
    }
}
