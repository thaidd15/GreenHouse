package com.example.dodinhthai.greenhouse;

public class Environment {
    private String mTemperature;
    private String mHumidity;
    private String mPH;

    public void setTemperature(String Temperature) {
        mTemperature = Temperature;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setHumidity(String Humidity) {
        mHumidity = Humidity;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setPH(String PH) {
        mPH = PH;
    }

    public String getPH() {
        return mPH;
    }
}
