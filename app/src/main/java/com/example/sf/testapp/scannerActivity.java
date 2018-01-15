/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sf.testapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sf.backend.api.networkControl;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */
public class scannerActivity extends Activity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton useFlash;
    private CompoundButton verifyCheck;
    private TextView statusMessage;
    private TextView barcodeValue;
    private TextView informationValue;
    private TextView topLabel;
    private TextView secondBottomLabel;
    private TextView bottomLabel;
    private Button submitButton;
    private Button returnButton;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    Button btnGetRound, btnDis, btnScan;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);
        informationValue = (TextView)findViewById(R.id.informationText);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        verifyCheck = (CompoundButton) findViewById(R.id.checkItem);
        topLabel = (TextView)findViewById(R.id.topScannerLabel);
        secondBottomLabel = (TextView)findViewById(R.id.secondBottomLabel);
        bottomLabel = (TextView)findViewById(R.id.BottomScannerLabel);
        submitButton = (Button)findViewById(R.id.submitButton);
        returnButton = (Button)findViewById(R.id.returnButton);

        verifyCheck.setVisibility(View.INVISIBLE);
        findViewById(R.id.read_barcode).setOnClickListener(this);

        submitButton.setOnClickListener(new View.OnClickListener() //Submit the item that was scanned before
        {
            @Override
            public void onClick(View view)
            {
                if(verifyCheck.isChecked() == true)
                {
                    System.out.println("submit this one");
                }
                else
                {
                    System.out.println("uncheck this one");
                }
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deviceActivity(view);
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
    }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) //checking if there's any data coming in from a barcode
                {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue); //set your own text here.
                    informationValue.setText("Informatie over het toedienen van het medicijn");//set your own text here.
                    topLabel.setText("iets");//set your own text here.
                    secondBottomLabel.setText("ook iets ");//set your own text here.
                    bottomLabel.setText("Jajaja niet verwacht he");//set your own text here.
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    verifyCheck.setVisibility(View.VISIBLE);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void deviceActivity(View view)
    {
        Intent deviceActivity = new Intent(this, com.example.sf.backend.api.DeviceList.class);
        startActivity(deviceActivity);
    }
    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(scannerActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private void Disconnect() {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout
    }

    private JSONObject parseJsonBarcode(String s) {
        JSONObject object = new JSONObject();
        try {
            object.put("barcode",s);
        } catch (Exception e){
            System.out.println(e);
        }
        return object;
    }

    private void requestBaxterItem(String barcode) {
        try{
            btSocket.getOutputStream().write(barcode.getBytes());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String waitForInput(InputStream inputStream) {
        ArrayList<Integer> input = new ArrayList<Integer>();
        try {
//            InputStream inputStream = mConnection.openInputStream();
            while (true) {
                while (inputStream.available() > 0) {
                    System.out.println("Getting input");
                    input.add(inputStream.read());
                }

                if (!input.isEmpty()) {

                    String inputstring = "";
                    for (int i = 0; i < input.size(); i++) {

                        inputstring += (char) (int) input.get(i);
                    }
                    System.out.println("input: " + inputstring);
                    return inputstring;
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return "Error in inputstream";
    }

    private void requestRoundInfo() {
        try {
            btSocket.getOutputStream().write("startRound".getBytes());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
