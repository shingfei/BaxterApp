package com.example.sf.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sf.backend.login.UserLogin;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView loginText;

    //Oncreate activities. Main code goes here
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        loginText = (TextView) findViewById(R.id.loginText);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserLogin.login(mEmailView.getText().toString(), mPasswordView.getText().toString())) {
                    pinActivity(view); //calls the methode where the new pinactivity resides.
                    finish(); //Ends the current activity
                } else {
                    loginText.setText("Email and/or password incorrect");
                    mEmailView.setText("");
                    mPasswordView.setText("");
                }
            }
        });

    }

    //Starting new activity for the pincode screen.
    private void pinActivity(View view)
    {
        Intent pincodeActivity = new Intent(this, com.example.sf.testapp.pincodeActivity.class);
        startActivity(pincodeActivity);
    }

    //Supresses the back button on a phone or ends the app itself
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

