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
    private static DAO patientDao;
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

    public static void update(Patient patient){
        new UpdateAsyncTask(patientDao).execute(patient);
    }

    public void delete(Patient patient){
        new DeleteAsyncTask(patientDao).execute(patient);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "ViewModel Destroyed");
    }

    //With AsyncTask, operations will be done in background thread

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

    private class InsertAsyncTask extends OperationsAsyncTask {

        public InsertAsyncTask(DAO mPatientDao) { super(mPatientDao);   }

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

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(DAO patientDao) { super(patientDao);        }

        @Override
        protected Void doInBackground(Patient... patients) {
            mAsyncTaskDao.delete(patients[0]);
            return null;
        }
    }
}

