package com.nextworkout.ui.report;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextworkout.AppDatabase;
import com.nextworkout.R;
import com.nextworkout.databinding.FragmentReportBinding;
import com.nextworkout.models.Exercise;
import com.nextworkout.models.ReportExercises;

import java.util.Comparator;
import java.util.List;

public class ReportFragment extends Fragment {

    private ReportViewModel mViewModel;
    private FragmentReportBinding binding;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        mViewModel.getReportExercises().observe(getViewLifecycleOwner(), reportExercises -> {

            binding.reportText.setText(mViewModel.getReport(reportExercises, getResources(), getContext()));

        });
    }

}