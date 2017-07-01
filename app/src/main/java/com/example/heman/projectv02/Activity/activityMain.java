package com.example.heman.projectv02.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.heman.projectv02.R;

/**
 * Created by Harshit on 7/1/2017.
 */

public class activityMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void goToLoginPage(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}

