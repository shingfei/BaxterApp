package com.example.sf.testapp;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private String dd;
    public static final String TAG = "PinLockView";
//    Button mEmailSignInButton = (Button) findViewById(R.id.confirmPin);
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;

   /* private void menuActivity(View view)
    {
        Intent mainMenuActivity = new Intent(this,mainMenu.class);
        startActivity(mainMenuActivity);
        return;
    }*/

    private void Active()
    {
        Intent mainMenuActivity = new Intent(this,mainMenu.class);
        startActivity(mainMenuActivity);
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

                Active();

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

       // mPinLockView.setPinLength(4);
       // mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        //mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        mPinLockView.setPinLockListener(mPinLockListener);

                /*mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                                System.out.println("working");
                                menuActivity(view);
                    }
                });*/
            }

}
