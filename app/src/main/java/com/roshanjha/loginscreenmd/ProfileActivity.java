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

public class ProfileActivity extends AppCompatActivity {


    Button _signOut;
    Button _editDetails;
    TextView _displayName;
    TextView _displayAddress;
    TextView _displayEmail;
    TextView _displayMobile;
    TextView _displayBldGrp;
    TextView _displayLastDonated;


    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();


        _displayName = (TextView)findViewById(R.id.displayName);
        _displayAddress = (TextView)findViewById(R.id.displayAddress);
        _displayEmail = (TextView)findViewById(R.id.displayEmail);
        _displayMobile = (TextView)findViewById(R.id.displayMobile);
        _displayBldGrp = (TextView)findViewById(R.id.displayBldGrp);
        _displayLastDonated = (TextView)findViewById(R.id.displayLastDonated);
        _signOut = (Button)findViewById(R.id._signOut);
        _editDetails = (Button)findViewById(R.id.editUserDetails);



        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    finish();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                }

            }
        };

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())

        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);

                _displayName.setText(userInformation.getUserName());
                _displayAddress.setText(userInformation.getAddress());
                _displayEmail.setText(userInformation.getEmail());
                _displayMobile.setText(userInformation.getMobile());
                _displayBldGrp.setText(userInformation.getBloodGrp());
                _displayLastDonated.setText(userInformation.getLastDonated());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        _signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        _editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, UpdateUserDetails.class));
            }
        });

    }
}
