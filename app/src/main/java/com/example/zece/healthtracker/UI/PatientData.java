package com.example.zece.healthtracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zece.healthtracker.R;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;

public class PatientData extends AppCompatActivity {

    public static final String NEW_PATIENTNOTE = "new_patient_note";
    public static final String NEW_FIRSTNAME = "new_patient_firstName";
    public static final String NEW_LASTNAME = "new_patient_lastName";
    public static final String NEW_RECORDDATE = "new_record_date";
    public EditText InputName, InputLastName, InputPatientNote;


    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateTimeInstance().format(calendar.getTime());



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);

        InputName = findViewById(R.id.InputName);
        InputLastName = findViewById(R.id.InputLastName);
        InputPatientNote = findViewById(R.id.InputPatientNote);

        final TextView date_data_input = findViewById(R.id.date_data_input);
        date_data_input.setText(currentDate);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(PatientData.this, PatientDataEdit.class);

                if (TextUtils.isEmpty(InputLastName.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);

                } else if (TextUtils.isEmpty(InputName.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);

                } else if (TextUtils.isEmpty(InputPatientNote.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);

                } else {
                    String patientLastName = InputLastName.getText().toString();
                    String patientFirstName = InputName.getText().toString();
                    String patientNote = InputPatientNote.getText().toString();
                    String recordDate = date_data_input.getText().toString();
                    resultIntent.putExtra(NEW_LASTNAME, patientLastName);
                    resultIntent.putExtra(NEW_FIRSTNAME, patientFirstName);
                    resultIntent.putExtra(NEW_PATIENTNOTE, patientNote);
                    resultIntent.putExtra(NEW_RECORDDATE, recordDate);
                    setResult(RESULT_OK, resultIntent);




                    File from = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Health_tracker_transfer/Test.mp3");
                    File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Health_tracker/" /*+recordDate*/
                            + " " + patientLastName
                            + " " + patientFirstName + ".mp3");
                    from.renameTo(to);


                }

                finish();


            }


        });

    }


    /*String patientFirstName = InputName.getText().toString();

        PatientData patientData = new PatientData();
        String inputName = patientData.getInputName();
        Toast.makeText(getApplicationContext(), inputName, Toast.LENGTH_LONG).show();

    public String getInputName(){
        return patientFirstName;
        }*/






}