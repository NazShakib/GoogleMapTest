package com.example.googlemaptest;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.googlemaptest.fragment.CustomerLogin;


public class CustomerActivity extends AppCompatActivity {



    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        toolbar = findViewById(R.id.toolbarCustomer);
        toolbar.setTitle("Customer Activity");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new CustomerLogin(CustomerActivity.this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.customerFragment,fragment)
                .commit();
    }



}
