package com.nextworkout.ui.progress;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nextworkout.AppDatabase;
import com.nextworkout.databinding.FragmentProgressBinding;
import com.nextworkout.models.Person;
import com.nextworkout.models.PersonDao;
import com.nextworkout.models.Weight;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressViewModel extends AndroidViewModel {
    private AppDatabase bd;
    private PersonDao personDao;

    public LiveData<List<Person>> people;
    public LiveData<List<Weight>> weight;

    public ProgressViewModel(@NonNull @NotNull Application application) {
        super(application);

        bd = AppDatabase.get(application);
        personDao = bd.personDao();

        people = personDao.getAllPeople();
        weight = personDao.getAllWeight();
    }

    public void updatePerson(FragmentProgressBinding binding){
        String name;
        int age;
        int height;

        name = binding.personName.getEditText().getText().toString();

        try {
            age = Integer.parseInt(binding.personAge.getEditText().getText().toString());
        } catch (NumberFormatException e){
            age = 0;
        }

        try {
            height = Integer.parseInt(binding.personHeight.getEditText().getText().toString());
        } catch (NumberFormatException e){
            height = 0;
        }

        Person person = new Person(name, age, height);
        person.set_id(1);
        AppDatabase.databaseWriteExecutor.execute(() -> personDao.updatePerson(person));
    }

    public void addWeight(int weight, Calendar date){
        Weight w = new Weight(weight, date);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            personDao.insertWeight(w);
            if (weight==0){
                personDao.deleteWeight(w);
            }
        });
    }

    public void deleteWeight(){
        AppDatabase.databaseWriteExecutor.execute(() -> personDao.deletAllWeight());
    }

}