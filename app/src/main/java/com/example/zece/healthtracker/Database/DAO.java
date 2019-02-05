package com.example.zece.healthtracker.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.zece.healthtracker.Database.Patient;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    void insert (Patient patient);

    @Query("SELECT * FROM patients")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM patients WHERE patient_id=:patientId")
    LiveData<Patient> getLast_name(String patientId);

    @Query("SELECT * FROM patients WHERE patient_id=:patientId")
    LiveData<Patient> getFirst_name(String patientId);

    @Query("SELECT * FROM patients WHERE patient_id=:patientId")
    LiveData<Patient> getPatient_note(String patientId);

    @Update
    void update(Patient patient);

    @Delete
    int delete(Patient patient);


}