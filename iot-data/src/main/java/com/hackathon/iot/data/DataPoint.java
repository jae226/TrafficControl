package com.hackathon.iot.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class DataPoint {

    @Id
    private Date timestamp;

    private int numNorthSouth;

    private int numEastWest;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getNumNorthSouth() {
        return numNorthSouth;
    }

    public void setNumNorthSouth(int numNorthSouth) {
        this.numNorthSouth = numNorthSouth;
    }

    public int getNumEastWest() {
        return numEastWest;
    }

    public void setNumEastWest(int numEastWest) {
        this.numEastWest = numEastWest;
    }
}
