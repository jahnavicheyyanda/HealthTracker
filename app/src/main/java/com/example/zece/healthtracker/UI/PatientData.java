package com.example.zece.healthtracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zece.healthtracker.R;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;

public class PatientData extends AppCompatActivity {

    public static final String NEW_PATIENTNOTE = "new_patient_note";
    public static final String NEW_FIRSTNAME = "new_patient_firstName";
    public static final String NEW_LASTNAME = "new_patient_lastName";
    public static final String NEW_RECORDDATE = "new_record_date";
    public EditText inputName, inputLastName, inputPatientNote;

    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateTimeInstance().format(calendar.getTime());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);

        inputName = findViewById(R.id.input_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputPatientNote = findViewById(R.id.input_patient_note);

        final TextView dateDataInput = findViewById(R.id.date_data_input);
        dateDataInput.setText(currentDate);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent(PatientData.this, PatientDataEdit.class);

            if (TextUtils.isEmpty(inputLastName.getText())) {
                setResult(RESULT_CANCELED, resultIntent);

            } else if (TextUtils.isEmpty(inputName.getText())) {
                setResult(RESULT_CANCELED, resultIntent);

            } else if (TextUtils.isEmpty(inputPatientNote.getText())) {
                setResult(RESULT_CANCELED, resultIntent);

            } else {
                String patientLastName = inputLastName.getText().toString();
                String patientFirstName = inputName.getText().toString();
                String patientNote = inputPatientNote.getText().toString();
                String recordDate = dateDataInput.getText().toString();
                resultIntent.putExtra(NEW_LASTNAME, patientLastName);
                resultIntent.putExtra(NEW_FIRSTNAME, patientFirstName);
                resultIntent.putExtra(NEW_PATIENTNOTE, patientNote);
                resultIntent.putExtra(NEW_RECORDDATE, recordDate);
                setResult(RESULT_OK, resultIntent);


                File from = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/Health_tracker_transfer/Test.wav");
                File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/Health_tracker/" /*+recordDate*/
                        + patientLastName + "_"
                        + patientFirstName +" " + currentDate + ".wav");
                from.renameTo(to);

            }

            finish();

        });
    }
}