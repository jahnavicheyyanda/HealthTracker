package com.example.zece.healthtracker.UI;


import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.example.zece.healthtracker.Database.Patient;
import com.example.zece.healthtracker.Database.Record;
import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.View.PatientDataEditViewModel;
import com.example.zece.healthtracker.Waveform.WaveformView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import static com.example.zece.healthtracker.UI.PlaybackThread.SAMPLE_RATE;


public class RecordedWave extends AppCompatActivity {

    private PlaybackThread mPlaybackThread;

    TextView patientFirstNameShow, patientLastNameShow, recordDateShow;

    private Bundle bundle;
    private String patientId;
    private String patientFirstName, patientLastName, recordDate;
    private LiveData<Patient> patient;
    private LiveData<Record> record;
    PatientDataEditViewModel patientDataEditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_wave);


        final WaveformView mPlaybackView = findViewById(R.id.playback_waveform_view);

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
            final ImageButton startButton = findViewById(R.id.start_recorded_button);
            startButton.setOnClickListener(v -> {
                if (!mPlaybackThread.playing()) {
                    mPlaybackThread.startPlayback();
                }
            });

            //to stop playing if it is playing.
            ImageButton stopButton = findViewById(R.id.stop_recorded_button);
            stopButton.setOnClickListener(v -> {
                if(mPlaybackThread.playing()) {
                    onStop();
                    mPlaybackThread.stopPlayback();
                }
            });

            ImageButton nextButton = findViewById(R.id.next_button_recorded);
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

    /*public void zoom_button(){
        Intent intent_zoom = new Intent(this, ZoomRecordWave.class);
        startActivity(intent_zoom);
    }*/

    @Override
    protected void onStop() {
        super.onStop();

        mPlaybackThread.stopPlayback();
    }

    public void updateData(View view){

        patientFirstNameShow = findViewById(R.id.patient_first_name_show);
        patientLastNameShow = findViewById(R.id.patient_last_name_show);
        recordDateShow = findViewById(R.id.record_date_show);

        bundle = getIntent().getExtras();

        if(bundle != null) {
            patientId = bundle.getString("patient_id");
            patientFirstName = bundle.getString("patient_firstName");
            patientLastName = bundle.getString("patient_lastName");
            recordDate = bundle.getString("record_date");
        }
        finish();
    }

    //Audio file will be get from Health_tracker_transfer folder in SD card.
    public short[] getAudioSample() throws IOException {

        patientFirstNameShow = findViewById(R.id.patient_first_name_show);
        patientLastNameShow = findViewById(R.id.patient_last_name_show);
        recordDateShow = findViewById(R.id.record_date_show);

        bundle = getIntent().getExtras();

        if(bundle != null) {
            patientId = bundle.getString("patient_id");
            patientFirstName = bundle.getString("patient_firstName");
            patientLastName = bundle.getString("patient_lastName");
            recordDate = bundle.getString("record_date");
        }

        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Health_tracker/"
                +patientLastName + "_"
                +patientFirstName+" "+recordDate+".wav"
                );
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




