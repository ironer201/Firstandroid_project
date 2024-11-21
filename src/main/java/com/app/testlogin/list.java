package com.app.testlogin;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class list extends AppCompatActivity {

    private TableLayout studentTable;
    private SharedPreferences sharedPreferences;
    private static final String STUDENT_LIST_KEY = "student_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        sharedPreferences = getSharedPreferences("student_data", MODE_PRIVATE);
        studentTable = findViewById(R.id.studentTab);

        Button addRowButton = findViewById(R.id.addRow);
        addRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewRow();
            }
        });

        Button updateButton = findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(list.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        // Load saved data on app startup
        loadSavedData();
        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Close the current activity
    }
    //add row
    private void addNewRow() {
        TableRow newRow = new TableRow(this);

        // Create EditText fields for ID and Name
        EditText idEditText = new EditText(this);
        idEditText.setHint("Enter ID");
        idEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        idEditText.setTextColor(Color.BLACK);// Equal weight for ID and Name

        EditText nameEditText = new EditText(this);
        nameEditText.setHint("Enter Name");
        nameEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        nameEditText.setTextColor(Color.BLACK);

        newRow.addView(idEditText);
        newRow.addView(nameEditText);

        studentTable.addView(newRow);
    }

    //save data
    private void saveData() {
        ArrayList<Map<String, String>> studentList = new ArrayList<>();
        int rowCount = studentTable.getChildCount();

        for (int i = 0; i < rowCount; i++) {
            TableRow row = (TableRow) studentTable.getChildAt(i);
            EditText idEditText = (EditText) row.getChildAt(0);
            EditText nameEditText = (EditText) row.getChildAt(1);

            String id = idEditText.getText().toString();
            String name = nameEditText.getText().toString();

            if (!id.isEmpty() && !name.isEmpty()) {
                Map<String, String> studentData = new HashMap<>();
                studentData.put("id", id);
                studentData.put("name", name);
                studentList.add(studentData);
            } else {
                Toast.makeText(this, "Please enter both ID and Name", Toast.LENGTH_SHORT).show();
            }
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(studentList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STUDENT_LIST_KEY, jsonString);
        editor.apply();
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }
    private void loadSavedData() {
        studentTable.removeAllViews();
        String jsonString = sharedPreferences.getString(STUDENT_LIST_KEY, null);

        if (jsonString != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Map<String, String>>>() {}.getType();
            ArrayList<Map<String, String>> studentList = gson.fromJson(jsonString, type);
            int paddingInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            for (Map<String, String> student : studentList) {
                TableRow newRow = new TableRow(this);

                EditText idEditText = new EditText(this);
                idEditText.setText(student.get("id"));
                //idEditText.setEnabled(false);
                idEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                idEditText.setTextColor(Color.BLACK);
                idEditText.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

                EditText nameEditText = new EditText(this);
                nameEditText.setText(student.get("name"));
                nameEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                nameEditText.setTextColor(Color.BLACK);

                // Add the delete button only after loading data
                Button deleteButton = new Button(this);
                deleteButton.setText("-");
                deleteButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove the current row from the TableLayout
                        studentTable.removeView(newRow);

                        // Remove the corresponding data from SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(idEditText.getText().toString());
                        editor.apply();
                    }
                });

                newRow.addView(idEditText);
                newRow.addView(nameEditText);

                studentTable.addView(newRow);
            }
        }
    }
}