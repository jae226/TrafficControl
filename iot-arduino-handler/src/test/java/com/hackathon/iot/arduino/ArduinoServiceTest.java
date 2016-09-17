package com.hackathon.iot.arduino;

import com.hackathon.iot.commons.DataPoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArduinoServiceTest {

    @Mock
    RestTemplate restTemplate;
    private ArduinoService arduinoService;

    @Before
    public void setup() {
        initMocks(this);

        arduinoService = new ArduinoService(restTemplate);
    }

    @Test
    public void testGetActualEndTime() {
        long endTime = 60000;
        long sendTime = 62000;
        long currentTime = new Date().getTime();

        long actualTime = ReflectionTestUtils.invokeMethod(arduinoService,
                "getActualEndTime", endTime, sendTime, currentTime);

        assertThat(actualTime, is(currentTime - (sendTime - endTime)));
    }

    @Test
    public void getWaitTimeCalculatesWaitTimeAccuratelyWhenRedStartsAfterGreen() {
        long[] arrivalTimes = new long[]{10000, 12000, 15000, 35000, 45000, 50000};
        long greenStart = 0;
        long redStart = 30000;
        long intervalEnd = 60000;

        long totalWaitTime = ReflectionTestUtils.invokeMethod(arduinoService,
                "getWaitTime", arrivalTimes, greenStart, redStart, intervalEnd);

        assertThat(totalWaitTime, is(50000L));
    }

    @Test
    public void getWaitTimeCalculatesWaitTimeAccuratelyWhenGreenStartsAfterRed() {
        long[] arrivalTimes = new long[]{10000, 12000, 15000, 35000, 45000, 50000};
        long greenStart = 30000;
        long redStart = 0;
        long intervalEnd = 60000;

        long totalWaitTime = ReflectionTestUtils.invokeMethod(arduinoService,
                "getWaitTime", arrivalTimes, greenStart, redStart, intervalEnd);

        assertThat(totalWaitTime, is(53000L));
    }

    @Test
    public void persistDataPointPostsToDataEndpoint() {
        ArgumentCaptor<URI> uriArgumentCaptor = ArgumentCaptor.forClass(URI.class);
        DataPoint dp = new DataPoint(1234L, 1234L, 1234L, 1, 1);

        ReflectionTestUtils.invokeMethod(arduinoService, "persistDataPoint", dp);

        verify(restTemplate).postForEntity(uriArgumentCaptor.capture(), same(dp), same(DataPoint.class));

        assertThat(uriArgumentCaptor.getValue().toString(), is("https://iot-data.run.aws-usw02-pr.ice.predix.io/dataPoints"));
    }

}