package com.hackathon.iot.arduino;

public class CycleRatio {
    private double northPercent;
    private double eastPercent;

    public CycleRatio(double northPercent, double eastPercent) {
        this.northPercent = northPercent;
        this.eastPercent = eastPercent;
    }

    public double getNorthPercent() {
        return northPercent;
    }

    public void setNorthPercent(double northPercent) {
        this.northPercent = northPercent;
    }

    public double getEastPercent() {
        return eastPercent;
    }

    public void setEastPercent(double eastPercent) {
        this.eastPercent = eastPercent;
    }
}
