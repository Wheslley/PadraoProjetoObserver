package control;

import java.io.File;
import java.util.List;
import java.util.Map;

import IModel.IMessages;
import model.CompositionOfDirectories;
import model.CurrentConditionsDisplay;
import model.WeatherData;

public class AuditingStation {

	public static void addObserver(Map<String, String> mapDirectoriesAndObservers, WeatherData weatherData,
			List<CurrentConditionsDisplay> listaObserver, CompositionOfDirectories compositionOfDirectories) {

		File newSensor = new File(compositionOfDirectories.getPath());
		newSensor.mkdirs();

		mapDirectoriesAndObservers.put(compositionOfDirectories.getName(), compositionOfDirectories.getPath());

		CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(compositionOfDirectories.getName(),
				compositionOfDirectories.getPath());

		listaObserver.add(currentConditions);
		weatherData.registerObserver(currentConditions);

	}

	public static void deleteObserver(List<CurrentConditionsDisplay> listaObserver,
			Map<String, String> mapDirectoriesAndObservers, WeatherData weatherData, String name) {

		listaObserver.forEach(observer -> {
			if (observer.getName().equals(name)) {

				String pathDelete = mapDirectoriesAndObservers.get(observer.getPath());

				if (AuditingStation.formatDirectorie(pathDelete)) {
					File fileDelete = new File(pathDelete);
					fileDelete.delete();
					System.out.println(IMessages.PRINT_DIRECTORIES_DELETED);
				} else {
					System.err.println(IMessages.ERROR);
				}

				weatherData.removeObserver(observer);
			}
		});

	}

	public static boolean formatDirectorie(String pathDelete) {

		try {
			File folder = new File(pathDelete);
			File[] arrayOfFiles = folder.listFiles();

			for (File file : arrayOfFiles) {
				if (file.isFile()) {
					File newSensor = new File(pathDelete + "//" + file.getName());
					newSensor.delete();
				} else if (file.isDirectory()) {
					formatDirectorie(file.getName());
				}
			}

			return true;

		} catch (Exception e) {
			return false;
		}

	}

}
