package com.nextworkout.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.nextworkout.AppDatabase;
import com.nextworkout.databinding.FragmentExercisesBinding;
import com.nextworkout.models.Exercise;

import java.util.List;

public class ExercisesFragment extends Fragment {

    private FragmentExercisesBinding binding;
    private LiveData<List<Exercise>> exercises;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter();
        binding.rcForExercise.setAdapter(exerciseAdapter);
        exercises = AppDatabase.get(getContext()).exercisesDao().getAllExercises();
        exercises.observe(getViewLifecycleOwner(), exercises1 -> {
            if (exercises1 != null){
                exerciseAdapter.setData(exercises1);
            }
        });
    }
}
