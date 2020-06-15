
package com.walkersorlie.restservice.DataBlock;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 *
 * @author Walker Sorlie
 */
@Document(collection = "collection_weather_hourly")
public class HourlyDataBlock {
    
    @Id
    private final String id;
    private final long time;
    private final String summary;
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

    public HourlyDataBlock(String id, long time, String summary, double precipIntensity, double precipProbability, 
            double temperature, double apparentTemperature, double dewPoint, double humidity, double pressure, 
            double windSpeed, double windGust, double windBearing, double cloudCover, double uvIndex, 
            double visibility, double ozone) {
        
        this.id = id;
        this.time = time;
        this.summary = summary;
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
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindGust() {
        return windGust;
    }

    public double getWindBearing() {
        return windBearing;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getOzone() {
        return ozone;
    }
    
    
    

}
