package com.example.zece.healthtracker.View;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.zece.healthtracker.Database.DAO;
import com.example.zece.healthtracker.Database.MyAppDatabase;
import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.Database.Record;

public class PatientDataEditViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private DAO mDao;
    private MyAppDatabase db;

    public PatientDataEditViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = MyAppDatabase.getDatabase(application);
        mDao = db.mDao();
    }

    public LiveData<Patient> getLast_name(String patientId) { return mDao.getLast_name(patientId); }

    public LiveData<Patient> getFirst_name(String patientId) { return mDao.getFirst_name(patientId); }

    public LiveData<Patient> getPatient_note(String patientId) { return mDao.getPatient_note(patientId);  }

    public LiveData<Patient> getRecord_date(String patientId) { return mDao.getRecord_date(patientId); }

    public LiveData<Record> getDate(String patientId) { return mDao.getDate(patientId); }


}
