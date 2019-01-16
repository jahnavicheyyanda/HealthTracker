package com.example.zece.healthtracker.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.zece.healthtracker.Patient;
import com.example.zece.healthtracker.PatientListAdaptor;
import com.example.zece.healthtracker.PatientViewModel;
import com.example.zece.healthtracker.R;

import java.util.List;
import java.util.UUID;

public class files_page extends AppCompatActivity {

    private static final int PATIENT_DATA_ACTIVITY_REQUEST_CODE = 1;
    private String TAG = this.getClass().getSimpleName();
    private PatientViewModel patientViewModel;
    private PatientListAdaptor patientListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        patientListAdaptor =new PatientListAdaptor(this);
        recyclerView.setAdapter(patientListAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(files_page.this, patient_data.class);
                startActivityForResult(intent, PATIENT_DATA_ACTIVITY_REQUEST_CODE);
            }
        });

        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);

        patientViewModel.getAllPatients().observe(this, new Observer<List<Patient>>(){
            @Override
            public void onChanged(@Nullable List<Patient> patients) {
                patientListAdaptor.setPatients(patients);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PATIENT_DATA_ACTIVITY_REQUEST_CODE && resultCode== RESULT_OK) {

            //to insert patient
            final String patient_id = UUID.randomUUID().toString();
            Patient patient = new Patient (patient_id, data.getStringExtra(patient_data.PATIENT_ADDED));
            patientViewModel.insert(patient);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(
                    getApplicationContext(),
                    R.string.not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
