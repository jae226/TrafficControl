package com.hackatho.iot.arduino;

public class CycleData {
    private long sendTime;

    private long intervalStart;
    private long intervalEnd;

    private long[] northSensorReadings;
    private long[] eastSensorReadings;

    private long[] northGreen;
    private long[] northRed;

    private long[] eastGreen;
    private long[] eastRed;

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(long intervalStart) {
        this.intervalStart = intervalStart;
    }

    public long getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(long intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public long[] getNorthSensorReadings() {
        return northSensorReadings;
    }

    public void setNorthSensorReadings(long[] northSensorReadings) {
        this.northSensorReadings = northSensorReadings;
    }

    public long[] getEastSensorReadings() {
        return eastSensorReadings;
    }

    public void setEastSensorReadings(long[] eastSensorReadings) {
        this.eastSensorReadings = eastSensorReadings;
    }

    public long[] getNorthGreen() {
        return northGreen;
    }

    public void setNorthGreen(long[] northGreen) {
        this.northGreen = northGreen;
    }

    public long[] getNorthRed() {
        return northRed;
    }

    public void setNorthRed(long[] northRed) {
        this.northRed = northRed;
    }

    public long[] getEastGreen() {
        return eastGreen;
    }

    public void setEastGreen(long[] eastGreen) {
        this.eastGreen = eastGreen;
    }

    public long[] getEastRed() {
        return eastRed;
    }

    public void setEastRed(long[] eastRed) {
        this.eastRed = eastRed;
    }
}
