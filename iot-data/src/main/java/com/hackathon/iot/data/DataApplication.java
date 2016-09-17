package com.hackathon.iot.data;

import com.hackathon.iot.commons.DataPoint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EntityScan("com.hackathon.iot.commons")
public class DataApplication implements InitializingBean {

    @Autowired
    RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        repositoryRestConfiguration.exposeIdsFor(DataPoint.class);
    }
}
