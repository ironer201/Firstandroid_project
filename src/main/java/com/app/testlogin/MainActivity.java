package com.app.testlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    Button login_button;   // Button for login
    EditText username;     // Username field
    EditText password;     // Password field
    HashMap<String, String> usersMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);

        // Initialize UI components
        login_button = findViewById(R.id.login_button);
        username = findViewById(R.id.username);   // Referencing the username EditText
        password = findViewById(R.id.password);   // Referencing the password EditText
        //usersMap.put("GSB", "123");

        usersMap.put("jubayed", "000");
        usersMap.put("akib", "111");
        usersMap.put("GSB", "121");
        usersMap.put("Omor", "123");



    }

    public void att(View view) {
        String inputUsername = username.getText().toString();
        String inputPassword = password.getText().toString();

// Check if the entered username exists in the users map and if the password is correct
        if(inputUsername != null && inputPassword != null) {
            // Make sure to trim inputs to avoid issues with whitespace
            inputUsername = inputUsername.trim();
            inputPassword = inputPassword.trim();

            if (usersMap.containsKey(inputUsername)) {
                // Check if the password matches
                if (usersMap.get(inputUsername).equals(inputPassword)) {
                    // If the login is successful, check if the username is "GSB"
                    if ("GSB".equals(inputUsername)&&"121".equals(inputPassword)) {
                        Toast.makeText(this, "Login Successful. Redirecting to Author Name Page.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, autormain.class)); // Author Name activity
                    }
                    else if ("Omor".equals(inputUsername)&&"123".equals(inputPassword)) {
                        Toast.makeText(this, "Login Successful. Redirecting to Author Name Page.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, autormain.class)); // Author Name activity
                    }

                    else {
                        Toast.makeText(this, "Login Successful. Redirecting to User Page.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, page.class)); // User Page activity
                    }

                } else {
                    // Incorrect password
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Username not found
                Toast.makeText(this, "Username Not Found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

}