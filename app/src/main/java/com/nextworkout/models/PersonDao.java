package com.nextworkout.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Person... people);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Person person);

    @Delete
    void deletePerson(Person person);

    @Delete
    void deleteWeight(Weight weight);

    @Query("SELECT * FROM person")
    LiveData<List<Person>> getAllPeople();

    @Update
    void updatePerson(Person person);

    @Update
    void updateWeight(Weight weight);
    
    @Query("SELECT * FROM weight")
    LiveData<List<Weight>> getAllWeight();

    @Query("DELETE FROM weight")
    void deletAllWeight();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeight(Weight weight);

    //Получени Person по _id
    @Query("SELECT * FROM person WHERE _id LIKE :id")
    Person findPersonById(int id);

}
