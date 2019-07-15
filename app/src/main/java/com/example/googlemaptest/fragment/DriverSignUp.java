package com.example.googlemaptest.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.googlemaptest.R;

import com.example.googlemaptest.userData.DriverInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;


@SuppressLint("ValidFragment")
public class DriverSignUp extends Fragment{


    private TextInputLayout driverName,driverEmail,driverPassword;
    private Button driverHaveAccount,driverSignUp;
    private LinearLayout parentLinearLayout,childLinearLayout;
    private int numberOfLines = 0;
    private Button addLocation;
    private Button removeLocation;
    private Context context;

    // Write a message to the database

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private DriverInfo driverInfo;
    private static final String TAG = "DriverSignUp";

    public DriverSignUp(Context context) {
      this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_driver_sign_up, container, false);
        driverName = view.findViewById(R.id.driverNameSign);
        driverEmail = view.findViewById(R.id.driverEmailSign);
        driverPassword = view.findViewById(R.id.driverPasswordSign);
        parentLinearLayout = view.findViewById(R.id.parent_linear_layout);
        driverHaveAccount = view.findViewById(R.id.driverHaveAccount);
        driverSignUp = view.findViewById(R.id.driverSignUpButton);


        // Firebase declaration
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BusInfo");



        childLinearLayout = view.findViewById(R.id.child_linear_layout);

        addLocation = view.findViewById(R.id.add_field_button);
        removeLocation = view.findViewById(R.id.delete_button);


        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddField(v);
            }
        });

        removeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDelete(v);
            }
        });

        driverHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUP(v);
            }
        });

        driverSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUP(v);
            }
        });

        return view;
    }



    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onDelete(View v) {

        parentLinearLayout.removeViewAt(parentLinearLayout.getChildCount()-2);
    }


    private void SignUP(View view)
    {
        int id = view.getId();
        if(id==R.id.driverSignUpButton)
        {
            String name = driverName.getEditText().getText().toString().trim();
            String email = driverEmail.getEditText().getText().toString().trim();
            String password = driverPassword.getEditText().getText().toString().trim();

            List<String> list = new ArrayList<>(parentLinearLayout.getChildCount()-1);
            for (int i = 0; i < parentLinearLayout.getChildCount()-1; i++) {

                LinearLayout linearLayout = (LinearLayout)parentLinearLayout.getChildAt(i);
                EditText editText = (EditText)linearLayout.getChildAt(0);
                String result = editText.getText().toString();
                list.add(result);
            }
            driverInfo = new DriverInfo(name,email,password,list);

            print(driverInfo.getLocationList());

            if(checkAll(driverInfo)) {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uId = user.getUid();
                                    mDatabase.child(uId).setValue(driverInfo);
                                    print("Account Created");

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(context, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


            }
            else
            {
                print("Please Fill Up");
            }

        }
        else if(id==R.id.driverHaveAccount)
        {
            Fragment fragment2 = new DriverLogin(context);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.driverFragment, fragment2); // where container is the FrameLayout where Fragment 1 was first placed
            transaction.commit();
        }
        else
        {
            return;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();



    }

    private void print(Object o)
    {
        Toast.makeText(context,o.toString(),Toast.LENGTH_SHORT).show();
    }


    private boolean validCheckText(String text)
    {
        if(text.isEmpty())
        {
            return false;
        }
        return true;
    }


    private boolean checkAll(DriverInfo driverInfo)
    {
        if (validCheckText(driverInfo.getEmail()) || validCheckText(driverInfo.getName())
                || validCheckText(driverInfo.getPassword())) {

            return true;

        }

        int count = parentLinearLayout.getChildCount()-1;
        for (int i = 0;i<count;i++)
        {
            if(validCheckText(driverInfo.getLocationList().get(i)))
            {
                return true;
            }
        }


        return false;
    }


}
