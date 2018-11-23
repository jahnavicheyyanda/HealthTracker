package com.example.zece.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class recorded_wave extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_wave);

        Button next_button = findViewById(R.id.next_button);
        Button cancel_button = findViewById(R.id.cancel_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_button();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_button();
            }
        });
    }

    public void next_button() {
        Intent intent_next = new Intent(this, patient_data.class);
                startActivity(intent_next);
    }

    public void cancel_button(){
        Intent intent_cancel = new Intent(this, recording_wave.class);
                startActivity(intent_cancel);

    }
}
