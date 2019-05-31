package com.example.zece.healthtracker.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.Database.Record;
import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.View.PatientDataEditViewModel;

import java.io.File;

public class PatientDataEdit extends AppCompatActivity {

    public static final String PATIENT_ID = "patient_id";
    static final String UPDATED_FIRSTNAME = "patient_firstName";
    static final String UPDATED_LASTNAME = "patient_lastName";
    static final String UPDATED_NOTE = "patient_note" ;
    static final String DATE = "record_date";
    private EditText inputNameEdit, inputLastNameEdit, inputPatientNoteEdit;
    private TextView inputDateEdit;
    private Bundle bundle;
    private String patientId;
    private String patientFirstName, patientLastName, recordDate;
    private LiveData<Patient> patient;
    private LiveData<Record> record;
    PatientDataEditViewModel patientDataEditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data_edit);



        inputNameEdit = findViewById(R.id.InputNameEdit);
        inputLastNameEdit = findViewById(R.id.InputLastNameEdit);
        inputPatientNoteEdit = findViewById(R.id.InputPatientNoteEdit);
        inputDateEdit = findViewById(R.id.InputDateEdit);

        bundle = getIntent().getExtras();


        if(bundle != null) {
            patientId = bundle.getString("patient_id");
            patientFirstName = bundle.getString("patient_firstName");
            patientLastName = bundle.getString("patient_lastName");
            recordDate = bundle.getString("record_date");
        }


        patientDataEditViewModel = ViewModelProviders.of(this).get(PatientDataEditViewModel.class);
        patient = patientDataEditViewModel.getFirst_name(patientId);
        patient = patientDataEditViewModel.getLast_name(patientId);
        patient = patientDataEditViewModel.getPatient_note(patientId);
        record = patientDataEditViewModel.getDate(patientId);


        patient.observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(@Nullable Patient patient) {
                inputNameEdit.setText(patient.getFirst_name());
                inputLastNameEdit.setText(patient.getLast_name());
                inputPatientNoteEdit.setText(patient.getNote());

            }
        });
        record.observe(this, new Observer<Record>() {
            @Override
            public void onChanged(@Nullable Record record) {
                inputDateEdit.setText(record.getDate());
            }
        });
    }

    public void updateData(View view){
        String updatedFirstName = inputNameEdit.getText().toString();
        String updatedLastName = inputLastNameEdit.getText().toString();
        String updatedPatientNote = inputPatientNoteEdit.getText().toString();
        String RecordDateEdit = inputDateEdit.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PATIENT_ID, patientId);
        resultIntent.putExtra(UPDATED_FIRSTNAME, updatedFirstName);
        resultIntent.putExtra(UPDATED_LASTNAME, updatedLastName);
        resultIntent.putExtra(UPDATED_NOTE, updatedPatientNote);
        setResult(RESULT_OK, resultIntent);



        File from = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Health_tracker/"
                +patientLastName + "_"
                +patientFirstName+" "+RecordDateEdit+".wav" );
        File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Health_tracker/"
                +updatedLastName +"_"
                +updatedFirstName+" "+RecordDateEdit+".wav");
        from.renameTo(to);

        finish();
    }

    public void cancelUpdate (View view){
        finish();
    }
}
