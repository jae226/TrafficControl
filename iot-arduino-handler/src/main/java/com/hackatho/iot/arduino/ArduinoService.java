package com.hackatho.iot.arduino;

import org.springframework.stereotype.Service;

@Service
public class ArduinoService {

    public CycleRatio processCycleData(CycleData cycleData) {
        return new CycleRatio(0.5, 0.5);
    }
}
