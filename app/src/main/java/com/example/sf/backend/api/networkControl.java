package com.example.sf.backend.api;

        import android.os.Handler;
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
    private Round round = new Round(); //contains the array with baxterItems
    Button btnGetRound, btnDis, btnScan;
    TextView patientText,medicijnText,patientNaamText,medicijnNaamText;
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

        //view of the network control layout
        setContentView(R.layout.activity_network_control);

        //call the widgtes
        btnGetRound = (Button)findViewById(R.id.button);
        btnDis = (Button)findViewById(R.id.button2);
        btnScan = (Button)findViewById(R.id.button3);
        patientText = (TextView)findViewById(R.id.textView);
        medicijnText = (TextView)findViewById(R.id.textView2);
        patientNaamText = (TextView)findViewById(R.id.textView5);
        medicijnNaamText = (TextView)findViewById(R.id.textView6);

        //connects the local device to the bleutooth device
        ConnectBT connector = new ConnectBT();
        connector.onPreExecute();
        connector.onPostExecute(connector.doInBackground());

        btnGetRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRoundInfo();
                //updateTextFieldsHandler.post(updateTextFields);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                --goes to scanfunction.
                 */
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                --self explanatory.
                 */

                Disconnect();
            }
        });
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  {

        /*
        --connects to selected device in the background.
         */

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

    public void Disconnect() {

        /*
        --self explanatory
         */

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

    private void requestRoundInfo() {

        /*
        --sends 'startRound' to the client.
        --catch response of client in an string.
        --parse it into an array of Baxteritems.
        --sets the roundarray in round object for easy access.
         */

        String inputString;

        try {
            btSocket.getOutputStream().write("startRound".getBytes());
            inputString = waitForInput(btSocket.getInputStream());
            StringBaxterItemParser parser = new StringBaxterItemParser();

            BaxterItem[] roundArray = new BaxterItem[parser.getLengthArray(inputString)];

            for (int i = 0; i < roundArray.length; i++) {
                roundArray[i] = parser.parseBaxterItem(inputString, i);
            }
            round.setRoundArray(roundArray);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String waitForInput(InputStream inputStream) {

        /*
        --returns a string with response of the client.
         */

        ArrayList<Integer> input = new ArrayList<>();

        try {
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
                    btSocket.getOutputStream().write("ready".getBytes());
                    System.out.println("input: " + inputstring);

                    return inputstring;
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return "Error in inputstream";
    }



    /*

    //used to change textviews

    private final Runnable updateTextFields = new Runnable() {
        public void run() {

            System.out.println(patient[1]);
            System.out.println(patient[0]);
            medicijnNaamText.setText(patient[0]);
            patientNaamText.setText(patient[1]);
        }
    };

    private final Handler updateTextFieldsHandler = new Handler();

    */
}
