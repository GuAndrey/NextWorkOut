package com.nextworkout;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nextworkout.models.Exercise;
import com.nextworkout.models.ExercisesDao;
import com.nextworkout.models.ExercisesWithWeekdaysEntity;
import com.nextworkout.models.Person;
import com.nextworkout.models.PersonDao;
import com.nextworkout.models.TempExercisesDao;
import com.nextworkout.models.TempWeekdayDao;
import com.nextworkout.models.Weekday;
import com.nextworkout.models.WeekdayDao;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Weekday.class, Exercise.class},
        version = 1)
public abstract class TempDatabase extends RoomDatabase {

    public abstract TempExercisesDao exercisesDao();
    public abstract TempWeekdayDao weekdayDao();

    private static final String DB_NAME="TempBD.db";
    private static volatile TempDatabase INSTANCE=null;

    public synchronized static TempDatabase get(Context ctx) {
        if (INSTANCE==null) {
            INSTANCE=create(ctx);
        }
        return(INSTANCE);
    }

    private static TempDatabase create(Context ctx) {
        TempDatabase b;

        b = Room.databaseBuilder(ctx.getApplicationContext(), TempDatabase.class, DB_NAME)
                .createFromAsset("database/WorkOutExercise.db")
                .fallbackToDestructiveMigration()
                .build();

        return b;
    }

}
