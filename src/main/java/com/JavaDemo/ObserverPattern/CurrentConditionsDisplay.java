package com.JavaDemo.ObserverPattern;

import java.util.Observable;
import java.util.Observer;

/**
 * @Author: zhuminming
 * @create: 2018/3/31 10:11
 * @GitHubAddress: https://github.com/zhuminming
 */
public class CurrentConditionsDisplay implements Observer{
    private Observable observable;
    private float temperature;
    private float pressure;

    public CurrentConditionsDisplay(Observable observable){
        this.observable= observable;
        this.observable.addObserver(this);

    }


    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof WeatherData){
            WeatherData data = (WeatherData) o;
            this.pressure=data.getPressure();
            this.temperature = data.getTemperature();
            display();
        }
    }

    private void display(){
        System.out.println("Current conditions:" + temperature+"F degrees and"+pressure+"% pressure");
    }


    public static void main(String[] args){
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay display = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurementsChanged(10.0f,12.0f,10.2f);
    }
}
