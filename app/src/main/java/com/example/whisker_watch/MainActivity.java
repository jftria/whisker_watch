package com.example.whisker_watch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Archive button and set click listener
        View btnArchives = findViewById(R.id.btnArchives);
        if (btnArchives != null) {
            btnArchives.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ArchiveActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Find the Submit Report button and set click listener
        View btnSubmitReport = findViewById(R.id.btnSubmitReport);
        if (btnSubmitReport != null) {
            btnSubmitReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}