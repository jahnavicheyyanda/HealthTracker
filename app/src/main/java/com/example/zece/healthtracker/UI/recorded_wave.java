package com.example.zece.healthtracker.UI;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.Waveform.RecorderVisualizerView;

import java.io.File;
import java.io.IOException;

public class recorded_wave extends AppCompatActivity {
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
        TextView recorded_text = findViewById(R.id.textView2);

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

        if (!isRecording) {
            File f = new File(getExternalFilesDir(null), "new.wav");

            myAudioRecorder = new MediaRecorder();
            myAudioRecorder
                    .setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            myAudioRecorder
                    .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder
                    .setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(f.getAbsolutePath());
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

       /* public void saved_wave() {
            Intent intent_save = new Intent(this, saved_wave.class);
            startActivity(intent_save);
        }*/

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
        Intent intent_next = new Intent(this, files_page.class);
        startActivity(intent_next);
    }

    public void cancel_button(){
        Intent intent_cancel = new Intent(this, recording_wave.class);
        startActivity(intent_cancel);

    }

}
