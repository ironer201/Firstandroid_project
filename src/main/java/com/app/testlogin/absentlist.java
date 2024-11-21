package com.app.testlogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class absentlist extends AppCompatActivity {
    private TableLayout absentTable;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absentlist);

        absentTable = findViewById(R.id.absentlistTable);
        sharedPreferences = getSharedPreferences("absent_data", MODE_PRIVATE);

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Close the current activity

        // Load saved data into the table
        loadSavedData();
    }

    private void loadSavedData() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        int rowCount = 0;

        while (true) {
            String courseCodeKey = "course_code_" + rowCount;
            String idKey = "id_" + rowCount;
            String reasonKey = "reason_" + rowCount;

            if (allEntries.containsKey(courseCodeKey)) {
                String courseCode = (String) allEntries.get(courseCodeKey);
                String id = (String) allEntries.get(idKey);
                String reason = (String) allEntries.get(reasonKey);

                TableRow row = new TableRow(this);

                TextView courseCodeField = new TextView(this);
                courseCodeField.setTextColor(getResources().getColor(android.R.color.black));
                courseCodeField.setText(courseCode);
                courseCodeField.setTextSize(20);
                courseCodeField.setBackgroundResource(R.drawable.cell_border);
                row.addView(courseCodeField);

                TextView idField = new TextView(this);
                idField.setText(id);
                idField.setTextColor(getResources().getColor(android.R.color.black));
                idField.setTextSize(20);
                idField.setBackgroundResource(R.drawable.cell_border);
                row.addView(idField);

                TextView reasonField = new TextView(this);
                reasonField.setText(reason);
                reasonField.setTextColor(getResources().getColor(android.R.color.black));
                reasonField.setTextSize(20);
                reasonField.setBackgroundResource(R.drawable.cell_border);
                row.addView(reasonField);

                absentTable.addView(row);
                rowCount++;
            } else {
                break;
            }
        }
    }
}