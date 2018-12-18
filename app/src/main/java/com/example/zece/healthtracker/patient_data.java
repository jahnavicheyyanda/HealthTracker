package com.example.zece.healthtracker;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class patient_data extends AppCompatActivity {

    public static FragmentManager fragmentManager;
//    public static MyAppDatabase myAppDatabase;


         @Override
         protected void onCreate (Bundle savedInstanceState){
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_patient_data);

             fragmentManager = getSupportFragmentManager();
//        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"carotiddb").allowMainThreadQueries().build();

             //Add patient_data_save fragment to the patient_data activity
             if (findViewById(R.id.fragment_patient_data_save) != null) {
                 if (savedInstanceState != null) {
                     return;
                 }

                 fragmentManager.beginTransaction().add(R.id.fragment_patient_data_save, new patient_data_save()).commit();
             }

         }


}
