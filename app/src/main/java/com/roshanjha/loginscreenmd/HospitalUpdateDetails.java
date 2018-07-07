package com.roshanjha.loginscreenmd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalUpdateDetails extends AppCompatActivity {

    private static final String TAG = "HospitalUpdateDetails";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference databaseReference;

    EditText _HupdateName;
    EditText _HupdateAddress;
    EditText _HupdateMobile;
    EditText _HupdateAvgBloodReqd;
    Button _HupdateDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update_details);

        mAuth = FirebaseAuth.getInstance();

        _HupdateName = (EditText)findViewById(R.id.Hupdate_name);
        _HupdateAddress = (EditText)findViewById(R.id.Hupdate_address);
        _HupdateMobile = (EditText)findViewById(R.id.Hupdate_mobile);
        _HupdateAvgBloodReqd = (EditText)findViewById(R.id.Hupdate_avgBldReqd);
        _HupdateDetails = (Button)findViewById(R.id.update_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HospitalUserInformation hospitalUserInformation = dataSnapshot.getValue(HospitalUserInformation.class);

                        _HupdateName.setText(hospitalUserInformation.getHospitalName());
                        _HupdateAddress.setText(hospitalUserInformation.getAddress());
                        _HupdateMobile.setText(hospitalUserInformation.getMobile());
                        _HupdateAvgBloodReqd.setText(hospitalUserInformation.getAvgBloodReqd());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(HospitalUpdateDetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    }
                });

        _HupdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = _HupdateName.getText().toString();
                String address = _HupdateAddress.getText().toString();
                String mobile = _HupdateMobile.getText().toString();
                String avgBldReqd = _HupdateAvgBloodReqd.getText().toString();

                HospitalUserInformation hospitalUserInformation = new HospitalUserInformation(name, address, mobile, avgBldReqd);
                FirebaseUser user = mAuth.getCurrentUser();

                databaseReference.child(user.getUid()).setValue(hospitalUserInformation);

                finish();
                startActivity(new Intent(HospitalUpdateDetails.this, HospitalProfileActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
