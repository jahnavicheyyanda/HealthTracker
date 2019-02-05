package com.example.zece.healthtracker.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "patients")
public class Patient {
    @PrimaryKey/*(autoGenerate = true)*/
    @NonNull
    private String patient_id;

    @ColumnInfo(name = "first_name")
    //@NonNull
    private String first_name;

    @ColumnInfo(name = "last_name")
    @NonNull
    private String last_name;

    @ColumnInfo(name = "note")
    private  String note;

    @ColumnInfo
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NonNull
    public String getPatient_id() {
        return patient_id;
    }
    @NonNull
    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    @NonNull
    public String getLast_name() {
        return this.last_name;
    }
    @NonNull
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNote() { return this.note; }

    public void setNote(String note) { this.note = note; }

    public Patient(String patient_id, String last_name, String first_name, String note/*, Date date*/) {
        this.patient_id=patient_id;
        this.last_name=last_name;
        this.first_name=first_name;
        this.note=note;
       // this.date = date;
    }
}
