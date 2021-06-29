package com.nextworkout.ui.exercise;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nextworkout.AppDatabase;
import com.nextworkout.MainActivity;
import com.nextworkout.R;
import com.nextworkout.databinding.FragmentExerciseDetailBinding;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.WeekdayDao;


public class DetailFragment extends Fragment {

    private Exercise exercise;
    private FragmentExerciseDetailBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentExerciseDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exercise = ExerciseAdapter.getExercise();

        binding.exDetailLabel.setText(exercise.getName());
        DetailImageAdapter detailImageAdapter = new DetailImageAdapter(exercise, getContext().getApplicationContext());
        binding.viewPager2.setAdapter(detailImageAdapter);

        binding.exDetailDetail.setText(exercise.getDetails());
        binding.groupOfMuscle.setText(exercise.getMuscle());
        binding.difficultyExercise.setText(exercise.getDifficulty());
        binding.typeExercise.setText(exercise.getType());

        if (exercise.isTimeOrIteration()){
            binding.typeInExerciseDetail.setText("раз.");
        } else {
            binding.typeInExerciseDetail.setText("мин.");
        }

        binding.btnAddExerciseInDetail.setOnClickListener(v -> {
            int count;

            try{
                count = Integer.parseInt(binding.editCountInExerciseDetail.getEditText().getText().toString());
            } catch (NumberFormatException e){
                e.printStackTrace();
                count = 0;
            }

            if (count > 0){
                ExercisesWithWeekdaysEntity ewwe = new ExercisesWithWeekdaysEntity(DayAdapter.getWeekday().get_id(), exercise.get_id());
                ewwe.setToDo(count);
                WeekdayDao weekdayDao = AppDatabase.get(getContext()).weekdayDao();
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    weekdayDao.insertExercise(ewwe);
                });

                MainActivity.navController.navigate(R.id.action_nav_detail_to_nav_training);
            }

        });
    }

}
