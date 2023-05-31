package com.example.healthfuelness;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
    private TextView temperatureStatus, temperatureValue;
    private TextView humidityStatus, humidityValue;
    private TextView uvLevelStatus, uvLevelValue;
    private TextView airQualityStatus;
    private TextView tolueneStatus, tolueneValue;
    private TextView acetoneStatus, acetoneValue;
    private TextView ammoniaStatus, ammoniaValue;
    private TextView alcoholStatus, alcoholValue;
    private TextView hydrogenStatus, hydrogenValue;
    private TextView dioxideCarbonStatus, dioxideCarbonValue;
    private TextView connectivityStatus;

    private Data receivedData = null;

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
        temperatureStatus = findViewById(R.id.tv_temperature_status);
        temperatureValue = findViewById(R.id.tv_temperature);
        humidityStatus = findViewById(R.id.tv_humidity_status);
        humidityValue = findViewById(R.id.tv_humidity);
        uvLevelStatus = findViewById(R.id.tv_uv_level_status);
        uvLevelValue = findViewById(R.id.tv_uv_level);
        airQualityStatus = findViewById(R.id.tv_air_quality_status);
        tolueneStatus = findViewById(R.id.tv_toluene_status);
        tolueneValue = findViewById(R.id.tv_toluene);
        acetoneStatus = findViewById(R.id.tv_acetone_status);
        acetoneValue = findViewById(R.id.tv_acetone);
        ammoniaStatus = findViewById(R.id.tv_ammonia_status);
        ammoniaValue = findViewById(R.id.tv_ammonia);
        alcoholStatus = findViewById(R.id.tv_alcohol_status);
        alcoholValue = findViewById(R.id.tv_alcohol);
        hydrogenStatus = findViewById(R.id.tv_hydrogen_status);
        hydrogenValue = findViewById(R.id.tv_hydrogen);
        dioxideCarbonStatus = findViewById(R.id.tv_dioxide_carbon_status);
        dioxideCarbonValue = findViewById(R.id.tv_dioxide_carbon);
        connectivityStatus = findViewById(R.id.tv_connectivity_status);

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
                                connectivityStatus.setText("Connected");
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
                        Toast.makeText(getApplicationContext(), "Disconnecting", Toast.LENGTH_SHORT).show();
                        connectivityStatus.setText("Disconnected");
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
                    //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    String[] dates = result.split("\\s");//splits the string based on whitespace
                    //using java foreach loop to print elements of string array
                    if (receivedData == null) {
                        Data aux = new Data(dates[0], dates[1], dates[2], dates[3], dates[4], dates[5], dates[6], dates[7], dates[8], dates[9]);
                        receivedData = aux;
                    } else {
                        receivedData.setTemperature(dates[0]);
                        receivedData.setHumidity(dates[1]);
                        receivedData.setUvLevel(dates[2]);
                        receivedData.setAirQuality(dates[3]);
                        receivedData.setToluene(dates[4]);
                        receivedData.setAcetone(dates[5]);
                        receivedData.setAmmonia(dates[6]);
                        receivedData.setAlcohol(dates[7]);
                        receivedData.setHydrogen(dates[8]);
                        receivedData.setDioxideCarbon(dates[9]);
                    }
                    temperatureValue.setText(receivedData.getTemperature());
                    humidityValue.setText(receivedData.getHumidity());
                    String uvLevel = receivedData.getUvLevel();
                    uvLevelValue.setText(uvLevel);
                    if (uvLevel.equals("0")) {
                        setStatus(uvLevelStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#636263")));
                    }
                    if ((uvLevel.equals("1")) || (uvLevel.equals("2"))){
                        setStatus(uvLevelStatus, "Scazut", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }
                    if ((uvLevel == "3") || (uvLevel == "4") || (uvLevel == "5") || (uvLevel == "6") || (uvLevel == "7")){
                        setStatus(uvLevelStatus, "Moderat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                    }
                    if ((uvLevel == "8") || (receivedData.getUvLevel() == "9") || (uvLevel == "10")){
                        setStatus(uvLevelStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                    } else {
                        setStatus(uvLevelStatus, "Extrem", ColorStateList.valueOf(Color.parseColor("#673AB7")));
                    }
                    airQualityStatus.setText(receivedData.getAirQuality());
                    int airQuality = 0;

                    String toluene = receivedData.getToluene();
                    tolueneValue.setText(toluene);
                    if (toluene.compareTo("100") > 0) {
                        setStatus(tolueneStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 2;
                    } else if (toluene.compareTo("50") > 0) {
                        setStatus(tolueneStatus, "Moderat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 1;
                    } else {
                        setStatus(tolueneStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }

                    String acetone = receivedData.getAcetone();
                    acetoneValue.setText(acetone);
                    if (toluene.compareTo("500") > 0) {
                        setStatus(acetoneStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 2;
                    } else {
                        setStatus(acetoneStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }

                    String ammonia = receivedData.getAmmonia();
                    ammoniaValue.setText(ammonia);
                    if (ammonia.compareTo("50") > 0) {
                        setStatus(ammoniaStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 2;
                    } else if (ammonia.compareTo("20") > 0) {
                        setStatus(ammoniaStatus, "Moderat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 1;
                    } else {
                        setStatus(ammoniaStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }

                    String alcohol = receivedData.getAlcohol();
                    alcoholValue.setText(alcohol);
                    if (alcohol.compareTo("800") > 0) {
                        setStatus(alcoholStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 2;
                    } else if (alcohol.compareTo("400") > 0) {
                        setStatus(alcoholStatus, "Moderat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 1;
                    } else {
                        setStatus(alcoholStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }

                    String hydrogen = receivedData.getHydrogen();
                    hydrogenValue.setText(hydrogen);
                    if (hydrogen.compareTo("4100") > 0) {
                        setStatus(hydrogenStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 2;
                    } else {
                        setStatus(hydrogenStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }

                    String dioxideCarbon = receivedData.getDioxideCarbon();
                    dioxideCarbonValue.setText(dioxideCarbon);
                    if (dioxideCarbon.compareTo("5000") > 0) {
                        setStatus(dioxideCarbonStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                        airQuality = 2;
                    } else {
                        setStatus(dioxideCarbonStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }

                    if (airQuality == 0) {
                        setStatus(airQualityStatus, "Perfect", ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    } else if (airQuality == 1) {
                        setStatus(airQualityStatus, "Moderat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                    }else {
                        setStatus(airQualityStatus, "Ridicat", ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                    }
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

    private String readRawData(InputStream in) throws IOException {
        byte[] receivedDataAux = new byte[100];
        // read until ‘;’ arrives OR end of stream reached;
        int i =0;
        while(true)
        {
            receivedDataAux[i] = (byte) in.read();
            if((receivedDataAux[i] == -1) || (receivedDataAux[i] == ';')) // -1 if the end of the stream is reached
            {
                break;
            }
            i++;
        }
        String receivedDataAsString = new String(receivedDataAux);
        return receivedDataAsString;
    }

    private void setStatus (TextView tvStatus, String value, ColorStateList color) {
        tvStatus.setText(value);
        tvStatus.setTextColor(color);
    }


}
