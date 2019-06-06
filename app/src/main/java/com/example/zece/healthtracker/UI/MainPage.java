package com.example.zece.healthtracker.UI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.example.zece.healthtracker.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.widget.ListPopupWindow.MATCH_PARENT;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        checkAndRequestPermissions();

        ImageButton filesButton = findViewById(R.id.record_list_button);

        filesButton.setOnClickListener(v -> filesButton());
        ImageButton recordButton = findViewById(R.id.next_page_button);
        recordButton.setOnClickListener(v -> recordButton());


        ImageButton appInfo = findViewById(R.id.app_info);
        appInfo.setOnClickListener(v -> AppInfoDisplay());
    }

    private void AppInfoDisplay() {
        LayoutInflater inflater = (LayoutInflater) MainPage.this.getSystemService(MainPage.LAYOUT_INFLATER_SERVICE);
        PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.activity_app_info, null), MATCH_PARENT,MATCH_PARENT, true);
        pw.showAtLocation(MainPage.this.findViewById(R.id.app_info), Gravity.CENTER, 0, 0);
        View view= pw.getContentView();
        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> mainPage());
    }

  private void mainPage() {
        Intent intentNext = new Intent(this, MainPage.class);
        startActivity(intentNext);
    }

    private void recordButton() {
        Intent intentNext = new Intent(this, MainActivity.class);
        startActivity(intentNext);
    }

    private void filesButton() {
        Intent intentNext = new Intent(this, FilesPage.class);
        startActivity(intentNext);
    }

    private void checkAndRequestPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE,RECORD_AUDIO };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

  public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    }