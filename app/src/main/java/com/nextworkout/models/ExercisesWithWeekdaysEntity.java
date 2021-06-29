package com.nextworkout.models;

import androidx.annotation.Nullable;

import androidx.room.Entity;
import androidx.room.ForeignKey;



@Entity(foreignKeys = {
        @ForeignKey(entity = Exercise.class,
                parentColumns = "_id",
                childColumns = "id_exercise",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Weekday.class,
                parentColumns = "_id",
                childColumns = "id_weekday",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)},
        primaryKeys = {"id_weekday", "id_exercise"})
public class ExercisesWithWeekdaysEntity {
    private int id_weekday;
    private int id_exercise;
    private int toDo;
    private int countDone;
    private boolean done;

    public ExercisesWithWeekdaysEntity(int id_weekday, int id_exercise) {
        this.id_weekday = id_weekday;
        this.id_exercise = id_exercise;
        this.done = false;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!obj.getClass().equals(this.getClass())) return false;
        ExercisesWithWeekdaysEntity that = (ExercisesWithWeekdaysEntity) obj;
        return this.getId_weekday() == that.getId_weekday() &&
                this.getId_exercise() == that.getId_exercise() &&
                this.getToDo() == that.getToDo() &&
                this.getCountDone() == that.getCountDone() &&
                this.isDone() == that.isDone();
    }

    public int getToDo() {
        return toDo;
    }

    public void setToDo(int toDo) {
        this.toDo = toDo;
    }

    public int getCountDone() {
        return countDone;
    }

    public void setCountDone(int countDone) {
        this.countDone = countDone;
    }

    public int getId_weekday() {
        return id_weekday;
    }

    public int getId_exercise() {
        return id_exercise;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
