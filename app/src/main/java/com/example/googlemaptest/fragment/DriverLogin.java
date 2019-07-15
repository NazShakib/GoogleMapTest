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
import android.widget.Toast;

import com.example.googlemaptest.map.DriverMap;
import com.example.googlemaptest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import afu.org.checkerframework.checker.nullness.qual.NonNull;


@SuppressLint("ValidFragment")
public class DriverLogin extends Fragment {


    private Context context;
    private TextInputLayout driverEmail,driverPassword;
    private Button driverHaveNotAccount,driverLogin;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private static final String TAG = "DriverLogin";

    public DriverLogin(Context context) {
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_driver_login, container, false);

        driverEmail = view.findViewById(R.id.driverEmailLogin);
        driverPassword = view.findViewById(R.id.driverPasswordLogin);
        driverHaveNotAccount = view.findViewById(R.id.driverHaventAccount);
        driverLogin = view.findViewById(R.id.driverLogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        driverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        driverHaveNotAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });


        return view;
    }

    private void login(View v) {

        int id = v.getId();
        if(id==R.id.driverLogin)
        {
            String email = driverEmail.getEditText().getText().toString().trim();
            String password = driverPassword.getEditText().getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                final FirebaseUser user = mAuth.getCurrentUser();
                                String uid= user.getUid();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("BusInfo").child(uid);

                                mDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                                        String userType = dataSnapshot.child("BusInfo").getValue().toString();
                                        if (userType.equals("BusInfo"))
                                        {
                                           print("Logged In Successfully");
                                        }
                                        else
                                        {
                                            print("Wrong Email or Password");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {

                                    }
                                });

                                String identity = mDatabase.getKey();

                                Log.d(TAG, "onComplete: "+identity);


                            } else {

                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


            //startActivity(new Intent(context, DriverMap.class));


        }
        else if(id==R.id.driverHaventAccount)
        {
            Fragment fragment2 = new DriverSignUp(context);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.driverFragment, fragment2); // where container is the FrameLayout where Fragment 1 was first placed
            transaction.commit();
        }
        else
        {
            return;
        }

    }

    private void print(Object o)
    {
        Toast.makeText(context,o.toString(),Toast.LENGTH_SHORT).show();
    }

}
