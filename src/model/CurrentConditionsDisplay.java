package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import IModel.IMessages;
import IModel.Observer;

public class CurrentConditionsDisplay implements Observer{
	
	private File sensor;
	private String name;
	private String path;
	
	public CurrentConditionsDisplay(String name, String path){
		this.name = name;
		this.path = path;
	}
	
	@Override
	public void update(File sensor) {
		
		this.sensor = sensor;
		
		copyArqchive();
		
	}
	
	public void copyArqchive(){
		
		File newSensor = new File(getPath() + "//" + IMessages.NAME_OF_FILE);
		newSensor.delete();
		
		try {
			FileReader fileReader = new FileReader(getSensor());
			FileWriter fileWriter = new FileWriter(newSensor, true);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String data = bufferedReader.readLine();
			fileWriter.write(data);
			
			bufferedReader.close();
			fileReader.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getSensor() {
		return sensor;
	}

	public void setSensor(File sensor) {
		this.sensor = sensor;
	}
	
}
