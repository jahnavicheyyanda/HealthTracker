package com.example.zece.healthtracker.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.Database.Record;
import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.View.PatientListAdapter;
import com.example.zece.healthtracker.View.PatientViewModel;

import java.io.File;
import java.util.UUID;

public class FilesPage extends AppCompatActivity implements PatientListAdapter.OnDeleteClickListener {

    public static final int UPDATE_PATIENT_DATA_ACTIVITY_REQUEST_CODE = 2;
    private static final int PATIENT_DATA_ACTIVITY_REQUEST_CODE = 1;
    private PatientViewModel patientViewModel;
    private PatientListAdapter patientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //connecting recyclerView to a layout manager, and attach an adapter for the data to be displayed
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        patientListAdapter = new PatientListAdapter(this, this, this);
        recyclerView.setAdapter(patientListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);

        fabVisibility();

        fab.setOnClickListener(view -> {

            Intent intent = new Intent(FilesPage.this, PatientData.class);
            startActivityForResult(intent, PATIENT_DATA_ACTIVITY_REQUEST_CODE);

        });

        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);

        patientViewModel.getAllPatients().observe(this, patients -> patientListAdapter.setPatients(patients));

        patientViewModel.getAllRecords().observe(this, records -> patientListAdapter.setRecords(records));

    }

    // In case of not having a new file to save, fab button will be invisible.
    public void fabVisibility() {
        FloatingActionButton fab = findViewById(R.id.fab);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Health_tracker_transfer/Test.wav");
        File file = new File(uri.toString());

        if (!file.exists()) {
            fab.hide();
        } else {
            fab.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PATIENT_DATA_ACTIVITY_REQUEST_CODE && resultCode== RESULT_OK) {

            //to insert patient

            final String patient_id = UUID.randomUUID().toString();
            Patient patient = new Patient ( patient_id,
                                            data.getStringExtra(PatientData.NEW_LASTNAME),
                                            data.getStringExtra(PatientData.NEW_FIRSTNAME),
                                            data.getStringExtra(PatientData.NEW_PATIENTNOTE));
            patientViewModel.insert(patient);

            final String rid = UUID.randomUUID().toString();
            Record record = new Record (rid,
                                        patient_id,
                                        data.getStringExtra(PatientData.NEW_RECORDDATE));
            patientViewModel.insert(record);

            fabVisibility();

            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == UPDATE_PATIENT_DATA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            //code update the patient data
            Patient patient = new Patient(
                    data.getStringExtra(PatientDataEdit.PATIENT_ID),
                    data.getStringExtra(PatientDataEdit.UPDATED_LASTNAME),
                    data.getStringExtra(PatientDataEdit.UPDATED_FIRSTNAME),
                    data.getStringExtra(PatientDataEdit.UPDATED_NOTE));
            PatientViewModel.update(patient);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.updated  ,
                    Toast.LENGTH_LONG).show();

        } else{
            Toast.makeText(
                    getApplicationContext(),
                    R.string.not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    //For delete operation

    @Override
    public void onDeleteClickListener(Patient mPatient) {

        patientViewModel.delete(mPatient);
    }
}
