package com.nextworkout.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ReportExercises... exercises);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(ReportExercises exercises);

    @Query("DELETE FROM reportExercise")
    void deleteAll();

    @Query("SELECT * FROM reportExercise")
    LiveData<List<ReportExercises>> getAll();

    @Query("SELECT * FROM exerciseswithweekdaysentity")
    List<ExercisesWithWeekdaysEntity> getAllExercises();
}
