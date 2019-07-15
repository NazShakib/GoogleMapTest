package com.example.googlemaptest.map;



import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.googlemaptest.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class CustomerMap extends FragmentActivity  {


   private CustomerMapFragment customerMapFragment;

   private DrawerLayout drawerLayout;


    private static final String TAG = "CustomerMap";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_map);

        drawerLayout = findViewById(R.id.drawerIdCustomer);
        //map call
         customerMapFragment = new CustomerMapFragment(this,drawerLayout);
         loadFragment(customerMapFragment);
    }


    private boolean loadFragment(Fragment fragment)
    {
        if (fragment!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.customerFragment,fragment)
                    .commit();
            return true;
        }
        return false;
    }







}
