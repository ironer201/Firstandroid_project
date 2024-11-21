package com.app.testlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

    }

    //attendence
    public void att(View view){
        startActivity(new Intent(this,attendence.class));
    }
    //Assignment
    public void assig(View view){
        startActivity(new Intent(this,Assignment.class));
    }

    //CT
//   public void ct(View view){
//        startActivity(new Intent(this, CT.class));
//    }

    //Absent
    public void absent(View view) {startActivity(new Intent(this, absent.class));}
    //list
    public void li(View view) {
        startActivity(new Intent(this, list.class));
    }
}