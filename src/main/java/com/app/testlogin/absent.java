package com.app.testlogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class absent extends AppCompatActivity {

    private TableLayout courseTable;
    private SharedPreferences sharedPreferences;
    //date
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    //course
    //spinner
    private String selectedCourseCode = "";
    String[] courses = {"CSE-2213", "CSE-2215", "CSE-2211", "MATH-2247", "EEE-2266"};
    Spinner courseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("absent_data", MODE_PRIVATE);

        // Initialize date picker
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        // Initialize TableLayout and Add Row Button
        courseTable = findViewById(R.id.courseTable);
        Button addRowButton = findViewById(R.id.addRowButton);
        Button submitButton = findViewById(R.id.submit);

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Close the current activity

        // Set OnClickListener for the Add Row Button
        addRowButton.setOnClickListener(v -> addNewRow());

        // Set OnClickListener for the Submit Button (saves data)
        submitButton.setOnClickListener(v -> {
            saveTableData();
            Toast.makeText(absent.this, "Data saved", Toast.LENGTH_SHORT).show();
            // Start AbsentListActivity
            Intent intent = new Intent(absent.this, absentlist.class);
            startActivity(intent);
        });
        // Set up course spinner
        courseSpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);

        // Track spinner selection
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update the selectedCourseCode with the current spinner selection
                selectedCourseCode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCourseCode = "";
            }
        });

        // Set OnClickListener for the Add Row Button
        addRowButton.setOnClickListener(v -> addNewRow());

        // Set OnClickListener for the Submit Button (saves data)
        submitButton.setOnClickListener(v -> {
            saveTableData();
            Toast.makeText(absent.this, "Data saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(absent.this, absentlist.class));
        });

        // Load saved data on app start
        loadSavedData();
    }//On create end

    //Calender

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month + 1, year);
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return month + "/" + day + "/" + year;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
// Calender is up

    //add row
    private void addNewRow() {
        // Create a new TableRow
        TableRow newRow = new TableRow(this);

        // Create EditText for the course code field and pre-fill it with selectedCourseCode
        EditText courseCode = new EditText(this);
        courseCode.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
        courseCode.setHint("Course Code");
        courseCode.setTextColor(getResources().getColor(android.R.color.black));
        courseCode.setTextSize(16);
        courseCode.setText(selectedCourseCode);  // Set the selected course code here

        EditText idEditText = new EditText(this);
        idEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
        idEditText.setHint("ID");
        idEditText.setTextColor(getResources().getColor(android.R.color.black));
        idEditText.setTextSize(16);

        EditText reasonAbsent = new EditText(this);
        reasonAbsent.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
        reasonAbsent.setHint("Reason");
        reasonAbsent.setTextColor(getResources().getColor(android.R.color.black));
        reasonAbsent.setTextSize(16);

        // Add EditTexts to the TableRow
        newRow.addView(courseCode);
        newRow.addView(idEditText);
        newRow.addView(reasonAbsent);

        // Add the TableRow to the TableLayout
        courseTable.addView(newRow);
    }//end
    private void saveTableData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int rowCount = courseTable.getChildCount();
        int savedRowCount = 0; // Track the number of successfully saved rows

        for (int i = 1; i < rowCount; i++) { // Start from 1 to skip the header row
            TableRow row = (TableRow) courseTable.getChildAt(i);
            EditText courseCode = (EditText) row.getChildAt(0);
            EditText idEditText = (EditText) row.getChildAt(1);
            EditText reasonAbsent = (EditText) row.getChildAt(2);

            String courseCodeText = courseCode.getText().toString();
            String idText = idEditText.getText().toString();
            String reasonText = reasonAbsent.getText().toString();

            // Generate a unique key based on the savedRowCount
            String courseCodeKey = "course_code_" + savedRowCount;
            String idKey = "id_" + savedRowCount;
            String reasonKey = "reason_" + savedRowCount;

            editor.putString(courseCodeKey, courseCodeText);
            editor.putString(idKey, idText);
            editor.putString(reasonKey, reasonText);

            savedRowCount++; // Increment savedRowCount only if data is saved successfully
        }
        editor.apply();
    }
    private void loadSavedData() {
        Map<String, ?> allEntries = sharedPreferences.getAll();

        // Recreate rows based on saved data
        int rowCount = 0; // Initialize a row counter
        while (true) {
            // Construct the keys to retrieve the data
            String courseCodeKey = "course_code_" + rowCount;
            String idKey = "id_" + rowCount;
            String reasonKey = "reason_" + rowCount;

            // Check if the keys exist in SharedPreferences
            if (allEntries.containsKey(courseCodeKey)) {
                // Retrieve the values from SharedPreferences
                String courseCode = (String) allEntries.get(courseCodeKey);
                String id = (String) allEntries.get(idKey);
                String reason = (String) allEntries.get(reasonKey);

                TableRow newRow = new TableRow(this);

                // Create EditTexts for each column in the new row
                EditText courseCodeField = new EditText(this);
                courseCodeField.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
                courseCodeField.setText(courseCode);
                courseCodeField.setTextColor(getResources().getColor(android.R.color.black));
                courseCodeField.setTextSize(16);

                EditText idEditText = new EditText(this);
                idEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
                idEditText.setText(id);
                idEditText.setTextColor(getResources().getColor(android.R.color.black));
                idEditText.setTextSize(16);

                EditText reasonAbsentField = new EditText(this);
                reasonAbsentField.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
                reasonAbsentField.setText(reason);
                reasonAbsentField.setTextColor(getResources().getColor(android.R.color.black));
                reasonAbsentField.setTextSize(16);

                // Add EditTexts to the TableRow
                newRow.addView(courseCodeField);
                newRow.addView(idEditText);
                newRow.addView(reasonAbsentField);

                // Create and add the delete button
                Button deleteButton = new Button(this);
                deleteButton.setText("-");
                deleteButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                deleteButton.setOnClickListener(v -> {
                    courseTable.removeView(newRow);
                    // Remove the corresponding data from SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(courseCodeKey);
                    editor.remove(idKey);
                    editor.remove(reasonKey);
                    editor.apply();
                });
                newRow.addView(deleteButton); // Add delete button to the row

                courseTable.addView(newRow);
                rowCount++; // Increment the row counter
            } else {
                // Exit the loop if there are no more entries for the current rowCount
                break;
            }
        }
    }
    public void st(View view) {

        startActivity(new Intent(this, absentlist.class));
    }
}