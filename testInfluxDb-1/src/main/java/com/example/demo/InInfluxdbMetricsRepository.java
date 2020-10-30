package com.example.demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("inInfluxdbMetricsRepository")
public class InInfluxdbMetricsRepository implements MetricsRepository<MetricEntity> {

    @Autowired
    public InfluxDB influxDB;

    @Override
    public synchronized void save(MetricEntity metric) {
       //Omit code, too long, refer to memory writing, refer to saveAll Here is a single insert
    }

    @Override
    public synchronized void saveAll(Iterable<MetricEntity> metrics) {
        if (metrics == null) {
            return;
        }
        BatchPoints batchPoints = BatchPoints.builder()
                .tag("async", "true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        metrics.forEach(metric->{
            Point point = Point
                    .measurement("sentinelInfo")
                    //Use subtle here, and use nanoseconds if you still have override data, so that time and tag are unique
                    .time(System.currentTimeMillis(), TimeUnit.MICROSECONDS)
                    .tag("app",metric.getApp())//tag Data Walk Indexing
                    .addField("gmtCreate", metric.getGmtCreate().getTime())
                    .addField("gmtModified", metric.getGmtModified().getTime())
                    .addField("timestamp", metric.getTimestamp().getTime())
                    .addField("resource", metric.getResource())
                    .addField("passQps", metric.getPassQps())
                    .addField("successQps", metric.getSuccessQps())
                    .addField("blockQps", metric.getBlockQps())
                    .addField("exceptionQps", metric.getExceptionQps())
                    .addField("rt", metric.getRt())
                    .addField("count", metric.getCount())
                    .addField("resourceCode", metric.getResourceCode())
                    .build();
            batchPoints.point(point);
        });
        //Batch Insert
        influxDB.write(batchPoints);
    }

    @Override
    public synchronized List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
		return null;
       //Omit code, too long, reference memory writing
    }

    @Override 
    public synchronized List<String> listResourcesOfApp(String app) {
		return null;
       //Omit code, too long, reference memory writing
    }
}