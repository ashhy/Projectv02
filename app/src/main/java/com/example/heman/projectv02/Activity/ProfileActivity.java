package com.example.heman.projectv02.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heman.projectv02.R;
import com.example.heman.projectv02.SharedPreferences.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    //Declare
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    Context context;
    private TextView textViewUsername, textViewUserEmail;
    private Button buttonGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initial Check
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        //Initialize
        buttonGPS = (Button) findViewById(R.id.buttonGPS);
        buttonGPS.setOnClickListener(this);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this,"This will open up the settings", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == buttonGPS) {
            startActivity(new Intent(getApplicationContext(), SurveyListActivity.class));
        }
    }


}
