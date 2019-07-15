package com.example.googlemaptest;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.googlemaptest.fragment.DriverLogin;


public class DriverActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        toolbar = findViewById(R.id.toolbarDriver);
        toolbar.setTitle("Driver Activity");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Fragment fragment = new DriverLogin(DriverActivity.this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.driverFragment,fragment)
                .commit();
    }
}
