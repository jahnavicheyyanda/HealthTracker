package com.example.zece.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import androidx.room.Dao;

public class patient_data extends AppCompatActivity {

    private EditText FirstName, LastName;
    private Button OnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);

        Integer PatientId ;
        FirstName = findViewById(R.id.InputName);
        LastName = findViewById(R.id.InputLastName);
        final Button Onsave = findViewById(R.id.save_button);


        Onsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer patient_id;
                String first_name = FirstName.getText().toString();
                String last_name = LastName.getText().toString();

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateTimeInstance().format(calendar.getTime());

                TextView date_data_input = findViewById(R.id.date_data_input);
                date_data_input.setText(currentDate);


                Patient patient = new Patient();
                patient.setFirst_name(first_name);
                patient.setLast_name(last_name);

                }



        });

       /* save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                files_page();
            }
        }); */





    }
    public void files_page(){
        Intent intent_save = new Intent(this, files_page.class);
        startActivity(intent_save);
    }
}
