package com.example.zece.healthtracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Patient.class},version = 2)
public abstract class MyAppDatabase extends RoomDatabase {
public abstract DAO DAO();

private static MyAppDatabase myAppDatabase;
    public static MyAppDatabase getAppDatabase(Context context) {
        if (myAppDatabase == null) {
            myAppDatabase = Room.databaseBuilder(context.getApplicationContext(), MyAppDatabase.class, "carotiddb")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return myAppDatabase;
    }
    public static void destroyInstance() {
        myAppDatabase = null;
    }


}
