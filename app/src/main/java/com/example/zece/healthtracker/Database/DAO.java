package com.example.zece.healthtracker.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.zece.healthtracker.Database.Patient;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    void insert (Patient patient);

    @Query("SELECT * FROM patients")
    LiveData<List<Patient>> getAllPatients();

}