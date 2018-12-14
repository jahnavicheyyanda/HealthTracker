package com.example.zece.healthtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.audiofx.Visualizer;
import com.john.waveview.WaveView;

public class recording_wave extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_wave);

        final WaveView firstwaveview =(WaveView)findViewById(R.id.waveView1);
        final WaveView secondwaveview =(WaveView)findViewById(R.id.waveView2);
        Button start_button = findViewById(R.id.start_button);
        Button stop_button = findViewById(R.id.stop_button);

        stop_button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                recorded_wave();
            }
        });
    }
        public void recorded_wave(){
            Intent intent = new Intent(this, recorded_wave.class);
            startActivity(intent);
        }


}



