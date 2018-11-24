package com.example.zece.healthtracker;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Patient.class},version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
public abstract DAO DAO();
}
