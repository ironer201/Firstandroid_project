package com.app.testlogin;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class static_asi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staticasi);
        TableLayout tableLayout = findViewById(R.id.main_table);

        // Load saved checkbox states
        SharedPreferences sharedPreferences = getSharedPreferences("CheckboxStates", MODE_PRIVATE);
        boolean checkbox1Checked = sharedPreferences.getBoolean("checkbox1_unique_id", false);
        boolean checkbox2Checked = sharedPreferences.getBoolean("checkbox2_unique_id", false);
        boolean checkbox3Checked = sharedPreferences.getBoolean("checkbox3_unique_id", false);
        boolean checkbox4Checked = sharedPreferences.getBoolean("checkbox4_unique_id", false);
        boolean checkbox5Checked = sharedPreferences.getBoolean("checkbox5_unique_id", false);

        // Update table row status
        updateStatusColor(tableLayout, 1, checkbox1Checked);
        updateStatusColor(tableLayout, 2, checkbox2Checked);
        updateStatusColor(tableLayout, 3, checkbox3Checked);
        updateStatusColor(tableLayout, 4, checkbox4Checked);
        updateStatusColor(tableLayout, 5, checkbox5Checked);
    }
    private void updateStatusColor(TableLayout tableLayout, int rowIndex, boolean isChecked) {
        // Get the specific row in the table
        TableRow row = (TableRow) tableLayout.getChildAt(rowIndex);

        if (row != null) {
            // Assume the "Status" column is the last TextView in the row
            TextView statusColumn = (TextView) row.getChildAt(row.getChildCount() - 1);

            if (statusColumn != null) {
                // Update text color based on checkbox state
                if (isChecked) {
                    //statusColumn.setText("Absent");
                    statusColumn.setBackgroundColor(Color.RED);  // Red for absent
                }
            }
        }
    }
}