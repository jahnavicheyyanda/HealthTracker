package com.example.zece.healthtracker.View;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.zece.healthtracker.Database.DAO;
import com.example.zece.healthtracker.Database.MyAppDatabase;
import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.Database.Record;

import java.util.List;



public class PatientViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private static DAO patientDao;
    private MyAppDatabase patientDB;

    //private LiveData member variable to cache the list of words.
    private LiveData<List<Patient>> mAllPatients;
    private LiveData<List<Record>> mAllRecords;

    //a constructor that gets a reference to the DB and gets the list of words from the DB.
    public PatientViewModel(Application application) {
        super(application);
        patientDB = MyAppDatabase.getDatabase(application);
        patientDao = patientDB.mDao();
        mAllPatients = patientDao.getAllPatients();
        mAllRecords = patientDao.getAllRecords();
    }

    //ViewModel is used to hide the implementation from the UI.
    public LiveData<List<Patient>> getAllPatients(){
        return mAllPatients;
    }

    public void insert(Patient patient) {
        new InsertAsyncTask(patientDao).execute(patient);
    }

    public static void update(Patient patient){
        new UpdateAsyncTask(patientDao).execute(patient);
    }

    public void delete(Patient patient){
        new DeleteAsyncTask(patientDao).execute(patient);
    }

    public LiveData<List<Record>> getAllRecords(){
        return mAllRecords;
    }

    public void insert(Record record) {new InsertAsyncTask2(patientDao).execute(record); }

    public static void update(Record record){ new UpdateAsyncTask2(patientDao).execute(record);  }

    public void delete(Record record){
        new DeleteAsyncTask2(patientDao).execute(record);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "ViewModel Destroyed");
    }

    //With AsyncTask, operations will be done in background thread
    @SuppressLint("StaticFieldLeak")
    private class OperationsAsyncTask extends AsyncTask<Patient, Void, Void> {

        DAO mAsyncTaskDao;

        OperationsAsyncTask(DAO dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(DAO mPatientDao) { super(mPatientDao);   }


        @Override
        protected Void doInBackground(Patient... patients) {
            mAsyncTaskDao.insert(patients[0]);
            return null;
        }

    }

    private static class UpdateAsyncTask extends AsyncTask<Patient, Void, Void> {

        DAO mPatientDao;

        UpdateAsyncTask(DAO patientDao) { this.mPatientDao = patientDao ;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            mPatientDao.update(patients[0]);
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteAsyncTask extends OperationsAsyncTask {

        DeleteAsyncTask(DAO patientDao) { super(patientDao);        }

        @Override
        protected Void doInBackground(Patient... patients) {
            mAsyncTaskDao.delete(patients[0]);
            return null;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class OperationsAsyncTask2 extends AsyncTask<Record, Void, Void> {

        DAO mAsyncTaskDao;

        OperationsAsyncTask2(DAO dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Record... records) {
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertAsyncTask2 extends OperationsAsyncTask2 {

        InsertAsyncTask2(DAO mPatientDao) { super(mPatientDao);   }

        @Override
        protected Void doInBackground(Record... records) {
            mAsyncTaskDao.insert(records[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask2 extends AsyncTask<Record, Void, Void> {

        DAO mPatientDao;

        UpdateAsyncTask2(DAO patientDao) { this.mPatientDao = patientDao ;
        }

        @Override
        protected Void doInBackground(Record... records) {
            mPatientDao.update(records[0]);
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteAsyncTask2 extends OperationsAsyncTask2 {

        DeleteAsyncTask2(DAO patientDao) { super(patientDao);        }

        @Override
        protected Void doInBackground(Record... records) {
            mAsyncTaskDao.delete(records[0]);
            return null;
        }
    }
}

