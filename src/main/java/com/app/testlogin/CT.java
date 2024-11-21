package com.app.testlogin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class CT extends AppCompatActivity {

    String[] courses = {"CSE-2213","CSE-2215","CSE-2211","MATH-2247","EEE-2266"};
    Spinner courseSpinner;
    TableLayout CTTable;
    // Temporary storage for checkbox states based on the selected course
    HashMap<String, boolean[]> courseCheckBoxStates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ct);
        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the current activity and returns to the previous screen
            }
        });
        // Initialize Spinner and TableLayout
        courseSpinner = findViewById(R.id.spinner);
        CTTable = findViewById(R.id.CTTable);

        // Initialize the HashMap to store checkbox states for each course
        courseCheckBoxStates = new HashMap<>();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        courseSpinner.setAdapter(adapter);

        // Set an onItemSelectedListener to handle item selections
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get selected item
                String selectedCourse = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selectedCourse, Toast.LENGTH_SHORT).show();

                // Display table based on the selected course and retrieve checkbox states
                displayCourseTable(selectedCourse);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle case when no item is selected
            }
        });

    }
    private void displayCourseTable(String course) {
        // Clear existing rows except the header
        CTTable.removeViews(1, CTTable.getChildCount() - 1);

        // Make the table visible
        CTTable.setVisibility(View.VISIBLE);

        // Define default checkbox states for each course if not already saved
        if (!courseCheckBoxStates.containsKey(course)) {
            courseCheckBoxStates.put(course, new boolean[]{false, false, false, false});
        }

        // Retrieve the saved checkbox states for the selected course
        boolean[] checkBoxStates = courseCheckBoxStates.get(course);

        // Display rows with checkboxes
        TableRow row = new TableRow(this);

        // Create four checkboxes for each course row, and set them based on the stored state
        for (int i = 0; i < 4; i++) {
            CheckBox checkBox = new CheckBox(this);
            assert checkBoxStates != null;
            checkBox.setChecked(checkBoxStates[i]);
            int finalI = i; // Capture the index for use in the listener
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Update the stored state for this checkbox
                checkBoxStates[finalI] = isChecked;
                courseCheckBoxStates.put(course, checkBoxStates);
            });
            row.addView(checkBox);
        }

        // Add the row to the table
        CTTable.addView(row);
    }

}