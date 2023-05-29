package com.example.healthfuelness;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ArduinoActivity extends AppCompatActivity {

    private Spinner spinnerPairedDevices;
    private Button buttonFindPairedDevices, buttonConnect, buttonReceivedData;
    private ListView lstvw, fromHC;

    private ArrayAdapter arrayAdapter, arrayAdapterPairedDevices, arrayAdapterData;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice HC05Device;
    private BluetoothSocket BTSocket;

    private Context context = this;
    private ArrayList listName, listMacAddress;
    private int HC05Index;
    private boolean bluetoothDeviceConnected = false;
    Set<BluetoothDevice> pairedDevices;

    // SPP UUID for any HC-05 bluetooth adapter
    public static final UUID HC05_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino);

        spinnerPairedDevices = findViewById(R.id.spinner_paired_devices);
        buttonFindPairedDevices = findViewById(R.id.button_find_paired_devices);
        buttonConnect = findViewById(R.id.button_connect);
        buttonReceivedData = findViewById(R.id.button_received_data);

        buttonFindPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
                } else if (!bluetoothAdapter.isEnabled()) {
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);
                    Toast.makeText(getApplicationContext(), "Please turn ON Bluetooth", Toast.LENGTH_SHORT).show();
                } else {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
                        requestPermission(Manifest.permission.BLUETOOTH);
                        return;
                    }
                    pairedDevices = bluetoothAdapter.getBondedDevices();
                    ArrayList list = new ArrayList();
                    listName = new ArrayList();
                    listMacAddress = new ArrayList();
                    list.add("Select");
                    listName.add("Select");
                    listMacAddress.add("0");
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            String deviceName = device.getName();
                            String macAddress = device.getAddress();
                            list.add("Name: " + deviceName + " MAC Address: " + macAddress);
                            listName.add(deviceName);
                            listMacAddress.add(macAddress);
                        }
                        lstvw = (ListView) findViewById(R.id.deviceList);
                        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                        lstvw.setAdapter(arrayAdapter);

                        //populate spinner with bluetooth paired devices
                        arrayAdapterPairedDevices = new ArrayAdapter<String>(getApplicationContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, listName);
                        arrayAdapterPairedDevices.setDropDownViewResource(com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item);
                        spinnerPairedDevices.setAdapter(arrayAdapterPairedDevices);
                    }

                }
            }
        });

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothDeviceConnected == false) {
                    if (spinnerPairedDevices.getSelectedItemPosition() == 0) {
                        Toast.makeText(getApplicationContext(), "Please select bluetooth device", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
                        requestPermission(Manifest.permission.BLUETOOTH);
                        return;
                    }
                    String selectedDevice = spinnerPairedDevices.getSelectedItem().toString();
                    if (!selectedDevice.equals("HC-05")) {
                        Toast.makeText(getApplicationContext(), "Please select  your HC-05", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (BluetoothDevice device : pairedDevices) {
                        if (selectedDevice.equals(device.getName())) {
                            HC05Device = device;
                            try {
                                BTSocket = HC05Device.createRfcommSocketToServiceRecord(HC05_UUID);
                                Toast.makeText(getApplicationContext(), "Connecting to device", Toast.LENGTH_SHORT).show();
                                BTSocket.connect();
                                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                                buttonConnect.setText("Disconnect");
                                bluetoothDeviceConnected = true;
                            } catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                                bluetoothDeviceConnected = false;
                            }
                        }
                    }
                } else {
                    try {
                        BTSocket.close();
                        Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_SHORT).show();
                        buttonConnect.setText("Connect");
                        bluetoothDeviceConnected = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                        bluetoothDeviceConnected = true;
                    }
                }
            }
        });

        buttonReceivedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!bluetoothAdapter.isEnabled()) {
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);
                    Toast.makeText(getApplicationContext(), "Please turn ON Bluetooth", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
                    requestPermission(Manifest.permission.BLUETOOTH);
                    return;
                }
                if (spinnerPairedDevices.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Please select bluetooth device", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (BTSocket == null) {
                    Toast.makeText(getApplicationContext(), "Please connect to your bluetooth device", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String result = readRawData(BTSocket.getInputStream());
                    ArrayList list2 = new ArrayList();
                    list2.add(result);
                    fromHC = findViewById(R.id.fromHC);
                    arrayAdapterData = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list2);
                    fromHC.setAdapter(arrayAdapterData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                

            }
        });




    }

    private void requestPermission(String whatPermission) {
        final String[] permissions = new String[]{whatPermission};
        ActivityCompat.requestPermissions(this, permissions, 1);
        return;
    }

    public String readRawData(InputStream in) throws IOException {
        byte[] receivedData = new byte[100];
        // read until ‘;’ arrives OR end of stream reached;
        int i =0;
        while(true)
        {
            receivedData[i] = (byte) in.read();
            if((receivedData[i] == -1) || (receivedData[i] == ';')) // -1 if the end of the stream is reached
            {
                break;
            }
            i++;
        }
        String receivedDataAsString = new String(receivedData);
        return receivedDataAsString;
    }


}
