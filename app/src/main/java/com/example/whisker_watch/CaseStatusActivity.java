package com.example.whisker_watch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CaseStatusActivity extends AppCompatActivity {

    private static final ArrayList<Report> reportList = new ArrayList<>();
    private LinearLayout containerCases;
    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_status);

        containerCases = findViewById(R.id.containerCases);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        setupNavigation();

        // Check if a new report was sent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("REPORT_DATA")) {
            Report newReport = (Report) intent.getSerializableExtra("REPORT_DATA");
            if (newReport != null) {
                reportList.add(0, newReport); // Add to top of list
            }
        }

        displayReports();
    }

    private void setupNavigation() {
        View navHome = findViewById(R.id.navHome);
        View navReport = findViewById(R.id.navReport);
        View navCenter = findViewById(R.id.navCenter);
        View navArchives = findViewById(R.id.navArchives);
        View navHelpCenters = findViewById(R.id.navHelpCenters);

        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        if (navReport != null) {
            navReport.setOnClickListener(v -> {
                startActivity(new Intent(this, ReportActivity.class));
            });
        }

        if (navCenter != null) {
            navCenter.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        if (navArchives != null) {
            navArchives.setOnClickListener(v -> {
                startActivity(new Intent(this, ArchiveActivity.class));
            });
        }

        if (navHelpCenters != null) {
            navHelpCenters.setOnClickListener(v -> {
                startActivity(new Intent(this, HelpCentersActivity.class));
            });
        }
    }

    private void displayReports() {
        // Clear container to avoid duplicates
        containerCases.removeAllViews();

        // Empty state
        if (reportList.isEmpty()) {
            if (tvEmptyState != null) tvEmptyState.setVisibility(View.VISIBLE);
            return;
        } else {
            if (tvEmptyState != null) tvEmptyState.setVisibility(View.GONE);
        }

        // Inflate one card per report, in order (newest first since we added to index 0)
        for (Report report : reportList) {
            View cardView = LayoutInflater.from(this)
                    .inflate(R.layout.case_item_layout, containerCases, false);

            TextView tvDesc = cardView.findViewById(R.id.tvCaseDescription);
            TextView tvLoc = cardView.findViewById(R.id.tvCaseLocation);
            ImageView ivPhoto = cardView.findViewById(R.id.ivCasePhoto);

            if (tvDesc != null) tvDesc.setText(report.getDescription());
            if (tvLoc != null) tvLoc.setText(report.getLocation());

            // Display the user's image, or fall back to placeholder
            if (ivPhoto != null) {
                if (report.hasImage()) {
                    try {
                        ivPhoto.setImageURI(Uri.parse(report.getImagePath()));
                    } catch (Exception e) {
                        ivPhoto.setImageResource(R.drawable.placeholder_animal);
                    }
                } else {
                    ivPhoto.setImageResource(R.drawable.placeholder_animal);
                }
            }

            containerCases.addView(cardView); // append (no index needed — list is already ordered)
        }
    }
}