package com.nextworkout.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reportExercise")
public class ReportExercises {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    @Embedded
    private ExercisesWithWeekdaysEntity exercises;

    public ReportExercises(ExercisesWithWeekdaysEntity exercises) {
        this.exercises = exercises;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ExercisesWithWeekdaysEntity getExercises() {
        return exercises;
    }


}
