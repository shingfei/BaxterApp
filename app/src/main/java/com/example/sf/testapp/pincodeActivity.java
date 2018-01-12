package com.example.sf.testapp;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.pinlockview.PinLockView;
import com.goodiebag.pinview.Pinview;
import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;


public class pincodeActivity extends AppCompatActivity {

    private String prepincode = "11111";

    public static final String TAG = "PinLockView";
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;

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

    private PinLockListener mPinLockListener = new PinLockListener()
    {

        @Override
        public void onComplete(String pin) {

            Log.d(TAG, "Pin complete: " + pin);
            int check = Integer.parseInt(pin);

            if(check == 1111)
            {

                mainActivity();

            }
            else
            {
                System.out.println("Error");
            }
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
        Button logOffPin = (Button) findViewById(R.id.logoffPin);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        mPinLockView.setPinLockListener(mPinLockListener);
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
