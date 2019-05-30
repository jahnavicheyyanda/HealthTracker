package com.example.zece.healthtracker.UI;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.Waveform.WaveformView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static com.example.zece.healthtracker.UI.PlaybackThread.SAMPLE_RATE;


public class RecordWave extends AppCompatActivity {

    private PlaybackThread mPlaybackThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_wave);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        final WaveformView mPlaybackView = findViewById(R.id.playbackWaveformView);

        short[] samples = null;
        try {
            samples = getAudioSample();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (samples != null) {



            mPlaybackThread = new PlaybackThread(samples, new PlaybackListener() {
                @Override
                public void onProgress(int progress) {
                    mPlaybackView.setMarkerPosition(progress);
                }
                @Override
                public void onCompletion() {
                    mPlaybackView.setMarkerPosition(mPlaybackView.getAudioLength());
                }
            });
            mPlaybackView.setChannels(1);
            mPlaybackView.setSampleRate(SAMPLE_RATE);
            mPlaybackView.setSamples(samples);


            final ImageButton start_button = findViewById(R.id.start_record_button);

            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPlaybackThread.playing()) {
                        mPlaybackThread.startPlayback();

                    }

                }
            });

            ImageButton stop_button = findViewById(R.id.stop_record_button);

            stop_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mPlaybackThread.playing()) {

                        onStop();
                        mPlaybackThread.stopPlayback();

                    }
                }
            });

            ImageButton next_button = findViewById(R.id.next_button_record2);

            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next_button();
                }
            });
        }
     }

    public void next_button() {
        Intent intent_next = new Intent(this, FilesPage.class);
        startActivity(intent_next);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPlaybackThread.stopPlayback();
    }

    private short[] getAudioSample() throws IOException {
        //InputStream is = getResources().openRawResource(R.raw.test2);
        Uri uri =  Uri.parse(Environment.getExternalStorageDirectory() + "/Health_tracker_transfer/Test.wav");
        File file = new File(uri.toString());
        FileInputStream is = new FileInputStream(file);
        byte[] data;
        try {
            data = IOUtils.toByteArray(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

        ShortBuffer sb = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        short[] samples = new short[sb.limit()];
        sb.get(samples);
        return samples;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}




