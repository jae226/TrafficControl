package com.hackathon.iot.arduino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin
@RequestMapping("/cycle-ratio")
public class ArduinoController {

    private ArduinoService arduinoService;

    @Autowired
    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @RequestMapping(method = POST)
    public CycleRatio calculateNewCycleRatio(@RequestBody CycleData cycleData) {
        return arduinoService.processCycleData(cycleData);
    }

}
