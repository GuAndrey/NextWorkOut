package com.nextworkout.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nextworkout.AppDatabase;
import com.nextworkout.MainActivity;
import com.nextworkout.R;
import com.nextworkout.models.Person;
import com.nextworkout.models.PersonDao;
import com.nextworkout.models.Weight;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class ProfileViewModel extends AndroidViewModel {
    private AppDatabase bd;
    private PersonDao personDao;

    public LiveData<List<Person>> people;

    public ProfileViewModel(@NonNull @NotNull Application application) {
        super(application);

        bd = AppDatabase.get(application);
        personDao = bd.personDao();

        people = personDao.getAllPeople();
    }

    public void createPerson(String name, int age, int height, int weight){
        Person person = new Person(name, age, height);
        Weight weight1 = new Weight(weight, Calendar.getInstance());
        Executors.newSingleThreadExecutor().execute(() -> {
            personDao.insertWeight(weight1);
            personDao.insertOne(person);
        });
    }
}