package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import IModel.Observer;
import IModel.Subjet;

public class WeatherData implements Subjet, Runnable {

	private List<Observer> observers;
	private Float temperature;
	private Float pressure;
	private Float humidity;
	private long lastDate;
	private File sensor;
	
	public WeatherData(){
		observers = new ArrayList<>();
	}
	
	public WeatherData(String name){
		observers = new ArrayList<>();
		this.lastDate = 0;
		setSensor(new File(name));
		Thread observationThread = new Thread(this);
		observationThread.start();
	}
	
	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Float getPressure() {
		return pressure;
	}

	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}

	public Float getHumidity() {
		return humidity;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}
	
	public File getSensor() {
		return sensor;
	}

	public void setSensor(File sensor) {
		this.sensor = sensor;
	}
	
	public Long getLastDate() {
		return lastDate;
	}

	public void setLastDate(Long lastDate) {
		this.lastDate = lastDate;
	}
	
	public void setMeasurements(Float temperature, Float pressure, Float humidity) {

		setTemperature(temperature);
		setPressure(pressure);
		setHumidity(humidity);
		
		notifyObserver();
		
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {

		try {
			observers.remove(observer);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	@Override
	public void notifyObserver() {

		for (Observer observer : observers) {
			observer.update(sensor);
		}

	}

	@Override
	public void run() {
		
		setLastDate(sensor.lastModified());
		
		while(true){
		
			if(getLastDate() != this.sensor.lastModified()){
			
				try{
					
					FileReader fileReader = new FileReader(sensor);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String data = bufferedReader.readLine();
					String[] measurements = data.split(",");
					setMeasurements(Float.parseFloat(measurements[0]), Float.parseFloat(measurements[1]), Float.parseFloat(measurements[2]));
					this.lastDate = sensor.lastModified();
					bufferedReader.close();
					fileReader.close();
					
				} catch (Exception e){
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
