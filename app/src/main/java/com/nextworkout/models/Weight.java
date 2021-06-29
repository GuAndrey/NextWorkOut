package com.nextworkout.models;

import androidx.room.Entity;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity(tableName = "weight",
        primaryKeys = {"day", "month", "year"})
public class Weight {
    private int day;
    private int month;
    private int year;
    private int weight;

    public Weight(int day, int month, int year, int weight) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.weight = weight;
    }

    public Weight(int weight, Calendar calendar) {
        this.day = calendar.getTime().getDate();
        this.month = calendar.getTime().getMonth() + 1;
        this.year = calendar.getTime().getYear() + 1900;
        this.weight = weight;
    }

    public Calendar getDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        calendar.set(Calendar.MONDAY, this.month - 1);
        calendar.set(Calendar.YEAR, this.year);
        return calendar;
    }

    public void setDate(Calendar calendar){
        this.day = calendar.getTime().getDate();
        this.month = calendar.getTime().getMonth() + 1;
        this.year = calendar.getTime().getYear() + 1900;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
