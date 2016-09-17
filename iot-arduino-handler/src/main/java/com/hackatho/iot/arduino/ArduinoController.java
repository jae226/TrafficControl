package com.hackatho.iot.arduino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin
@RequestMapping("/cycle-ratio")
public class ArduinoController {

    ArduinoService arduinoService;

    @Autowired
    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @RequestMapping(method = POST)
    public CycleRatio calculateNewCycleRatio(@RequestBody CycleData cycleData) {
        return arduinoService.processCycleData(cycleData);
    }

}
