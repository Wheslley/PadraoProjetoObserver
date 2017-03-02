package principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import IModel.IMessages;
import control.AuditingStation;
import model.CompositionOfDirectories;
import model.CurrentConditionsDisplay;
import model.WeatherData;

public class WeatherStation {

	public static void main(String[] args) {

		List<CurrentConditionsDisplay> listaObserver = new ArrayList<>();
		Map<String, String> mapDirectoriesAndObservers = new HashMap<>();
		WeatherData weatherData = new WeatherData(IMessages.NAME_OF_FILE);
		Scanner scan = new Scanner(System.in);
		boolean flag = true;
		Integer opcao = 0;
		
		while (flag) {

			opcao = 0;

			System.out.println(IMessages.MENU);

			opcao = scan.nextInt();

			switch (opcao) {
			case 1: {

				CompositionOfDirectories compositionOfDirectories = new CompositionOfDirectories();
				
				System.out.println(IMessages.PRINT_NAME_OBSERVER);
				compositionOfDirectories.setName(scan.next());

				System.out.println(IMessages.PRINT_PATH_OBSERVER);
				compositionOfDirectories.setPath(scan.next());

				AuditingStation.addObserver(mapDirectoriesAndObservers, weatherData, listaObserver, compositionOfDirectories);
				break;

			}
			case 2: {

				System.out.println(IMessages.PRINT_NAME_OBSERVER);
				String name = scan.next();
				
				AuditingStation.deleteObserver(listaObserver, mapDirectoriesAndObservers, weatherData, name);
				break;
				
			}
			default:
				flag = false;
				break;
			}

		}
		
		scan.close();
		System.exit(0);
		
	}

}
