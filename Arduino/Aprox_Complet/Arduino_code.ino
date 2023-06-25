#include "Arduino.h"
#include <SoftwareSerial.h>
#include <Wire.h>
#include <SPI.h>
#include <DHT.h>;
#define USE_ARDUINO_INTERRUPTS true    // Set-up low-level interrupts for most acurate BPM maths.
#include <PulseSensorPlayground.h>
#include <ArduinoJson.h>
#include <SD.h>

//Bluetooth module
const byte rxPin = 8;
const byte txPin = 9;
SoftwareSerial BTSerial(rxPin, txPin); // RX tX

// Senzorul UV
#define UVPIN A0 // definim pinul analog A0 ca pin de intrare de la senzorul UV
int uvSensorValue, uvIndex; // variabilele folosite pentru citirea pinului UVPIN
float uvVoltage, uvVoltageInmV; // variabilele de tranformare din semnal analogin in volti si mili-volti

// Senzor de temperatura si umiditate
#define DHTPIN 2 // la ce pin suntem conectați
#define DHTTYPE DHT22 // DHT 22 (AM2302)
DHT dht(DHTPIN, DHTTYPE); // Inițializarea senzorul DHT pentru Arduino la 16 MHz
// Variabile
float humidity; //Stochează valoarea umidității
float temperature; //Stochează valoarea temperaturii

//Pulse sensor
const int PulseWire = 2;       // PulseSensor PURPLE WIRE connected to ANALOG PIN 2
const int LED13 = 13;          // The on-board Arduino LED, close to PIN 13.
int Threshold = 520;           // Determine which Signal to "count as a beat" and which to ignore.
                               // Use the "Getting Started Project" to fine-tune Threshold Value beyond default setting.
                               // Other
PulseSensorPlayground pulseSensor;  // Creates an instance of the PulseSensorPlayground object called "pulseSensor

//MQ-135 Gas Sensor
#define AQPIN A1 // what pin we're connected to
int aqSensorValue;
int R0 = 538;
int R2 = 1000;
float RS;
float aqVoltage;
float x1, x2;
float PPM_toluen, PPM_acetona, PPM_nh3, PPM_alcool, PPM_h2, PPM_co2;
float y1_toluen, y1_acetona, y1_nh3, y1_alcool, y1_h2, y1_co2;
float y2_toluen, y2_acetona, y2_nh3, y2_alcool, y2_h2, y2_co2;
float c1_toluen, c1_acetona, c1_nh3, c1_alcool, c1_h2, c1_co2;
float c2_toluen, c2_acetona, c2_nh3, c2_alcool, c2_h2, c2_co2;
int warning_toluen, warning_acetona, warning_nh3, warning_alcool, warning_h2, warning_co2;

void setup() {
  pinMode(rxPin, INPUT);
  pinMode(txPin, OUTPUT);
  BTSerial.begin(9600);
  Serial.begin(9600);
  dht.begin();
  
  // Configure the PulseSensor object, by assigning our variables to it.
  //pulseSensor.analogInput(PulseWire);  
  //pulseSensor.blinkOnPulse(LED13);       //auto-magically blink Arduino's LED with heartbeat.
  //pulseSensor.setThreshold(Threshold);  
  // Double-check the "pulseSensor" object was created and "began" seeing a signal.
  //pulseSensor.begin();

  

  delay(20);
}

void loop() {  
  Serial.print(millis()/1000.00);
  Serial.print(", ");
  //Temperatura și umiditatea
  //Citirea datelor și stocarea în variabilele hum și temp
  humidity = dht.readHumidity();
  temperature = dht.readTemperature();
  //Transmiterea valorile de temperatură și umiditate prin modulul bluetooth
  BTSerial.print(temperature);
  BTSerial.print(" ");
  BTSerial.print(humidity);
  BTSerial.print(" ");
  //Transmiterea valorile de temperatură și umiditate prin serial
  Serial.print(temperature);
  Serial.print(", ");
  Serial.print(humidity);
  Serial.print(", ");

  //UV Level
  //5 volts / 1024 units or, 0.0049 volts (4.9 mV) per unit -> 500mV/unit
  uvSensorValue = analogRead(UVPIN);
  // Convert the analog reading (which goes from 0 - 1023) to a voltage (0 - 5V):
  uvVoltage = uvSensorValue * (5.0 / 1023.0);
  uvVoltageInmV = uvVoltage * 1000;

  if (uvVoltageInmV < 50) {
    uvIndex = 0;
  } else if (uvVoltageInmV < 227) {
    uvIndex = 1;
  } else if (uvVoltageInmV < 318) {
    uvIndex = 2;
  } else if (uvVoltageInmV < 408) {
    uvIndex = 3;
  } else if (uvVoltageInmV < 503) {
    uvIndex = 4;
  } else if (uvVoltageInmV < 606) {
    uvIndex = 5;
  } else if (uvVoltageInmV < 696) {
    uvIndex = 6;
  } else if (uvVoltageInmV < 795) {
    uvIndex = 7;
  } else if (uvVoltageInmV < 881) {
    uvIndex = 8;
  } else if (uvVoltageInmV < 976) {
    uvIndex = 9;
  } else if (uvVoltageInmV < 1079) {
    uvIndex = 10;
  } else {
    uvIndex = 11;
  }
  BTSerial.print(uvIndex);
  BTSerial.print(" ");

  Serial.print(uvIndex);
  Serial.print(", ");
 
  //Air Quality  

  BTSerial.print("AirQuality");
  BTSerial.print(" ");
  
  aqSensorValue = analogRead(AQPIN);
  aqVoltage = aqSensorValue * (5.0 / 1023.0);
  RS = R2 * (1-aqVoltage);
  RS = RS/aqVoltage;

  x1 = 10;
  x2 = 100;

  //Toluen
  y1_toluen  = 0.7;
  y2_toluen  = 0.35;
  c1_toluen  = constanta1(x1, x2, y1_toluen, y2_toluen);
  c2_toluen  = constanta2(x1, x2, y1_toluen, y2_toluen);

  PPM_toluen = c2_toluen  + c1_toluen  *  (RS/R0);

  BTSerial.print(PPM_toluen);
  BTSerial.print(" ");

  Serial.print(PPM_toluen);
  Serial.print(", ");
  
  //Acetona
  y1_acetona  = 1.122;
  y2_acetona  = 0.446;
  c1_acetona  = constanta1(x1, x2, y1_acetona, y2_acetona);
  c2_acetona  = constanta2(x1, x2, y1_acetona, y2_acetona);

  PPM_acetona = c2_acetona  + c1_acetona  *  (RS/R0);

  BTSerial.print(PPM_acetona);
  BTSerial.print(" ");

  Serial.print(PPM_acetona);
  Serial.print(", ");
  
  //NH3
  y1_nh3  = 0.7;
  y2_nh3  = 0.25;
  c1_nh3  = constanta1(x1, x2, y1_nh3, y2_nh3);
  c2_nh3 = constanta2(x1, x2, y1_nh3, y2_nh3);
  
  PPM_nh3 = c2_nh3  + c1_nh3  *  (RS/R0);
 
  BTSerial.print(PPM_nh3);
  BTSerial.print(" ");

  Serial.print(PPM_nh3);
  Serial.print(", ");
 
  //Alcool
  y1_alcool  = 1.122;
  y2_alcool  = 0.977;
  c1_alcool  = constanta1(x1, x2, y1_alcool, y2_alcool);
  c2_alcool  = constanta2(x1, x2, y1_alcool, y2_alcool);
  
  PPM_alcool = c2_alcool  + c1_alcool  *  (RS/R0);
  
  BTSerial.print(PPM_alcool);
  BTSerial.print(" ");

  Serial.print(PPM_alcool);
  Serial.print(", ");
  
  //h2
  y1_h2  = 0.55;
  y2_h2  = 0.2;
  c1_h2  = constanta1(x1, x2, y1_h2, y2_h2);
  c2_h2  = constanta2(x1, x2, y1_h2, y2_h2);

  PPM_h2 = c2_h2  + c1_h2  *  (RS/R0);
  
  BTSerial.print(PPM_h2);
  BTSerial.print(" ");

  Serial.print(PPM_h2);
  Serial.print(", ");
 
  //co2
  y1_co2  = 1.122;
  y2_co2  = 1;
  c1_co2  = constanta1(x1, x2, y1_co2, y2_co2);
  c2_co2  = constanta2(x1, x2, y1_co2, y2_co2);

  PPM_co2 = c2_co2  + c1_co2  *  (RS/R0);
  
  BTSerial.print(PPM_co2);
  BTSerial.print(";");

  Serial.print(PPM_co2);
  Serial.print("\n");
  
  delay(500); 

  //delay(1); // timp de asteptare intre citiri pentru stabilitate


  //delay(2000);
  //Pulse
  /*
  int myBPM = pulseSensor.getBeatsPerMinute();  // Calls function on our pulseSensor object that returns BPM as an "int".
                                               // "myBPM" hold this BPM value now.
  //if (pulseSensor.sawStartOfBeat()) {            // Constantly test to see if "a beat happened".
    Serial.print("BPM: ");                        // Print phrase "BPM: "
    Serial.println(myBPM);                        // Print the value inside of myBPM.
  //}
  Serial.println("New BBM: ");
  Serial.println(analogRead(2));
  delay(10); 
  */
  

}

float constanta1(float x1, float x2, float y1, float y2) {
  float A = (y2 - y1)/(x2 - x1);
  float c1 = 1 / A;
  return c1;
}

float constanta2(float x1, float x2, float y1, float y2) {
  float A = (y2 - y1)/(x2 - x1);
  float c2 = x1 - y1 / A;
  return c2;
}
