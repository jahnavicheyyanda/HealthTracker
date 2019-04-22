package com.example.zece.healthtracker.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "records", foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "patient_id", childColumns = "pid", onDelete = CASCADE))
public class Record {
    @PrimaryKey/*(autoGenerate = true)*/
    @NonNull
    public String rid;

    @ColumnInfo
    public String pid;

    @ColumnInfo
    private String date;

    @NonNull
    public String getRid() {return rid;}
    @NonNull
    public void setRid(String rid) {this.rid = rid;}

    @NonNull
    public String getPid() {return pid;}
    @NonNull
    public void setPid(String pid) {this.pid = pid;}

    public String  getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public Record (String rid, String pid, String date){
        this.rid = rid;
        this.pid = pid;
        this.date=date;
    }

}
