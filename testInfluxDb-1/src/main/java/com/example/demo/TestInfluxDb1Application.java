package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestInfluxDb1Application {
	@Autowired
	@Qualifier("inInfluxdbMetricsRepository")
	private MetricsRepository<MetricEntity> metricStore;
	public static void main(String[] args) {
		SpringApplication.run(TestInfluxDb1Application.class, args);
	}

}
