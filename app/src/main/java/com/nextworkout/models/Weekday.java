package com.nextworkout.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "weekday")
public class Weekday {

    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String name;
    private long time;

    public Weekday(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!obj.getClass().equals(this.getClass())) return false;
        Weekday that = (Weekday) obj;
        return this.get_id() == that.get_id() &&
                this.getName().equals(that.getName()) &&
                this.getTime() == that.getTime();
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public long getTime() {
        return time;
    }

    public String getTimeString(){
        Date date = new Date(time);
        return "" + date.getHours() + ":" + date.getMinutes();
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }
}
