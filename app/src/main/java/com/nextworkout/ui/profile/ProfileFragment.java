package com.nextworkout.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nextworkout.MainActivity;
import com.nextworkout.R;
import com.nextworkout.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding.btnAdd.setOnClickListener(v -> {

            String name = binding.inputName.getEditText().getText().toString();
            int age;
            int height;
            int weight;

            try {
                age = Integer.parseInt(binding.inputAge.getEditText().getText().toString());
            } catch (NumberFormatException e){
                age = 0;
            }
            try {
                height = Integer.parseInt(binding.inputHeight.getEditText().getText().toString());
            } catch (NumberFormatException e){
                height = 0;
            }
            try {
                weight = Integer.parseInt(binding.inputWeight.getEditText().getText().toString());
            } catch (NumberFormatException e){
                weight = 0;
            }

            mViewModel.createPerson(name, age, height, weight);

            MainActivity.navController.navigate(R.id.action_nav_profile_to_nav_today);
        });

        mViewModel.people.observe(getViewLifecycleOwner(), people -> {
            if (people != null){
                if (people.size() > 0){
                    MainActivity.navController.navigate(R.id.action_nav_profile_to_nav_today);
                }
            }
        });
    }

}