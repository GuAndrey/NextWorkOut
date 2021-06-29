package com.nextworkout.ui.progress;

import androidx.core.app.DialogCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.nextworkout.R;
import com.nextworkout.databinding.FragmentProgressBinding;
import com.nextworkout.ui.profile.ProfileFragment;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class ProgressFragment extends Fragment implements DialogWeight.DialogDismissListener, DialogDelete.DeleteWeightListener {

    private ProgressViewModel mViewModel;
    private FragmentProgressBinding binding;
    private DataPoint[] dp;
    private final SimpleDateFormat sdf = new SimpleDateFormat("d MMM");
    private EditText personName;
    private EditText personAge;
    private EditText personHeight;

    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProgressBinding.inflate(inflater, container, false);
        personName = binding.personName.getEditText();
        personAge = binding.personAge.getEditText();
        personHeight = binding.personHeight.getEditText();

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ProgressViewModel.class);

        mViewModel.people.observe(getViewLifecycleOwner(), people -> {
            if (people != null){
                personName.setText(people.get(0).getName());
                personAge.setText(""+people.get(0).getAge());
                personHeight.setText(""+people.get(0).getHeight());
            }
        });

        bindPersonListener();

        Spinner spinner = binding.spinnerRange;

        DialogWeight dialogAdd = new DialogWeight();
        DialogDelete dialogDelete = new DialogDelete();

        Bundle arg = new Bundle();

        binding.btnAddWeight.setOnClickListener(v -> {
            arg.putString("Spinner", spinner.getSelectedItem().toString());
            dialogAdd.setArguments(arg);
            dialogAdd.show(getParentFragmentManager(), "DIALOG ADD");
        });

        binding.btnDeleteWeight.setOnClickListener(v ->
                dialogDelete.show(getParentFragmentManager(), "DIALOG DELETE"));

        GraphView graph = binding.graph;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

        mViewModel.weight.observe(getViewLifecycleOwner(), weights -> {
            if (weights != null){
                dp = new DataPoint[weights.size()];
                for (int i = 0; i < dp.length; i++){
                    dp[i] = new DataPoint(weights.get(i).getDate().getTimeInMillis(), weights.get(i).getWeight());
                }
                Arrays.sort(dp, new Comparator<DataPoint>() {
                    @Override
                    public int compare(DataPoint o1, DataPoint o2) {
                        double x = o1.getX();
                        double y = o2.getX();
                        return Double.compare(x, y);
                    }
                });
                series.resetData(dp);
            }
        });
        graph.setCursorMode(true);
        series.setColor(Color.parseColor("#FFd50000"));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);

        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX){
                    return sdf.format(new Date((long) value));
                }else{
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        //graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setXAxisBoundsManual(true);
        setGraphRange(graph, spinner.getSelectedItem().toString());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                series.resetData(dp);
                setGraphRange(graph, spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setGraphRange(GraphView graphView, String sDays){
        long days = Long.parseLong(sDays.split(" ")[0]);
        graphView.getViewport().setMaxX(Calendar.getInstance().getTimeInMillis());
        graphView.getViewport().setMinX(Calendar.getInstance().getTimeInMillis() - ((days) * 24 * 60 * 60 * 1000));
    }

    private void bindPersonListener() {
        binding.btnEditName.setOnClickListener(v -> {
            personName.setEnabled(true);
            personName.requestFocus();
        });

        binding.btnEditAge.setOnClickListener(v -> {
            personAge.setEnabled(true);
            personAge.requestFocus();
        });

        binding.btnEditHeight.setOnClickListener(v -> {
            personHeight.setEnabled(true);
            personHeight.requestFocus();
        });

        personName.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_ENTER))
            {
                personName.clearFocus();
                return true;
            }
            return false;
        });

        personAge.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_ENTER))
            {
                personAge.clearFocus();
                return true;
            }
            return false;
        });

        personHeight.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_ENTER))
            {
                personHeight.clearFocus();
                return true;
            }
            return false;
        });

        personName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                personName.setEnabled(false);
                mViewModel.updatePerson(binding);
            }
        });

        personAge.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                personAge.setEnabled(false);
                mViewModel.updatePerson(binding);
            }
        });

        personHeight.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                personHeight.setEnabled(false);
                mViewModel.updatePerson(binding);
            }
        });
    }

    @Override
    public void onDialogDismiss(String weight, Calendar date) {
        Log.d("Calendar", "" + weight);
        Log.d("Calendar", "" + date.getTime());
        int w = 0;
        if (!weight.isEmpty()) w = Integer.parseInt(weight);
        mViewModel.addWeight(w, date);
    }

    @Override
    public void onDeleteWeight() {
        Log.d("Calendar", "delete all weight");
        mViewModel.deleteWeight();
    }
}