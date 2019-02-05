package com.example.zece.healthtracker.Database;

import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.DateRoomConverter;


@Database(entities = {Patient.class},version = 1)
@TypeConverters({DateRoomConverter.class})
public abstract class MyAppDatabase extends RoomDatabase {

public abstract DAO mDao();

private static volatile MyAppDatabase patientRoomInstance;

public static MyAppDatabase getDatabase(final Context context) {
    if (patientRoomInstance == null){
        synchronized (MyAppDatabase.class){
            if (patientRoomInstance == null) {
                patientRoomInstance = Room.databaseBuilder(context.getApplicationContext(), MyAppDatabase.class, "carotiddb")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .build();
            }
        }
    }
        return patientRoomInstance;
    }
}
