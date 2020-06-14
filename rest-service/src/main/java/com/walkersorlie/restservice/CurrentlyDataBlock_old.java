/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Walker Sorlie
 */
@Document(collection = "collection_weather_currently")
public class CurrentlyDataBlock_old {

    //required 
    private final long requestTime;

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

    private CurrentlyDataBlock_old(Builder builder) {
        //required 
        this.requestTime = builder.requestTime;

        this.id = builder.id;
        this.time = builder.time;
        this.summary = builder.summary;
        this.nearestStormDistance = builder.nearestStormDistance;
        this.nearestStormBearing = builder.nearestStormBearing;
        this.precipIntensity = builder.precipIntensity;
        this.precipProbability = builder.precipProbability;
        this.temperature = builder.temperature;
        this.apparentTemperature = builder.apparentTemperature;
        this.dewPoint = builder.dewPoint;
        this.humidity = builder.humidity;
        this.pressure = builder.pressure;
        this.windSpeed = builder.windSpeed;
        this.windGust = builder.windGust;
        this.windBearing = builder.windBearing;
        this.cloudCover = builder.cloudCover;
        this.uvIndex = builder.uvIndex;
        this.visibility = builder.visibility;
        this.ozone = builder.ozone;
    }

    public String getId() {
        return this.id;
    }

    public long getRequestTime() {
        return this.requestTime;
    }

    public long getTime() {
        return this.time;
    }

    public String getFormattedTime() {
        return "";
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

    public static class Builder {

        // required
        private long requestTime;

        private String id;
        private long time;
        private String summary;
        private int nearestStormDistance;
        private int nearestStormBearing;
        private double precipIntensity;
        private double precipProbability;
        private double temperature;
        private double apparentTemperature;
        private double dewPoint;
        private double humidity;
        private double pressure;
        private double windSpeed;
        private double windGust;
        private double windBearing;
        private double cloudCover;
        private double uvIndex;
        private double visibility;
        private double ozone;

        public Builder() {
        }

        public Builder setRequestTime(long requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setNearestStormDistance(int distance) {
            this.nearestStormDistance = distance;
            return this;
        }

        public Builder setNearestStormBearing(int bearing) {
            this.nearestStormBearing = bearing;
            return this;
        }

        public Builder setPrecipIntensity(double intensity) {
            this.precipIntensity = intensity;
            return this;
        }

        public Builder setPrecipProbability(double probability) {
            this.precipProbability = probability;
            return this;
        }

        public Builder setTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder setApparentTemperature(double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
            return this;
        }

        public Builder setdewPoint(double dewPoint) {
            this.dewPoint = dewPoint;
            return this;
        }

        public Builder setHumidity(double humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setPressure(double pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder setWindGust(double windGust) {
            this.windGust = windGust;
            return this;
        }

        public Builder setWindBearing(double windBearing) {
            this.windBearing = windBearing;
            return this;
        }

        public Builder setCloudCover(double cloudCover) {
            this.cloudCover = cloudCover;
            return this;
        }

        public Builder setUvIndex(double uvIndex) {
            this.uvIndex = uvIndex;
            return this;
        }

        public Builder setVisibility(double visibility) {
            this.visibility = visibility;
            return this;
        }

        public Builder setOzone(double ozone) {
            this.ozone = ozone;
            return this;
        }

        public CurrentlyDataBlock_old build() {
            return new CurrentlyDataBlock_old(this);
        }
    }
}
