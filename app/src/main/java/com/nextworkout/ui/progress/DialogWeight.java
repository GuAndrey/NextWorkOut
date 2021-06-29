package com.nextworkout.ui.progress;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.nextworkout.R;
import com.nextworkout.databinding.DialogWeightBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;


public class DialogWeight extends DialogFragment {

    private EditText editText;
    private CalendarView calendarView;
    private Calendar calendar;
    private Button button;
    private String spinner;

    DialogWeightBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogWeightBinding.inflate(inflater, container, false);
        editText = binding.addWeightInput;
        calendarView = binding.calendarWeight;
        button = binding.btnOKDialog;
        spinner = getArguments().getString("Spinner");
        calendar = Calendar.getInstance();


        long days = Long.parseLong(spinner.split(" ")[0]);
        calendarView.setMinDate(calendar.getTimeInMillis() - (days * 24 * 60 * 60 * 1000));
        calendarView.setMaxDate(calendar.getTimeInMillis());

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DATE, dayOfMonth);
        });
        button.setOnClickListener(v -> {
            Log.d("Calendar", spinner);
            Fragment pf = getParentFragmentManager().getFragments().get(0);
            ((DialogDismissListener)pf).onDialogDismiss(editText.getText().toString(), calendar);
            dismiss();
        });

        return binding.getRoot();
    }

    public interface DialogDismissListener{
        void onDialogDismiss(String weight, Calendar date);
    }
}

