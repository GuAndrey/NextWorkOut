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
public interface WeekdayDao {
    @Query("SELECT _id, name, time from weekday")
    LiveData<List<WeekdayWithExercises>> getWeekdayWithExercises();

    @Query("SELECT _id, name, time from weekday where _id LIKE :id")
    LiveData<WeekdayWithExercises> getWeekdayWithExercisesById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Weekday... weekdays);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExercise(ExercisesWithWeekdaysEntity exercisesWithWeekdaysEntity);

    @Delete
    void delete(ExercisesWithWeekdaysEntity exercisesWithWeekdaysEntity);

    @Query("SELECT * FROM weekday")
    LiveData<List<Weekday>> getAllWeekdays();

    @Update
    void update(Weekday weekday);

    @Query("SELECT * FROM weekday WHERE _id LIKE :id")
    Weekday findById(int id);
}
