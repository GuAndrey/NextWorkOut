package com.nextworkout.models;

import androidx.annotation.Nullable;
import androidx.room.Relation;

import java.util.List;

public class WeekdayWithExercises {
    private int _id;
    private String name;
    private long time;

    @Relation(parentColumn = "_id", entityColumn = "id_weekday")
    private List<ExercisesWithWeekdaysEntity> exercises;

    public WeekdayWithExercises(int _id, String name, List<ExercisesWithWeekdaysEntity> exercises, long time) {
        this._id = _id;
        this.name = name;
        this.exercises = exercises;
        this.time = time;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!obj.getClass().equals(this.getClass())) return false;
        WeekdayWithExercises that = (WeekdayWithExercises) obj;
        return this.get_id() == that.get_id() &&
                this.getName().equals(that.getName()) &&
                this.getExercises().equals(that.getExercises()) &&
                this.getTime() == that.getTime();
    }

    public long getTime() {
        return time;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public List<ExercisesWithWeekdaysEntity> getExercises() {
        return exercises;
    }
}
