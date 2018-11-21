package com.example.zece.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class connection_check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_check);

        Button next_button2 = findViewById(R.id.next_button2);

        next_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recording_wave();
            }
        });
    }
    public void recording_wave() {
        Intent intent = new Intent(this, recording_wave.class);
        startActivity(intent);
    }
}
