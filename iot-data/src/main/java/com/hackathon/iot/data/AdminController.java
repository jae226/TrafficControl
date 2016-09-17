package com.hackathon.iot.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private DataPointRepository dataPointRepository;

    @Autowired
    public AdminController(DataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    @RequestMapping(value = "/deleteAll", method = POST)
    public void deleteAll() {
        dataPointRepository.deleteAll();
    }

}
