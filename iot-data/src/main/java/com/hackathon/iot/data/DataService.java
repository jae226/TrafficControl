package com.hackathon.iot.data;

import com.hackathon.iot.commons.DataPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DataPoint> getDataPoints() {
        return jdbcTemplate.query("SELECT * FROM data_point", new Object[]{}, new BeanPropertyRowMapper(DataPoint.class));
    }
}
