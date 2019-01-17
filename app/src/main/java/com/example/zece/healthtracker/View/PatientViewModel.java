package com.example.zece.healthtracker.View;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.zece.healthtracker.Database.DAO;
import com.example.zece.healthtracker.Database.MyAppDatabase;
import com.example.zece.healthtracker.Database.Patient;

import java.util.List;



public class PatientViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private DAO patientDao;
    public MyAppDatabase patientDB;
    private LiveData<List<Patient>> mAllPatients;

    public PatientViewModel(Application application) {
        super(application);

        patientDB = MyAppDatabase.getDatabase(application);
        patientDao = patientDB.mDao();
        mAllPatients = patientDao.getAllPatients();

    }

    public void insert(Patient patient) {
        new InsertAsyncTask(patientDao).execute(patient);
    }

    public LiveData<List<Patient>> getAllPatients(){
        return mAllPatients;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "ViewModel Destroyed");
    }

    //With AsyncTask, operations will be done in background thread

    private class InsertAsyncTask extends AsyncTask<Patient, Void, Void> {

        DAO mPatientDao;

        public InsertAsyncTask(DAO mPatientDao) {
            this.mPatientDao = mPatientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            mPatientDao.insert(patients[0]);
            return null;
        }
    }
}

