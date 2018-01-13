package com.example.sf.testapp;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.PinLockView;
import com.goodiebag.pinview.Pinview;
import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import org.w3c.dom.Text;


public class pincodeActivity extends AppCompatActivity {

    public static final String TAG = "PinLockView";
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private TextView errorLabel;
    private boolean pinCreated = false;
    private int storedPin = 0;

    //Activity declaration
    private void mainActivity()
    {
        Intent mainMenuActivity = new Intent(this,mainMenu.class);
        startActivity(mainMenuActivity);
        return;
    }

    private void loginActivity(View view)
    {
        Intent loginActivity = new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
        return;
    }

    //A pincode checker if the pincode has been made or is correct.
    private PinLockListener mPinLockListener = new PinLockListener()
    {

        @Override
        public void onComplete(String pin) {

            Log.d(TAG, "Pin complete: " + pin);
            int check = Integer.parseInt(pin); //Parsing since the library doesn't have any other way to recieve the pin

            if(check == storedPin && pinCreated == true)
            {
                mainActivity(); // Starting new activity
                finish(); //Ends previous activity
            }
            else
            {
                if(pinCreated == true) //Check if the pin is created and if its incorrect
                {
                    errorLabel.setText("Try again");
                    mPinLockView.resetPinLockView();//Resetting the pinview
                    System.out.println("Error");
                }
                else
                {
                    checkPin(pin); //Method to check if the pin has been created and is correct
                }
            }
        }

        public boolean checkPin(String pin)
        {
            int checkPin = Integer.parseInt(pin);

            if(storedPin == checkPin)
            {
                pinCreated = true; //allowing the normal pinchecker to do his job
                System.out.println("stored worked");
                errorLabel.setText("Voer uw pin");
                mPinLockView.resetPinLockView();
                return true;
            }
            else if(checkPin>0) //Checking if the user filled in the created pin and gets compared to the stored pincode
            {
                    storedPin = checkPin;
                    mPinLockView.resetPinLockView();
                    //System.out.println("checkin creating pin" + checkPin + " "+storedPin);
                    errorLabel.setText("Re enter pin");
            }
            else
            {
                return false;
            }

            return false;
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }

    };

    //Prevents action on the back button.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        errorLabel = (TextView) findViewById(R.id.profile_name);
        Button changePin = (Button) findViewById(R.id.changePin);
        Button logOffPin = (Button) findViewById(R.id.logoffPin);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        if(pinCreated == false)
        {
            errorLabel.setText("Maak uw pin");
        }
        else
        {
            errorLabel.setText("Voer uw pin in");
        }

        changePin.setOnClickListener(new View.OnClickListener()//Allowing the user reset his/her pincode if the user desires to, but he/she has to re-enter the mail & password.
        {
        @Override
        public void onClick(View view)
        {
            pinCreated = false;
            loginActivity(view);
        }
    });
        logOffPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                loginActivity(view);
                finish();
            }
        });
    }

}
