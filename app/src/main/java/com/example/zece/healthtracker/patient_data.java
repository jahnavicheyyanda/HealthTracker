package com.example.zece.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class patient_data extends AppCompatActivity {

    public static final String PATIENT_ADDED = "new_patient";
    private EditText InputName, InputLastName;


         @Override
         protected void onCreate (Bundle savedInstanceState){
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_patient_data);
             InputName = findViewById(R.id.InputName);
             InputLastName = findViewById(R.id.InputLastName);

             Button saveButton = findViewById(R.id.save_button);
             saveButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent resultIntent = new Intent();

                     if (TextUtils.isEmpty(InputLastName.getText())) {
                         setResult(RESULT_CANCELED, resultIntent);
                     } else {
                         String patient = InputLastName.getText().toString();
                         resultIntent.putExtra(PATIENT_ADDED, patient);
                         setResult(RESULT_OK, resultIntent);
                     }

                     finish();
                 }
             });

         }


}
