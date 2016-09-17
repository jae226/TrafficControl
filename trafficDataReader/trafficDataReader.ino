#include <SimpleTimer.h>

#include <ESP8266WiFi.h>
#include <ESP8266WiFiAP.h>
#include <ESP8266WiFiGeneric.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266WiFiScan.h>
#include <ESP8266WiFiSTA.h>
#include <ESP8266WiFiType.h>
#include <WiFiClient.h>
#include <WiFiClientSecure.h>
#include <WiFiServer.h>
#include <WiFiUdp.h>
#include <Time.h>


#define GREENLEDNORTH 12
#define REDLEDNORTH 14

#define SENSORPINNORTH 2

SimpleTimer timer;

long trafficLightInterval = 20000;

const char* ssid = "Internet";
const char* password = "Acc3ssGr@nted";

const char* host = "iot-arduino-handler.run.aws-usw02-pr.ice.predix.io";
char* URL = "/cycle-ratio";
const char* fingerprint ="81 02 28 c7 b6 2a 27 8e 27 f0 01 83 77 3a c5 c9 23 3b e0 02";

/*long northSensorReadings[1024];
int endOfNorthReadings = 0;
long eastSensorReadings[1024];
int endOfEastReadings = 0;*/

char types[4096]; //either E=East or N=North
int endOfTypes = 0;
char carTimes[4096];
int endOfCarTimes = 0;

long northGreenTime;

long northRedTime;

long EastGreenTime;

long eastRedTime;

long startInterval;
long endInterval;
long sendRequestTime;

int sensorState = 0;
int lastState = 0;

void setup() {
  //pinMode(0, OUTPUT);
  pinMode(GREENLEDNORTH, OUTPUT);
  pinMode(SENSORPINNORTH, INPUT);
  digitalWrite(SENSORPINNORTH, HIGH);

  Serial.begin(115200);
  delay(100);

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
 
  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());


  startInterval = millis();
  Serial.print("Current time: ");
  Serial.println(startInterval);

  timer.setInterval(trafficLightInterval, communicateWithServer);
}

void loop() {

  timer.run();
  
  sensorState = digitalRead(SENSORPINNORTH);
  
  if (sensorState == LOW) {
    digitalWrite(GREENLEDNORTH, HIGH);
  } else {
    digitalWrite(GREENLEDNORTH, LOW);
  }
  
  if (sensorState && !lastState) {
    Serial.println(millis());
  }
  /*if (!sensorState && lastState) {
    Serial.println("Broken");
    Serial.println(millis());
  }*/
  lastState = sensorState;
}

void communicateWithServer() {
  WiFiClientSecure client;
  const int httpPort = 443;
  if (client.connect(host, httpPort)) {

    if (client.verify(fingerprint, host)) {
      Serial.println("ssl cert matches");
    } else {
      Serial.println("ssl cert mismatch");
    }
      
    String postData="{\"eastGreen\": 0, \"eastRed\": 0, \"eastSensorReadings\": [0], \"intervalEnd\": 0, \"intervalStart\": 0, \"northGreen\": 0, \"northRed\": 0, \"northSensorReadings\": [0], \"sendTime\": 0}";
  
    client.print("POST ");
    client.print(URL);
    client.println(" HTTP/1.1");
    client.print("Host: ");
    client.println(host);
    //client.println("User-Agent: ArduinoIoT/1.0");
    client.println("Connection: close");
    client.println("Content-Type: application/json;");
    client.print("Content-Length: ");
    client.println(postData.length());
    client.println();
    client.println(postData);
  
    delay(500);
  
    while(client.available()) {
      String line = client.readStringUntil('\r');
      Serial.println(line);
    }

  
  } else {
    Serial.println("connection failed...");
  }
  delay(500);
}

