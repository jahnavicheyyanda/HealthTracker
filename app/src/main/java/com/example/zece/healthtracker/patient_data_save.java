package com.example.zece.healthtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;


public class patient_data_save extends Fragment {

    private EditText PatientId, FirstName, LastName, Date;
    private Button OnSave;

    public patient_data_save() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_data_save, container, false);
        FirstName = view.findViewById(R.id.InputName);
        LastName = view.findViewById(R.id.InputLastName);
        PatientId = view.findViewById(R.id.InputPatientId);
        OnSave = view.findViewById(R.id.save_button);

        Calendar calendar = Calendar.getInstance();
        final String currentDate = DateFormat.getDateTimeInstance().format(calendar.getTime());

        TextView date_data_input = view.findViewById(R.id.date_data_input);
        date_data_input.setText(currentDate);

        OnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = FirstName.getText().toString();
                String lastName = LastName.getText().toString();
                int patientId = Integer.parseInt(PatientId.getText().toString());

                Patient patient = new Patient();
                patient.setFirst_name(firstName);
                patient.setLast_name(lastName);
                patient.setPatient_id(patientId);

//                patient_data.myAppDatabase.DAO().addPatient(patient);
                Toast.makeText(getActivity(),"Data saved successfully",Toast.LENGTH_SHORT).show();

                FirstName.setText("");
                LastName.setText("");
                PatientId.setText("");

            }
        });

        /*save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                files_page();
            }
        }); */

        /*public void files_page(){
            Intent intent_save = new Intent(this, files_page.class);
            startActivity(intent_save);
        }*/

        return view;
    }

}
