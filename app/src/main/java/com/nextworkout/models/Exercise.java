package com.nextworkout.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String name;
    private String path;
    private String details;
    private String muscle;
    private boolean timeOrIteration;
    private String equipment;
    private String difficulty;
    private String type;

    public Exercise(String name, String path, String details, String muscle, boolean timeOrIteration,
                    String equipment, String difficulty, String type) {
        this.name = name;
        this.path = path;
        this.details = details;
        this.muscle = muscle;
        this.timeOrIteration = timeOrIteration;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public String getDetails() {
        return details;
    }

    public String getMuscle() {
        return muscle;
    }

    public boolean isTimeOrIteration() {
        return timeOrIteration;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getType() {
        return type;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!obj.getClass().equals(this.getClass())) return false;
        Exercise that = (Exercise) obj;
        return this.get_id() == that.get_id() &&
                this.getName().equals(that.getName()) &&
                this.getDetails().equals(that.getDetails()) &&
                this.getDifficulty().equals(that.getDifficulty()) &&
                this.getEquipment().equals(that.getEquipment()) &&
                this.getMuscle().equals(that.getMuscle()) &&
                this.getPath().equals(that.getPath()) &&
                this.getType().equals(that.getType()) &&
                this.isTimeOrIteration() == that.isTimeOrIteration();
    }
}
