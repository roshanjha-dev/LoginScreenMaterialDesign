package com.roshanjha.loginscreenmd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmpLoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText2;
    EditText _passwordText2;
    Button _loginButton2;
    TextView _signupLink2;
    Switch _switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

        _emailText2 = (EditText)findViewById(R.id.input_email2);
        _passwordText2 = (EditText)findViewById(R.id.input_password2);
        _loginButton2 = (Button)findViewById(R.id.btn_login2);
        _signupLink2 = (TextView)findViewById(R.id.link_signup2);
        _switch2 = (Switch)findViewById(R.id.switch2);

        _loginButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmpSignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_switch2.isChecked()){
                    startActivity(new Intent(EmpLoginActivity.this, MainActivity.class));
                }
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton2.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(EmpLoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        /*String email = _emailText2.getText().toString();
        String password = _passwordText2.getText().toString();
        */

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onLoginSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton2.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton2.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText2.getText().toString();
        String password = _passwordText2.getText().toString();

        if (email.isEmpty()) {
            _emailText2.setError("enter a valid Hospital name");
            valid = false;
        } else {
            _emailText2.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText2.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText2.setError(null);
        }

        return valid;
    }
}
