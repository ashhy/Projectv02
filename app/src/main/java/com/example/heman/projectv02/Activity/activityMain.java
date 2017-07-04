package com.example.heman.projectv02.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.heman.projectv02.R;

/**
 * Created by Harshit on 7/1/2017.
 */

public class activityMain extends AppCompatActivity {
    TextView a, b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        a = (TextView) findViewById(R.id.textView3);
        b = (TextView) findViewById(R.id.text_list_view);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/ft.ttf");
        a.setTypeface(face);
        b.setTypeface(face);
    }

    public void goToLoginPage(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void exitApp(View view) {
        Button exit = (Button) findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}

