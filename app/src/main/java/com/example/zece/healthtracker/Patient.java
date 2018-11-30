package com.example.zece.healthtracker;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Patient
{
    @PrimaryKey(autoGenerate = true)
    private int patient_id;

    private String first_name;

    private String last_name;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
