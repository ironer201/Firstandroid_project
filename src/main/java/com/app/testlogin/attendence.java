package com.app.testlogin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class attendence extends AppCompatActivity {

    private static final String STUDENT_LIST_KEY = "student_list";
    private static final String CHECKBOX_STATES_KEY = "checkbox_states";

    //date
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private SharedPreferences sharedPreferences;
    private TableLayout attendanceTable;
    private Spinner courseSpinner;
    //private Map<String, Boolean> checkboxStates = new HashMap<>();

    String[] courses = {"CSE-2213", "CSE-2215", "CSE-2211", "MATH-2247", "EEE-2266"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Close the current activity
        // Initialize date picker
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());


        // Initialize SharedPreferences and TableLayout
        sharedPreferences = getSharedPreferences("student_data", MODE_PRIVATE);
        attendanceTable = findViewById(R.id.attenTable);

        // Initialize Spinner (Dropdown Menu)
        courseSpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);

        // Spinner Item Selected Listener
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selectedCourse, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle when no item is selected
            }
        });

        // Initialize and load attendance data
        loadAttendanceData();

        // Initialize Date Picker
        initDatePicker();
        // Submit button
        Button submitButton = findViewById(R.id.update);
        submitButton.setOnClickListener(v -> submitAttendance());
    }

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

    // Load attendance data from SharedPreferences
    private void loadAttendanceData() {
        String jsonString = sharedPreferences.getString(STUDENT_LIST_KEY, null);
        int paddingInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        if (jsonString != null) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Map<String, String>>>() {}.getType();
                ArrayList<Map<String, String>> studentList = gson.fromJson(jsonString, type);

                for (Map<String, String> student : studentList) {
                    String studentId = student.get("id");

                    // Create a new TableRow
                    TableRow newRow = new TableRow(this);

                    // ID TextView
                    TextView idTextView = new TextView(this);
                    idTextView.setText(studentId);
                    idTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    idTextView.setTextColor(Color.BLACK);
                    idTextView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

                    // Name TextView
                    TextView nameTextView = new TextView(this);
                    nameTextView.setText(student.get("name"));
                    nameTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    nameTextView.setTextColor(Color.BLACK);

                    // Checkbox
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

                    // Restore checkbox state
                    boolean isChecked = sharedPreferences.getBoolean(CHECKBOX_STATES_KEY + "_" + studentId, false);
                    checkBox.setChecked(isChecked);

                    // Save state on change
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked1) -> saveCheckboxState(studentId, isChecked1));

                    // Add views to the row
                    newRow.addView(idTextView);
                    newRow.addView(nameTextView);
                    newRow.addView(checkBox);

                    // Add the row to the table
                    attendanceTable.addView(newRow);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load student data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveCheckboxState(String studentId, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHECKBOX_STATES_KEY + "_" + studentId, isChecked);
        editor.apply();
    }

    private void submitAttendance() {
        String selectedCourse = courseSpinner.getSelectedItem().toString();
        String selectedDate = dateButton.getText().toString();

        ArrayList<Map<String, String>> attendanceData = new ArrayList<>();

        for (int i = 0; i < attendanceTable.getChildCount(); i++) {
            View rowView = attendanceTable.getChildAt(i);
            if (rowView instanceof TableRow) {
                TableRow row = (TableRow) rowView;

                TextView idView = (TextView) row.getChildAt(0);
                TextView nameView = (TextView) row.getChildAt(1);
                CheckBox checkBox = (CheckBox) row.getChildAt(2);

                if (idView != null && nameView != null && checkBox != null) {
                    String id = idView.getText().toString();
                    String name = nameView.getText().toString();
                    String status = checkBox.isChecked() ? "Present" : "Absent";

                    Map<String, String> record = new HashMap<>();
                    record.put("date", selectedDate);
                    record.put("course", selectedCourse);
                    record.put("id", id);
                    record.put("name", name);
                    record.put("status", status);
                    attendanceData.add(record);
                }
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonData = gson.toJson(attendanceData);
        editor.putString("attendance_records", jsonData);
        editor.apply();

        Toast.makeText(this, "Attendance submitted!", Toast.LENGTH_SHORT).show();
    }

    public void still(View view) {
        startActivity(new Intent(this,attenden_list.class));
    }
}
