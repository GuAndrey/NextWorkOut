package com.nextworkout;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
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
import com.nextworkout.models.ReportDao;
import com.nextworkout.models.ReportExercises;
import com.nextworkout.models.TempWeekdayDao;
import com.nextworkout.models.Weekday;
import com.nextworkout.models.WeekdayDao;
import com.nextworkout.models.Weight;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Person.class, Exercise.class,
                    Weekday.class, ExercisesWithWeekdaysEntity.class,
                    ReportExercises.class, Weight.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReportDao reportDao();
    public abstract PersonDao personDao();
    public abstract ExercisesDao exercisesDao();
    public abstract WeekdayDao weekdayDao();
    private static final String DB_NAME="appBD.db";
    private static volatile AppDatabase INSTANCE=null;
    private static final int NUMBER_OF_THREADS = 1;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE IF NOT EXISTS reportExercise (" +
//                    "to Do INTEGER," +
//                    "_id INTEGER PRIMARY KEY NOT NULL, " +
//                    "countDone INTEGER," +
//                    "done INTEGER," +
//                    "id_weekday INTEGER," +
//                    "id_exercise INTEGER" +
//                    ");");
        }
    };


    public synchronized static AppDatabase get(Context ctx) {

        if (INSTANCE==null) {
            Log.d("DateBase", "need create");
            INSTANCE=create(ctx);
        }
        Log.d("DateBase", "return INSTANCE");
        return(INSTANCE);
    }

    private static AppDatabase create(Context ctx) {
        AppDatabase b;

        b = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, DB_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();

        LiveData<List<Weekday>> temp1 = TempDatabase.get(ctx).weekdayDao().getAllWeekdays();
        LiveData<List<Exercise>> temp2 = TempDatabase.get(ctx).exercisesDao().getAllExercises();

        temp1.observeForever(weekdays ->
                databaseWriteExecutor.execute(() ->
                        b.weekdayDao().insertAll(weekdays.toArray(new Weekday[0]))));

        temp2.observeForever(exercises ->
                databaseWriteExecutor.execute(() ->
                        b.exercisesDao().insertAll(exercises.toArray(new Exercise[0]))));

        return b;
    }

}
