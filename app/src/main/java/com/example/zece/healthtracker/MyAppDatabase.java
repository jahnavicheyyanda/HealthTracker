package com.example.zece.healthtracker;

import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Patient.class},version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
public abstract DAO mDao();

private static volatile MyAppDatabase patientRoomInstance;

static MyAppDatabase getDatabase(final Context context) {
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
