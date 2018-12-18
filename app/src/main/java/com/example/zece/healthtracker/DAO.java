package com.example.zece.healthtracker;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface DAO
{
    @Insert
    public void addPatient(Patient patient);

}