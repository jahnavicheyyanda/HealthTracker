package com.example.zece.healthtracker.UI;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.Waveform.WaveformView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import static com.example.zece.healthtracker.UI.PlaybackThread.SAMPLE_RATE;


public class RecordWave extends AppCompatActivity {

    private PlaybackThread mPlaybackThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_wave);

        //final ImageButton zoomImg = findViewById(R.id.zoom_img);

        //zoomImg.setOnClickListener(v -> zoom_button());


        final WaveformView mPlaybackView = findViewById(R.id.playback_waveform_view);
        //Bitmap bitmap = loadBitmapFromView(findViewById(R.id.playbackWaveformView),350,450);

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
            mPlaybackView.setChannels(2);
            mPlaybackView.setSampleRate(SAMPLE_RATE);
            mPlaybackView.setSamples(samples);

            //to start playing if it is not playing.
            final ImageButton startButton = findViewById(R.id.start_record_button);
            startButton.setOnClickListener(v -> {
                if (!mPlaybackThread.playing()) {
                    mPlaybackThread.startPlayback();
                }
            });

            //to stop playing if it is playing.
            ImageButton stopButton = findViewById(R.id.stop_record_button);
            stopButton.setOnClickListener(v -> {
                if(mPlaybackThread.playing()) {
                    onStop();
                    mPlaybackThread.stopPlayback();
                }
            });

            ImageButton nextButton = findViewById(R.id.next_button_record2);
            nextButton.setOnClickListener(v -> nextButton());
        }

        final ZoomControls simpleZoomControl = findViewById(R.id.simple_zoom_control);


        // perform  setOnZoomInClickListener event on ZoomControls
        simpleZoomControl.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleZoomControl.setIsZoomOutEnabled(true);
                // add zoom in code here
                // calculate current scale x and y value of WaveformView
                float x = mPlaybackView.getScaleX();
                float y = mPlaybackView.getScaleY();
                final float MAX_ZOOM = 5f;
                // set increased value of scale x and y to perform zoom in functionality
                mPlaybackView.setScaleX((float) (x + 1));
                mPlaybackView.setScaleY((float) (y + 1));
                // display a toast to show Zoom In Message on Screen
                //Toast.makeText(getApplicationContext(),"Zoom In",Toast.LENGTH_SHORT).show();
                // hide the ZoomControls from the screen
                if(mPlaybackView.getScaleX() < MAX_ZOOM){
                    simpleZoomControl.setIsZoomInEnabled(true);
                }
                else if (mPlaybackView.getScaleX() >= (MAX_ZOOM) ){
                        simpleZoomControl.setIsZoomInEnabled(false);

            }
            }
        });

        // perform  setOnZoomOutClickListener event on ZoomControls
        simpleZoomControl.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleZoomControl.setIsZoomInEnabled(true);
                // add zoom out code here
                // calculate current scale x and y value of WaveformView
                float x = mPlaybackView.getScaleX();
                float y = mPlaybackView.getScaleY();
                final float MIN_ZOOM = 1f;
                // set decreased value of scale x and y to perform zoom out functionality
                mPlaybackView.setScaleX((float) (x - 1));
                mPlaybackView.setScaleY((float) (y - 1));
                // display a toast to show Zoom Out Message on Screen
                //Toast.makeText(getApplicationContext(),"Zoom Out",Toast.LENGTH_SHORT).show();
                if(mPlaybackView.getScaleX() <= (MIN_ZOOM)){
                    simpleZoomControl.setIsZoomOutEnabled(false);
                }
            }
        });

     }


    /*public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }*/

    public void nextButton() {
        Intent intent_next = new Intent(this, FilesPage.class);
        startActivity(intent_next);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mPlaybackThread.stopPlayback();
    }

    //Audio file will be get from Health_tracker_transfer folder in SD card.
    private short[] getAudioSample() throws IOException {

        Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Health_tracker_transfer/Test.wav");
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

}




