package com.example.zece.healthtracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zece.healthtracker.R;

import static android.widget.ListPopupWindow.MATCH_PARENT;

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


        ImageButton AppInfo = findViewById(R.id.app_info);
        AppInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoDisplay();
            }

        });

    }

    private void AppInfoDisplay() {
        LayoutInflater inflater = (LayoutInflater) MainPage.this.getSystemService(MainPage.LAYOUT_INFLATER_SERVICE);
        PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.activity_app_info, null), MATCH_PARENT,MATCH_PARENT, true);
        pw.showAtLocation(MainPage.this.findViewById(R.id.app_info), Gravity.START, 0, 0);
        View v= pw.getContentView();
        TextView tv=v.findViewById(R.id.info);
        View view= pw.getContentView();
        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainpage();
            }

        });

    }

  private void mainpage() {
        Intent intent_next = new Intent(this, MainPage.class);
        startActivity(intent_next);
    }


    private void recordbutton() {
        Intent intent_next = new Intent(this, MainActivity.class);
        startActivity(intent_next);
    }

    private void filesbutton() {

        //FilesPage filesPage = new FilesPage();
        //filesPage.fabInvisible();

        Intent intent_next = new Intent(this, FilesPage.class);
        startActivity(intent_next);
    }

    }