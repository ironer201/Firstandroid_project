package com.app.testlogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class attenden_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendenlist);
        TableLayout tableLayout = findViewById(R.id.staticTable);

        // Retrieve attendance data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("student_data", MODE_PRIVATE);
        String jsonData = sharedPreferences.getString("attendance_records", null);

        if (jsonData != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Map<String, String>>>() {}.getType();
            ArrayList<Map<String, String>> attendanceRecords = gson.fromJson(jsonData, type);

            int paddingInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

            for (Map<String, String> record : attendanceRecords) {
                // Create a new TableRow
                TableRow row = new TableRow(this);

                // Date TextView
                TextView dateView = new TextView(this);
                dateView.setText(record.get("date"));
                dateView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

                // Course TextView
                TextView courseView = new TextView(this);
                courseView.setText(record.get("course"));
                courseView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

                // ID TextView
                TextView idView = new TextView(this);
                idView.setText(record.get("id"));
                idView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

                // Name TextView
                TextView nameView = new TextView(this);
                nameView.setText(record.get("name"));
                nameView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

                // Status TextView
                TextView statusView = new TextView(this);
                statusView.setText(record.get("status"));
                statusView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

                // Add views to the row
                row.addView(dateView);
                row.addView(courseView);
                row.addView(idView);
                row.addView(nameView);
                row.addView(statusView);

                // Add row to the table
                tableLayout.addView(row);
            }
        }
    }
}
