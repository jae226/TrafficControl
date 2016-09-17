package com.hackathon.iot.commons;

import javax.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class DataPoint {

    @Id
    private Date timestamp;

    @NotNull
    private long totalWaitTimeNorth;

    @NotNull
    private long totalWaitTimeEast;

    @NotNull
    private int totalCarsNorth;

    @NotNull
    private int totalCarsEast;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getTotalWaitTimeNorth() {
        return totalWaitTimeNorth;
    }

    public void setTotalWaitTimeNorth(long totalWaitTimeNorth) {
        this.totalWaitTimeNorth = totalWaitTimeNorth;
    }

    public long getTotalWaitTimeEast() {
        return totalWaitTimeEast;
    }

    public void setTotalWaitTimeEast(long totalWaitTimeEast) {
        this.totalWaitTimeEast = totalWaitTimeEast;
    }

    public int getTotalCarsNorth() {
        return totalCarsNorth;
    }

    public void setTotalCarsNorth(int totalCarsNorth) {
        this.totalCarsNorth = totalCarsNorth;
    }

    public int getTotalCarsEast() {
        return totalCarsEast;
    }

    public void setTotalCarsEast(int totalCarsEast) {
        this.totalCarsEast = totalCarsEast;
    }
}
