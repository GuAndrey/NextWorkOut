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
public interface TempWeekdayDao {
    @Query("SELECT * FROM weekday")
    LiveData<List<Weekday>> getAllWeekdays();
}
