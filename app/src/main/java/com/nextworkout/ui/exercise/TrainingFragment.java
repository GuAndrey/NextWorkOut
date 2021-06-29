package com.nextworkout.ui.exercise;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextworkout.databinding.FragmentTrainingBinding;
import com.nextworkout.ui.diffItems.DiffItemWeekdayWithExercises;

public class TrainingFragment extends Fragment {

    private TrainingViewModel mViewModel;
    private FragmentTrainingBinding binding;
    private static TrainingFragment instance;


    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        instance = this;
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);
        DayAdapter dayAdapter = new DayAdapter(new DiffItemWeekdayWithExercises());

        binding.daysList.setAdapter(dayAdapter);

        mViewModel.weekdayWithExercise.observe(getViewLifecycleOwner(), weekdays -> {
            Log.d("Problem_with_bd", "data_update");
            if (weekdays != null){
                Log.d("Problem_with_bd", "data_update too");
                dayAdapter.submitList(weekdays);
            }
        });

        binding.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info;
                info = mViewModel.getInfo();

                Bundle arg = new Bundle();
                arg.putString("info", info);

                InfoDialog infoDialog = new InfoDialog();
                infoDialog.setArguments(arg);
                infoDialog.show(getParentFragmentManager(), "INFO");
            }
        });
    }

    public static TrainingFragment getInstance() {
        return instance;
    }
}