
#include "BluetoothSerial.h"

BluetoothSerial SerialBT;


void setup() {
  Serial.begin(115200);
  SerialBT.begin("Mercedinho 1935"); //Bluetooth device name
}

void loop() {
  if (SerialBT.available()) {
    String message = SerialBT.readString();
    SerialBT.println(message);
  }
  delay(20);
}
