#include "Arduino.h"
#include <SoftwareSerial.h>
#include <Wire.h>
#include <SPI.h>

const byte rxPin = 8;
const byte txPin = 9;
SoftwareSerial BTSerial(rxPin, txPin); // RX tX

#define UVPIN A0 // what pin we're connected to

int uvRaw;

void setup() {
  // put your setup code here, to run once:
  pinMode(rxPin, INPUT);
  pinMode(txPin, OUTPUT);
  BTSerial.begin(9600);
  Serial.begin(9600);
  BTSerial.println("hello1");
  Serial.println("Test UV Meter");
  //delay(500);
}

//String messageBuffer = "";
//String message = "";

void loop() {
  uvRaw = analogRead(UVPIN);
  //Serial.println("UV Index");

  if (uvRaw <= 2) {
    BTSerial.print(uvRaw);
    BTSerial.println(" - LOW");
  }

  else if (uvRaw > 2 && uvRaw <= 5) {
    BTSerial.print(uvRaw);
    BTSerial.println(" - MODERATE");
  }

  else if (uvRaw > 5 && uvRaw <= 7) {
    BTSerial.print(uvRaw);
    BTSerial.println(" - HIGH");
  }

  else if (uvRaw > 7 && uvRaw <= 10 ) {
    BTSerial.print(uvRaw);
    BTSerial.println(" - VERY HIGH");
  }

  else {
    BTSerial.print(uvRaw);
    BTSerial.println(" - EXTREME");
  }

/*
  while (BTSerial.available() > 0) {
    Serial.print(BTSerial.read());
    char data = (char) BTSerial.read();
    messageBuffer += data;
    if (data == ";") {
      message = messageBuffer;
      messageBuffer = "";
      Serial.print(message); // send to serial monitor
      message = "You sent " + message;
      BTSerial.print(message); // send back to bluetooth terminal
    }
  }
  */

  delay(500);
}
