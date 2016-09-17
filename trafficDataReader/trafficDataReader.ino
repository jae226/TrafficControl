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
#define GREENLEDEAST 15
#define REDLEDNORTH 14
#define REDLEDEAST 13
#define INTERVAL 20000

#define SENSORPINNORTH 2
#define SENSORPINEAST 16

SimpleTimer timer;

boolean lightBool = true;

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

long eastGreenTime;

long eastRedTime;

long startInterval;
long endInterval;
long sendRequestTime;

double greenNorthRatio;
double redNorthRatio;
long timeToBeGreenNorth = INTERVAL/2;
long timeToBeRedNorth = INTERVAL/2;

int sensorState = 0;
int lastState = 0;

void setup() {
  //pinMode(0, OUTPUT);
  pinMode(GREENLEDNORTH, OUTPUT);
  pinMode(REDLEDNORTH, OUTPUT);
  pinMode(SENSORPINNORTH, INPUT);
  digitalWrite(SENSORPINNORTH, HIGH);
  digitalWrite(GREENLEDNORTH, HIGH);

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
  timer.setTimeout(timeToBeGreenNorth, changeLightColorToRedNorth);
}

void loop() {

  timer.run();
  
  sensorState = digitalRead(SENSORPINNORTH);
  
  /*if (sensorState == LOW) {
    digitalWrite(GREENLEDNORTH, HIGH);
  } else {
    digitalWrite(GREENLEDNORTH, LOW);
  }*/
  
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
  Serial.println("Trying to connect...");
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
  
    delay(100);
  
    while(client.available()) {
      String line = client.readStringUntil('\r');
      Serial.println(line);
      line.trim();
      if (line.startsWith("{\"northPercent")) {
        int locStr = line.indexOf(",\"eastPercent");
        greenNorthRatio = line.substring(16, locStr).toFloat();
        redNorthRatio = line.substring(34, line.length()-1).toFloat();
        Serial.println(String(greenNorthRatio));
        Serial.println(String(redNorthRatio));
        
      }
    }
    timeToBeGreenNorth = INTERVAL * greenNorthRatio;
    timeToBeRedNorth = INTERVAL * redNorthRatio;
  
  } else {
    Serial.println("connection failed...");
  }
}

void changeLightColorToRedNorth() {
  digitalWrite(GREENLEDNORTH, LOW);
  digitalWrite(REDLEDNORTH, HIGH);
  timer.setTimeout(timeToBeRedNorth, changeLightColorToGreenNorth);
}

void changeLightColorToGreenNorth() {
  digitalWrite(GREENLEDNORTH, HIGH);
  digitalWrite(REDLEDNORTH, LOW);
  timer.setTimeout(timeToBeGreenNorth, changeLightColorToRedNorth);
}

