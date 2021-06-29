package com.nextworkout.ui.today;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextworkout.R;
import com.nextworkout.databinding.FragmentTodayBinding;
import com.nextworkout.ui.diffItems.DiffItemEWWE;

public class TodayFragment extends Fragment {

    private TodayViewModel mViewModel;
    private FragmentTodayBinding binding;

    public static TodayFragment newInstance() {
        return new TodayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTodayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TodayViewModel.class);
        TodayExercisesAdapter todayExercisesAdapter = new TodayExercisesAdapter(new DiffItemEWWE());
        binding.rcInToday.setAdapter(todayExercisesAdapter);

        mViewModel.weekdayWithExercise.observe(getViewLifecycleOwner(), weekdayWithExercises ->{
                if (weekdayWithExercises != null)
                    if (weekdayWithExercises.getExercises() == null || weekdayWithExercises.getExercises().size() < 1){
                        binding.todayFragLabel.setText("Сегодня отдыхаем)");
                    }
                    todayExercisesAdapter.submitList(weekdayWithExercises.getExercises());
        });
    }

}