package com.example.sf.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class pincodeActivity extends AppCompatActivity {


    private void menuActivity(View view)
    {
        Intent mainMenuActivity = new Intent(this,mainMenu.class);
        startActivity(mainMenuActivity);
        return;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);

        Button mEmailSignInButton = (Button) findViewById(R.id.confirmPin);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuActivity(view);
            }
        });
    }

}
