package com.hackathon.iot.data;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface DataPointRepository extends CrudRepository<DataPoint, Date> {
}
