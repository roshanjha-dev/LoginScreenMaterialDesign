package com.roshanjha.loginscreenmd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class EmpSignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference databaseReference;

    EditText _nameText4;
    EditText _addressText4;
    EditText _emailText4;
    EditText _mobileText4;
    EditText _avgBloodReqd4;
    EditText _passwordText4;
    EditText _reEnterPasswordText4;
    Button _signupButton4;
    TextView _loginLink4;
    Switch _switch4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_signup);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        /*firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){
                    Intent intent = new Intent(EmpSignupActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };*/

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(EmpSignupActivity.this, HospitalProfileActivity.class));
        }

        _nameText4 = (EditText)findViewById(R.id.input_name4);
        _addressText4 = (EditText)findViewById(R.id.input_address4);
        _emailText4 = (EditText)findViewById(R.id.input_email4);
        _mobileText4 = (EditText)findViewById(R.id.input_mobile4);
        _avgBloodReqd4 = (EditText)findViewById(R.id.avg_blood_reqd4);
        _passwordText4 = (EditText)findViewById(R.id.input_password4);
        _reEnterPasswordText4 = (EditText)findViewById(R.id.input_reEnterPassword4);
        _signupButton4 = (Button)findViewById(R.id.btn_signup4);
        _loginLink4 = (TextView)findViewById(R.id.link_login4);
        _switch4 = (Switch)findViewById(R.id.switch4);

        _signupButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EmpLoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _switch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_switch4.isChecked()){
                    startActivity(new Intent(EmpSignupActivity.this, SignupActivity.class));
                }
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            _signupButton4.setEnabled(true);
            return;
        }

        _signupButton4.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(EmpSignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText4.getText().toString();
        final String address = _addressText4.getText().toString();
        final String email = _emailText4.getText().toString();
        final String mobile = _mobileText4.getText().toString();
        final String avgBloodReqd = _avgBloodReqd4.getText().toString();
        String password = _passwordText4.getText().toString();
        String reEnterPassword = _reEnterPasswordText4.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(EmpSignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(EmpSignupActivity.this, "Registration error" + task.getException(), Toast.LENGTH_SHORT).show();
                }else {

                    sendEmailVerification();
                    HospitalUserInformation hospitalUserInformation = new HospitalUserInformation(name, address, email, mobile, avgBloodReqd);

                    FirebaseUser user = mAuth.getCurrentUser();

                    databaseReference.child(user.getUid()).setValue(hospitalUserInformation);

                    finish();
                    startActivity(new Intent(EmpSignupActivity.this, HospitalProfileActivity.class));

                    /*String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                    Map newPost = new HashMap();
                    newPost.put("name", name);
                    newPost.put("address", address);
                    newPost.put("email", email);
                    newPost.put("phone", mobile);
                    newPost.put("avgBloodReqd", avgBloodReqd);

                    current_user_db.setValue(newPost);*/
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
        if(user2!=null){
            user2.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(EmpSignupActivity.this, "Check the email for Verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }


    public void onSignupSuccess() {
        _signupButton4.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText4.getText().toString();
        String address = _addressText4.getText().toString();
        String email = _emailText4.getText().toString();
        String mobile = _mobileText4.getText().toString();
        String password = _passwordText4.getText().toString();
        String reEnterPassword = _reEnterPasswordText4.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText4.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText4.setError(null);
        }

        if (address.isEmpty()) {
            _addressText4.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText4.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText4.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText4.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText4.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText4.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText4.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText4.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText4.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText4.setError(null);
        }

        return valid;
    }
}
