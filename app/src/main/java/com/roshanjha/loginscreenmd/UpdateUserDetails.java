package com.roshanjha.loginscreenmd;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UpdateUserDetails extends AppCompatActivity {

    private static final String TAG = "UpdateUserDetails";

    EditText _updateName;
    EditText _updateAddress;
    EditText _updateMobile;
    Spinner _updateBloodGroup;
    TextView _updateLastDonated;
    Button _updateDetails;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_details);

        _updateName = (EditText)findViewById(R.id.update_name);
        _updateAddress = (EditText)findViewById(R.id.update_address);
        _updateMobile = (EditText)findViewById(R.id.update_mobile);
        _updateBloodGroup = (Spinner) findViewById(R.id.update_bloodGroup);
        _updateLastDonated = (TextView) findViewById(R.id.update_lastDonated);
        _updateDetails = (Button)findViewById(R.id.update_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _updateLastDonated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        UpdateUserDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                _updateLastDonated.setText(date);
            }
        };

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);

                        _updateName.setText(userInformation.getUserName());
                        _updateAddress.setText(userInformation.getAddress());
                        _updateMobile.setText(userInformation.getMobile());

                        _updateLastDonated.setText(userInformation.getLastDonated());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(UpdateUserDetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    }
                });

        _updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = _updateName.getText().toString();
                String address = _updateAddress.getText().toString();
                String mobile = _updateMobile.getText().toString();
                String bloodGroup = _updateBloodGroup.getSelectedItem().toString();
                String lastDonated = _updateLastDonated.getText().toString();

                UserInformation userInformation = new UserInformation(name, address, mobile, bloodGroup, lastDonated);

                databaseReference.setValue(userInformation);
                finish();
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
