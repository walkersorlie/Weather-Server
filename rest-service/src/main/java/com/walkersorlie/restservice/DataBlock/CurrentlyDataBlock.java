/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice.DataBlock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import static java.time.ZoneId.systemDefault;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Walker Sorlie
 */
@Document(collection = "collection_weather_currently")
public class CurrentlyDataBlock {

    @Id
    private final String id;
    private final long time;
    private final String summary;
    private final int nearestStormDistance;
    private final int nearestStormBearing;
    private final double precipIntensity;
    private final double precipProbability;
    private final double temperature;
    private final double apparentTemperature;
    private final double dewPoint;
    private final double humidity;
    private final double pressure;
    private final double windSpeed;
    private final double windGust;
    private final double windBearing;
    private final double cloudCover;
    private final double uvIndex;
    private final double visibility;
    private final double ozone;


    public CurrentlyDataBlock(String id, long time, String summary, int nearestStormDistance, int nearestStormBearing, 
            double precipIntensity, double precipProbability, double temperature, double apparentTemperature, 
            double dewPoint, double humidity, double pressure, double windSpeed, double windGust, 
            double windBearing, double cloudCover, double uvIndex, double visibility, double ozone) {

        this.id = id;
        this.time = time;
        this.summary = summary;
        this.nearestStormDistance = nearestStormDistance;
        this.nearestStormBearing = nearestStormBearing;
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.windBearing = windBearing;
        this.cloudCover = cloudCover;
        this.uvIndex = uvIndex;
        this.visibility = visibility;
        this.ozone = ozone;      
    }

    public String getId() {
        return this.id;
    }

    public long getTime() {
        return this.time;
    }

    public String getFormattedTime() {
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss (z)");
        
        Instant instant = Instant.ofEpochSecond(this.time);
        ZonedDateTime localTime = instant.atZone(ZoneId.systemDefault());

        return localTime.format(dateFormat);
    }

    public String getSummary() {
        return this.summary;
    }

    public int getNearestStormDistance() {
        return this.nearestStormDistance;
    }

    public int getNearestStormBearing() {
        return this.nearestStormBearing;
    }

    public double getPrecipIntensity() {
        return this.precipIntensity;
    }

    public double getPrecipProbability() {
        return this.precipProbability;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public double getApparentTemperature() {
        return this.apparentTemperature;
    }

    public double getDewPoint() {
        return this.dewPoint;
    }

    public double getHumidity() {
        return this.humidity;
    }

    public double getPressure() {
        return this.pressure;
    }

    public double getWindSpeed() {
        return this.windSpeed;
    }

    public double getWindGust() {
        return this.windGust;
    }

    public double getWindBearing() {
        return this.windBearing;
    }

    public double getCloudCover() {
        return this.cloudCover;
    }

    public double getUvIndex() {
        return this.uvIndex;
    }

    public double getVisibility() {
        return this.visibility;
    }

    public double getOzone() {
        return this.ozone;
    }
}
