package com.example.zece.healthtracker.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    void insert (Patient patient);

    @Update
    void update(Patient patient);

    @Delete
    int delete(Patient patient);

    @Query("SELECT * FROM patients")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM patients WHERE patient_id=:patientId")
    LiveData<Patient> getLast_name(String patientId);

    @Query("SELECT * FROM patients WHERE patient_id=:patientId")
    LiveData<Patient> getFirst_name(String patientId);

    @Query("SELECT * FROM patients WHERE patient_id=:patientId")
    LiveData<Patient> getPatient_note(String patientId);


    @Insert
    void insert(Record... record);

    @Update
    void update(Record... record);

    @Delete
    void delete(Record... record);

    @Query("SELECT * FROM records")
    LiveData<List<Record>> getAllRecords();

    @Query("SELECT * FROM records WHERE pid=:pid")
    LiveData<Record> getDate(String pid);

    //@Query("SELECT * FROM records WHERE rid=:rid")
    //LiveData<Record> getDate(String rid);


}