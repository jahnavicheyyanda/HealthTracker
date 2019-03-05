package com.example.zece.healthtracker.Database;

import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Patient.class, Record.class},version = 6)
public abstract class MyAppDatabase extends RoomDatabase {

public abstract DAO mDao();

//DB is made singleton to prevent having multiple instances of the database opened at the same time.
private static volatile MyAppDatabase patientRoomInstance;

public static MyAppDatabase getDatabase(final Context context) {
    if (patientRoomInstance == null){
        synchronized (MyAppDatabase.class){
            if (patientRoomInstance == null) {
                patientRoomInstance = Room.databaseBuilder(context.getApplicationContext(), MyAppDatabase.class, "carotiddb")
                        .build();
            }
        }
    }
        return patientRoomInstance;
    }
}
