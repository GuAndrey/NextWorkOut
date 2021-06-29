package com.nextworkout.ui.exercise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InfoDialog extends DialogFragment {

    private String info;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable  Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        info = getArguments().getString("info");

        builder.setMessage(info)
                .setPositiveButton("Ok", (dialog, which) -> {});

        return builder.create();
    }
}
