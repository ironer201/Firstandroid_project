package com.app.testlogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//bit image
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class Assignment extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assignment);

        // Back Button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the current activity and returns to the previous screen
            }
        });

        // Upload Button 1
        ImageButton uploadButton1 = findViewById(R.id.upload);
        uploadButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();  // Open file chooser for the image
            }
        });

        // Upload Button 2 (similar to button 1, can be customized for different functionality)
        ImageButton uploadButton2 = findViewById(R.id.upload1);
        uploadButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();  // Can be used for a different type of file if needed
            }
        });

        // Button 3 (Functionality to be customized as needed)
        ImageButton button3 = findViewById(R.id.upload2);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action for button 3
                openFileChooser();
            }
        });

        // Button 4 (Functionality to be customized as needed)
        ImageButton button4 = findViewById(R.id.upload3);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action for button 4
                openFileChooser();
            }
        });

        // Button 5 (Functionality to be customized as needed)
        ImageButton button5 = findViewById(R.id.upload4);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action for button 5
                openFileChooser();
            }
        });
        // Example: Assuming you have multiple checkboxes
        CheckBox checkbox1 = findViewById(R.id.check1);
        CheckBox checkbox2 = findViewById(R.id.check2);
        CheckBox checkbox3 = findViewById(R.id.check3);
        CheckBox checkbox4 = findViewById(R.id.check4);
        CheckBox checkbox5 = findViewById(R.id.check5);
        // Add more as needed

        // Load saved states for each checkbox
        checkbox1.setChecked(loadCheckboxState("checkbox1_unique_id"));
        checkbox2.setChecked(loadCheckboxState("checkbox2_unique_id"));
        checkbox3.setChecked(loadCheckboxState("checkbox3_unique_id"));
        checkbox4.setChecked(loadCheckboxState("checkbox4_unique_id"));
        checkbox5.setChecked(loadCheckboxState("checkbox5_unique_id"));

        // Update Button to save states when clicked
        Button updateButton = findViewById(R.id.submit);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the current state of each checkbox
                saveCheckboxState("checkbox1_unique_id", checkbox1.isChecked());
                saveCheckboxState("checkbox2_unique_id", checkbox2.isChecked());
                saveCheckboxState("checkbox3_unique_id", checkbox3.isChecked());
                saveCheckboxState("checkbox4_unique_id", checkbox4.isChecked());
                saveCheckboxState("checkbox5_unique_id", checkbox5.isChecked());

                Toast.makeText(Assignment.this, "Checkbox states saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //checkbox
    private void saveCheckboxState(String checkboxId, boolean isChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("CheckboxStates", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(checkboxId, isChecked);  // Save state with a unique ID for each checkbox
        editor.apply();  // Apply changes
    }
    private boolean loadCheckboxState(String checkboxId) {
        SharedPreferences sharedPreferences = getSharedPreferences("CheckboxStates", MODE_PRIVATE);
        return sharedPreferences.getBoolean(checkboxId, false);  // Default is unchecked
    }

    // Open File Chooser to pick images
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImageToRealtimeDatabase();  // Call the new method
        }
    }


    // Upload Image to Firebase
    private String convertImageToBase64(Uri imageUri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in converting image", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    private void uploadImageToRealtimeDatabase() {
        if (imageUri != null) {
            String base64String = convertImageToBase64(imageUri);

            if (base64String != null) {
                // Initialize Firebase Database reference
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("uploads");

                // Generate a unique ID for each image entry
                String uniqueId = databaseRef.push().getKey();

                if (uniqueId != null) {
                    // Save Base64 string to the database
                    databaseRef.child(uniqueId).setValue(base64String)
                            .addOnSuccessListener(aVoid -> Toast.makeText(Assignment.this, "Upload successful", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(Assignment.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            } else {
                Toast.makeText(this, "Failed to convert image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void status(View view) {
        startActivity(new Intent(this,static_asi.class));
    }
}