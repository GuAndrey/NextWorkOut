package com.nextworkout.ui.diffItems;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.nextworkout.models.ExercisesWithWeekdaysEntity;

import org.jetbrains.annotations.NotNull;

public class DiffItemEWWE extends DiffUtil.ItemCallback<ExercisesWithWeekdaysEntity>{
        @Override
        public boolean areItemsTheSame(@NonNull ExercisesWithWeekdaysEntity oldItem, @NotNull ExercisesWithWeekdaysEntity newItem) {
                return oldItem.getId_exercise() == newItem.getId_exercise() && oldItem.getId_weekday() == newItem.getId_weekday();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExercisesWithWeekdaysEntity oldItem, @NotNull ExercisesWithWeekdaysEntity newItem) {
                return oldItem.equals(newItem);
        }
}
