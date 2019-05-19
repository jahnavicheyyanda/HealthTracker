
package com.example.zece.healthtracker.UI;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.audiofx.Visualizer;
import android.media.MediaPlayer;
import android.widget.TextView;

import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.Waveform.RecorderVisualizerView;
import com.example.zece.healthtracker.Waveform.VisualizerView;



public class RecordingWave extends AppCompatActivity {

    VisualizerView mVisualizerView;
    public MediaPlayer mMediaPlayer;
    public Visualizer mVisualizer;
    //public ConnectedThread mConnectedThread;
    public BluetoothSocket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_wave);

 //    final WaveView firstwaveview =(WaveView)findViewById(R.id.waveView1);
 //       final WaveView secondwaveview =(WaveView)findViewById(R.id.waveView2);
        Button stop_button = findViewById(R.id.stop_button);
        //TextView recording_text = findViewById(R.id.textView);


        stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMediaPlayer.pause();
            }
        });

        mVisualizerView = findViewById(R.id.myvisualizerview);
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
    }

    private void initAudio() {

          mMediaPlayer = MediaPlayer.create(RecordingWave.this, Uri.parse(Environment.getExternalStorageDirectory()
                                              + "/Health_tracker_transfer/Test.wav"));

        setupVisualizerFxAndUI();
        // Make sure the visualizer is enabled only when you actually want to
        // receive data, and
        // when it makes sense to receive data.
        mVisualizer.setEnabled(true);
        // When the stream ends, we don't need to collect any more data. We
        // don't do this in
        // setupVisualizerFxAndUI because we likely want to have more,
        // non-Visualizer related code
        // in this callback.
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mVisualizer.setEnabled(false);
                    }
                });

        Button start_button = findViewById(R.id.start_button);

        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            mMediaPlayer.start();

            }
        });

      /*  Button recording_start_pi = findViewById(R.id.recording_start_button);
        recording_start_pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConnectedThread = new ConnectedThread(socket);
                mConnectedThread.start();
            }
        });*/




        Button next_button_record = findViewById(R.id.next_button_record);

        next_button_record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mMediaPlayer.stop();
                recorded_wave();

                // Stopping the file will completely stop the file and you won’t be able to run it again.
                // when we hit the Play button again to be able to play it again (because the MediaPlayer object will be cleared after stop().
                // There will be no file present in the object anymore.)
                mMediaPlayer = MediaPlayer.create(RecordingWave.this,  Uri.parse(Environment.getExternalStorageDirectory()
                                                    + "/Health_tracker_transfer/Test.wav"));

            }
        });

    }




    public void recorded_wave () {
        Intent intent = new Intent(this, RecordedWave.class);
        startActivity(intent);

    }


    private void setupVisualizerFxAndUI () {
        // Create the Visualizer object and attach it to our media player.
                mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
                mVisualizer.setCaptureSize(10);
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
                        }, Visualizer.getMaxCaptureRate() / 6, true, true);



            }



}

