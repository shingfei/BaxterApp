package com.example.sf.backend.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import com.example.sf.testapp.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class networkControl extends AppCompatActivity {
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
        setContentView(R.layout.activity_network_control);

        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        //view of the ledControl layout
        setContentView(R.layout.activity_network_control);

        //call the widgtes
        btnGetRound = (Button)findViewById(R.id.button);
        btnDis = (Button)findViewById(R.id.button2);


        ConnectBT connector = new ConnectBT();
        connector.onPreExecute();
        connector.onPostExecute(connector.doInBackground());

        btnGetRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRoundInfo();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //scan functie hier
                requestBaxterItem(new String());
                try {
                    StringBaxterItemParser parser = new StringBaxterItemParser();
                    BaxterItem baxterItem = parser.jsonParser(waitForInput(btSocket.getInputStream()));
                    //display baxter item
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect(); //close connection
            }
        });
    }
//////
    private class ConnectBT extends AsyncTask<Void, Void, Void>  {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(networkControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
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
