package com.hackathon.iot.arduino;

import com.hackathon.iot.commons.DataPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@Service
public class ArduinoService {

    private RestTemplate restTemplate;

    @Autowired
    public ArduinoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CycleRatio processCycleData(CycleData cycleData) {
        int numNorth = cycleData.getNorthSensorReadings().length;
        int numEast = cycleData.getEastSensorReadings().length;

        long actualEndTime = getActualEndTime(cycleData.getIntervalEnd(), cycleData.getSendTime(), new Date().getTime());

        long waitTimeNorth = getWaitTime(cycleData.getNorthSensorReadings(),
                cycleData.getNorthGreen(), cycleData.getNorthRed(), cycleData.getIntervalEnd());

        long waitTimeEast = getWaitTime(cycleData.getEastSensorReadings(),
                cycleData.getEastGreen(), cycleData.getEastRed(), cycleData.getIntervalEnd());

        DataPoint dp = new DataPoint(actualEndTime, waitTimeNorth, waitTimeEast, numNorth, numEast);

        persistDataPoint(dp);

        double newNorthPercentage = calculateNorthPercentage(numNorth, numEast);
        double newEastPercentage = 1 - newNorthPercentage;

        return new CycleRatio(newNorthPercentage, newEastPercentage);
    }

    private long getActualEndTime(long endTime, long sendTime, long currentTime) {
        long offset = (sendTime - endTime);
        return currentTime - offset;
    }

    private long getWaitTime(long[] arrivalTimes, long greenStart, long redStart, long intervalEnd) {
        long sum = 0;
        for (long arrival : arrivalTimes) {
            if (redStart > greenStart && arrival >= redStart) {
                sum += intervalEnd - arrival;
            } else if (greenStart > redStart && arrival < greenStart) {
                sum += greenStart - arrival;
            }
        }
        return sum;
    }

    private void persistDataPoint(DataPoint dp) {
        URI uri = UriComponentsBuilder.fromUriString("https://iot-data.run.aws-usw02-pr.ice.predix.io")
                .path("/dataPoints").build().toUri();

        restTemplate.postForEntity(uri, dp, DataPoint.class);
    }

    private double calculateNorthPercentage(int numCarsNorth, int numCarsEast) {
        return 1 / (1 + (numCarsEast / numCarsNorth));
    }
}
