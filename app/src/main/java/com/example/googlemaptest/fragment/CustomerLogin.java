package com.example.googlemaptest.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlemaptest.R;
import com.example.googlemaptest.map.CustomerMap;
import com.example.googlemaptest.map.CustomerMapFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


@SuppressLint("ValidFragment")
public class CustomerLogin extends Fragment {


    private TextInputLayout customerEmail,customerPassword;
    private Button customerHaveNotAccount,customerLogin;
   private Context context;

    public CustomerLogin(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_customer_login, container, false);
         customerEmail = view.findViewById(R.id.customerEmailLogin);
         customerPassword = view.findViewById(R.id.customerPasswordLogin);
         customerHaveNotAccount = view.findViewById(R.id.customerHaventAccount);
         customerLogin = view.findViewById(R.id.logButton);


         customerLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 login(v);
             }
         });

         customerHaveNotAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 login(v);
             }
         });

        return view;
    }


    private void login(View view)
    {
        int id = view.getId();
        if(id==R.id.logButton)
        {
            String email = customerEmail.getEditText().getText().toString().trim();
            String password = customerPassword.getEditText().getText().toString().trim();
            PermissionCheck();

        }
        else if(id==R.id.customerHaventAccount)
        {
            Fragment fragment2 = new CustomerSignUp(context);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.customerFragment, fragment2); // where container is the FrameLayout where Fragment 1 was first placed
            transaction.commit();
        }
        else
        {
            return;
        }
    }


    private void PermissionCheck() {

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivity(new Intent(context, CustomerMap.class));
                        getActivity().finish();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if(response.isPermanentlyDenied()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Permission Denied")
                                    .setMessage("Permission to access device location is permanently denied. you need to go to setting to allow the permission.")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                                        }
                                    })
                                    .show();
                        } else {
                            Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();



    }






}
