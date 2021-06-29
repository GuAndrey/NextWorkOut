package com.nextworkout.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TempExercisesDao {
    @Query("SELECT * FROM exercise")
    LiveData<List<Exercise>>getAllExercises();
}
