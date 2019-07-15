package com.example.googlemaptest.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.googlemaptest.R;
import com.example.googlemaptest.userData.CustomerInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import afu.org.checkerframework.checker.nullness.qual.NonNull;


@SuppressLint("ValidFragment")
public class CustomerSignUp extends Fragment {

    private Context context;
    private TextInputLayout customerName,customerEmail,customerPhone,customerPassword;
    private Button customerHaveAccount,customerSignUp;

    private CustomerInfo customerInfo;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private static final String TAG = "CustomerSignUp";

    public CustomerSignUp(Context context) {
      this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_sign_up, container, false);

        customerName = view.findViewById(R.id.customerNameSign);
        customerEmail = view.findViewById(R.id.customerEmailSign);
        customerPhone = view.findViewById(R.id.customerPhoneSign);
        customerPassword = view.findViewById(R.id.customerPasswordSign);

        customerHaveAccount = view.findViewById(R.id.customerHaveAccount);
        customerSignUp = view.findViewById(R.id.signUpButton);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CustomerInfo");


        customerHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUP(v);
            }
        });


        customerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUP(v);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();



    }


    private void SignUP(View view)
    {
        int id = view.getId();
        if(id==R.id.signUpButton)
        {
            String name = customerName.getEditText().getText().toString().trim();
            String email = customerEmail.getEditText().getText().toString().trim();
            String phone = customerPhone.getEditText().getText().toString().trim();
            String password = customerPassword.getEditText().getText().toString().trim();


            customerInfo = new CustomerInfo(name,email,phone,password);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                String uId = user.getUid();
                                mDatabase.child(uId).setValue(customerInfo);

                                print("Create Account");

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });



        }
        else if(id==R.id.customerHaveAccount)
        {
            Fragment fragment2 = new CustomerLogin(context);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.customerFragment, fragment2); // where container is the FrameLayout where Fragment 1 was first placed
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
