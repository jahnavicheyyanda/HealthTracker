package com.example.zece.healthtracker.UI;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.Waveform.RecorderVisualizerView;

import java.io.File;
import java.io.IOException;

public class RecordedWave extends AppCompatActivity {
    private MediaRecorder myAudioRecorder;
    private boolean isRecording;
    private RecorderVisualizerView visualizerView;
    public static final int REPEAT_INTERVAL = 40;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_wave);

        visualizerView = findViewById(R.id.visualizer);
        Button next_button = findViewById(R.id.next_button);
        Button cancel_button = findViewById(R.id.cancel_button);

        next_button.setOnClickListener(v -> next_button());

        cancel_button.setOnClickListener(v -> cancel_button());

        if (!isRecording) {
            File file = new File(getExternalFilesDir(null), "new");

            myAudioRecorder = new MediaRecorder();
            myAudioRecorder
                    .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            myAudioRecorder
                    .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder
                    .setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(file.getAbsolutePath());
            myAudioRecorder.setAudioSamplingRate(44100);
            //myAudioRecorder.setAudioEncodingBitRate(96000);

            try {
                myAudioRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                myAudioRecorder = null;
                return;
            }

            myAudioRecorder.start();
            isRecording = true;
            handler.post(updateVisualizer);

        } else {
            myAudioRecorder.stop();
            myAudioRecorder.release();
            isRecording = false;
        }

    }


    Runnable updateVisualizer = new Runnable() {
        @Override
        public void run() {
            if (isRecording) // if we are already recording
            {
                // get the current amplitude
                int x = myAudioRecorder.getMaxAmplitude();
                visualizerView.addAmplitude(x); // update the VisualizeView
                visualizerView.invalidate(); // refresh the VisualizerView

                // update in 40 milliseconds
                handler.postDelayed(this, REPEAT_INTERVAL);
            }
        }
    };

    public void next_button() {
        Intent intent_next = new Intent(this, RecordWave.class);
        startActivity(intent_next);
    }

    public void cancel_button(){
        Intent intent_cancel = new Intent(this, RecordingWave.class);
        startActivity(intent_cancel);

    }

}
