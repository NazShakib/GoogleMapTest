package com.example.googlemaptest;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationSettingsRequest;

import java.util.Map;

import static com.google.android.gms.common.GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE;

public class MainActivity extends AppCompatActivity {

    private Button driverButton,custommerButton;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        driverButton = findViewById(R.id.driverButton);
        custommerButton = findViewById(R.id.customerButton);

        if(isServicesOk())
        {
            initial();
        }
    }






    private void initial()
    {
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DriverActivity.class));
            }
        });


        custommerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CustomerActivity.class));
            }
        });

    }


    private boolean isServicesOk()
    {
        Log.d(TAG, "isServicesOk: Google services checking");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if(available== ConnectionResult.SUCCESS)
        {
            Log.i(TAG, "isServicesOk: Everything is ok");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Log.i(TAG, "isServicesOk: We can resolve the problem");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,available,GOOGLE_PLAY_SERVICES_VERSION_CODE);
            dialog.show();
        }
        else
        {
            Toast.makeText(this,"You can't make map connected",Toast.LENGTH_SHORT).show();

        }
        return false;

    }

}
