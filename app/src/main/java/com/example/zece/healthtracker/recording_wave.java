package com.example.zece.healthtracker;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.audiofx.Visualizer;
import android.media.MediaPlayer;
import android.app.Activity;
import android.media.AudioManager;



public class recording_wave extends AppCompatActivity {

    VisualizerView mVisualizerView;
    VisualizerView nVisualizerView;

    private MediaPlayer mMediaPlayer;
    private Visualizer mVisualizer;
    private MediaPlayer nMediaPlayer;
    private Visualizer nVisualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_wave);

 /*     final WaveView firstwaveview =(WaveView)findViewById(R.id.waveView1);
        final WaveView secondwaveview =(WaveView)findViewById(R.id.waveView2); */
        Button start_button = findViewById(R.id.start_button);
        Button stop_button = findViewById(R.id.stop_button);

        stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                recorded_wave();
            }
        });

        mVisualizerView = (VisualizerView) findViewById(R.id.myvisualizerview);
        nVisualizerView = (VisualizerView) findViewById(R.id.myvisualizerview2);
        initAudio();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && mMediaPlayer != null) {
            mVisualizer.release();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        if (isFinishing() && nMediaPlayer != null) {
            nVisualizer.release();
            nMediaPlayer.release();
            nMediaPlayer = null;
        }
    }

    private void initAudio() {
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mMediaPlayer = MediaPlayer.create(this, R.raw.test);
        nMediaPlayer = MediaPlayer.create(this,R.raw.test2);


        setupVisualizerFxAndUI();
        // Make sure the visualizer is enabled only when you actually want to
        // receive data, and
        // when it makes sense to receive data.
            mVisualizer.setEnabled(true);
            nVisualizer.setEnabled(true);
        // When the stream ends, we don't need to collect any more data. We
        // don't do this in
        // setupVisualizerFxAndUI because we likely want to have more,
        // non-Visualizer related code
        // in this callback.
        mMediaPlayer
                .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mVisualizer.setEnabled(false);
                    }
                });
            mMediaPlayer.start();

            nMediaPlayer
                .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        nVisualizer.setEnabled(false);
                    }
                });
            nMediaPlayer.start();

    }


        private void setupVisualizerFxAndUI () {
        // Create the Visualizer object and attach it to our media player.
                mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
                mVisualizer.setCaptureSize(100);
                //Visualizer.getCaptureSizeRange()[1]
                mVisualizer.setDataCaptureListener(
                        new Visualizer.OnDataCaptureListener() {
                            public void onWaveFormDataCapture(Visualizer visualizer,
                                                              byte[] bytes, int samplingRate) {
                                    mVisualizerView.updateVisualizer(bytes);
                            }

                            public void onFftDataCapture(Visualizer visualizer,
                                                         byte[] bytes, int samplingRate) {
                            }
                        }, Visualizer.getMaxCaptureRate() / 24, true, true);

                nVisualizer = new Visualizer(nMediaPlayer.getAudioSessionId());
                nVisualizer.setCaptureSize(100);
                nVisualizer.setDataCaptureListener(
                        new Visualizer.OnDataCaptureListener() {
                            public void onWaveFormDataCapture(Visualizer visualizer,
                                                              byte[] bytes, int samplingRate) {

                                    nVisualizerView.updateVisualizer(bytes);
                                }

                            public void onFftDataCapture(Visualizer visualizer,
                                                         byte[] bytes, int samplingRate) {
                            }
                        }, Visualizer.getMaxCaptureRate() / 24, true, true);

            }

            public void recorded_wave () {
        Intent intent = new Intent(this, recorded_wave.class);
        startActivity(intent);
        mMediaPlayer.stop();
        nMediaPlayer.stop();
    }


}



