package com.example.zece.healthtracker.UI;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.zece.healthtracker.R;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ImageView AppLogo = findViewById(R.id.imageView2);
        ImageButton FilesButton = findViewById(R.id.imageButton3);

        FilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filesbutton();
            }

        });
        ImageButton RecordButton = findViewById(R.id.imageButton4);
        RecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordbutton();
            }

        });


    }

    private void recordbutton() {
        Intent intent_next = new Intent(this, MainActivity.class);
        startActivity(intent_next);
    }

    private void filesbutton() {
        Intent intent_next = new Intent(this, FilesPage.class);
        startActivity(intent_next);
    }

    }