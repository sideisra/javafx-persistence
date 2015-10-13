package de.saxsys.javafx.persistence;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CarView {

	private static final Random RAND = new Random();
	private static final Map<String, String> CAR_MODELS_2_MANUFACTURER = new HashMap<>();

	static {
		CAR_MODELS_2_MANUFACTURER.put("A3 etron", "Audi");
		CAR_MODELS_2_MANUFACTURER.put("eGolf", "VW");
		CAR_MODELS_2_MANUFACTURER.put("Q7 etron", "Audi");
		CAR_MODELS_2_MANUFACTURER.put("Passat GTE", "VW");
		CAR_MODELS_2_MANUFACTURER.put("Golf GTE", "VW");
		CAR_MODELS_2_MANUFACTURER.put("Model S", "Tesla");
	}

	private final CarList cars = new CarList();

	private final CarDb carDb = new CarDb();

	@FXML
	private ListView<Car> lvCars;

	@FXML
	public void initialize() {
		lvCars.setCellFactory(param -> new CarListCell());
		lvCars.itemsProperty().bind(cars.carsProperty());
		cars.getCars().setAll(carDb.readCars());
		cars.getCars().addListener((ListChangeListener<Car>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					c.getAddedSubList().stream().forEach(carDb::saveCar);
				}
				if (c.wasRemoved()) {
					c.getRemoved().stream().forEach(carDb::deleteCar);
				}
			}
		});
	}

	@FXML
	void addCar(final ActionEvent event) {
		final Car newCar = new Car();
		final String model = new LinkedList<>(CAR_MODELS_2_MANUFACTURER.keySet())
				.get(RAND.nextInt(CAR_MODELS_2_MANUFACTURER.size()));
		newCar.setManufacturer(CAR_MODELS_2_MANUFACTURER.get(model));
		newCar.setModel(model);
		lvCars.getItems().add(newCar);
	}

	@FXML
	void removeCar(final ActionEvent event) {
		if (lvCars.getItems().size() > 0) {
			lvCars.getItems().remove(0);
		}
	}
}
