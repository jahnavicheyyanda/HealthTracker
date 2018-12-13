package com.example.zece.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.media.audiofx.Visualizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button next_button = findViewById(R.id.next_button_home);

        next_button.setOnClickListener(new View.OnClickListener() {
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
