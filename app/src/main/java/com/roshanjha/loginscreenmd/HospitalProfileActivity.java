package com.roshanjha.loginscreenmd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalProfileActivity extends AppCompatActivity {

    Button _HsignOut;
    Button _HeditDetails;
    TextView _HdisplayName;
    TextView _HdisplayAddress;
    TextView _HdisplayEmail;
    TextView _HdisplayMobile;
    TextView _HdisplayAvgBldReqd;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_profile);

        mAuth = FirebaseAuth.getInstance();


        _HdisplayName = (TextView)findViewById(R.id.HdisplayName);
        _HdisplayAddress = (TextView)findViewById(R.id.HdisplayAddress);
        _HdisplayEmail = (TextView)findViewById(R.id.HdisplayEmail);
        _HdisplayMobile = (TextView)findViewById(R.id.HdisplayMobile);
        _HdisplayAvgBldReqd = (TextView)findViewById(R.id.HdisplayAvgBldReqd);

        _HsignOut = (Button)findViewById(R.id._HsignOut);
        _HeditDetails = (Button)findViewById(R.id.HeditDetails);



        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    finish();
                    startActivity(new Intent(HospitalProfileActivity.this, EmpLoginActivity.class));
                }

            }
        };

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HospitalUserInformation hospitalUserInformation = dataSnapshot.getValue(HospitalUserInformation.class);

                        _HdisplayName.setText(hospitalUserInformation.getHospitalName());
                        _HdisplayAddress.setText(hospitalUserInformation.getAddress());
                        _HdisplayEmail.setText(hospitalUserInformation.getMailid());
                        _HdisplayMobile.setText(hospitalUserInformation.getMobile());
                        _HdisplayAvgBldReqd.setText(hospitalUserInformation.getAvgBloodReqd());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(HospitalProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    }
                });

        _HsignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(HospitalProfileActivity.this, MainActivity.class));
            }
        });

        _HeditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalProfileActivity.this, HospitalUpdateDetails.class));
            }
        });
    }
}
