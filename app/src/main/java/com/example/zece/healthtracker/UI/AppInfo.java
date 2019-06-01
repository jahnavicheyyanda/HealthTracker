package com.example.zece.healthtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.UI.FilesPage;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

       /* Button buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainpage();
            }

        });*/
    }
/*
    private void mainpage() {
        Intent intent_next = new Intent(this, MainPage.class);
        startActivity(intent_next);

    }*/

}
