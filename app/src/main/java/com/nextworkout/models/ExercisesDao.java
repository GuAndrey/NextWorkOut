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
public interface ExercisesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Exercise... exercises);

    @Query("SELECT * FROM exercise")
    LiveData<List<Exercise>>getAllExercises();

    @Query("SELECT * FROM exercise WHERE _id LIKE :id")
    Exercise findById(int id);

    @Query("DELETE FROM exercise WHERE _id LIKE :id")
    void deleteById(int id);

    @Update
    void updateExercisesWithWeekdays(ExercisesWithWeekdaysEntity exercisesWithWeekdaysEntity);
}
