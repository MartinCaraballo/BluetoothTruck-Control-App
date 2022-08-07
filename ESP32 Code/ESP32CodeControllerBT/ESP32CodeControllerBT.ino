
#include "BluetoothSerial.h"

BluetoothSerial SerialBT;
int ledPin = 2;
String ledOnCommand = "<led-on>";
String ledOffCommand = "<led-off>";

void setup() {
  Serial.begin(115200);
  SerialBT.begin("Mercedinho 1935"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  pinMode(ledPin, OUTPUT);
}

void loop() {
  if (SerialBT.available()) {
    String message = SerialBT.readString();
    if (message == ledOnCommand) {
      LedOn();
    } else if (message == ledOffCommand) {
      LedOff();
    } else {
      SerialBT.println(message);
    }
  }
  delay(20);
}

void LedOn() {
  digitalWrite(ledPin, HIGH);
  SerialBT.println("led turned on");
}

void LedOff() {
  digitalWrite(ledPin, LOW);
  SerialBT.println("led turned off");
}
