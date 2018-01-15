package com.example.sf.testapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andrognito.pinlockview.PinLockView;
import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;


public class pincodeActivity extends AppCompatActivity {

    public static final String TAG = "PinLockView";
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private TextView errorLabel;
    private boolean pinCreated;
    private boolean pinSaved;
    private int savedPincode;
    private String pincode = "pincode";
    private String pinCreate = "pinCreate";
    private SharedPreferences pincodeSaved;
    private SharedPreferences pincodeCreated;


    //On create events
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
        pincodeSaved = getPreferences(MODE_PRIVATE);
        pincodeCreated = getPreferences(MODE_PRIVATE);
        //Shared viarable
        pinCreated = pincodeCreated.getBoolean(pinCreate,false);
        savedPincode = pincodeSaved.getInt(pincode,0);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        errorLabel = (TextView) findViewById(R.id.profile_name);
        Button changePin = (Button) findViewById(R.id.changePin);
        Button logOffPin = (Button) findViewById(R.id.logoffPin);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        SharedPreferences sharedPref= getSharedPreferences("mypref", 0);

        if(pinCreated == false)
        {
            errorLabel.setText("Enter a new pin");
        }
        else
        {
            errorLabel.setText("Enter your pin");
        }

        //Reset pin button
        changePin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pincodeSaved.edit().clear().commit();
                pincodeCreated.edit().clear().commit();
                pinCreated = false;
                loginActivity(view);
            }
        });

        //Logoff button
        logOffPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                loginActivity(view);
                finish();
            }
        });


    }

    private void mainActivity()
    {
        Intent mainMenuActivity = new Intent(this,mainMenu.class);
        startActivity(mainMenuActivity);
        return;
    }

    //Login activity declaration
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

            if(pincodeSaved.getInt(pincode,savedPincode) == check && pincodeCreated.getBoolean(pinCreate,pinSaved) == true)
            {
                mainActivity(); // Starting new activity
                finish(); //Ends previous activity
            }
            else
            {
                if(pincodeCreated.getBoolean(pinCreate,pinSaved) == true) //Check if the pin is created and if its incorrect
                {
                    errorLabel.setText("Try again");
                    System.out.println(pincodeSaved.getInt(pincode,savedPincode));
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

            if(pincodeSaved.getInt(pincode,savedPincode) == checkPin)
            {
                pinSaved = true;
                pincodeCreated.edit().putBoolean(pinCreate, pinSaved).commit();
                //allowing the normal pinchecker to do his job
                System.out.println("stored worked");
                errorLabel.setText("Voer uw pin");
                mPinLockView.resetPinLockView();
                return true;
            }
            else if(checkPin>0) //Checking if the user filled in the created pin and gets compared to the stored pincode
            {
                    savedPincode = checkPin;
                    pincodeSaved.edit().putInt(pincode,savedPincode).commit();

                    mPinLockView.resetPinLockView();
                    System.out.println("checkin creating pin" + checkPin + " "+pincodeSaved.getInt(pincode,checkPin));
                    errorLabel.setText("Re enter pin");
            }
            else
            {
                return false;
            }

            return false;
        }

        //Leave overridden functions as is
        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        //Leave overridden functions as is
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



}
