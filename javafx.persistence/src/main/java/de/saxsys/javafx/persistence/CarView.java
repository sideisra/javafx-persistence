package de.saxsys.javafx.persistence;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

	final ObjectProperty<CarList> cars = new SimpleObjectProperty<>(new CarList());

	@FXML
	private ListView<Car> lvCars;

	@FXML
	public void initialize() {
		lvCars.setCellFactory(param -> new CarListCell());
		lvCars.itemsProperty().bind(cars.get().carsProperty());
		cars.addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				lvCars.itemsProperty().bind(cars.get().carsProperty());
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

	@FXML
	void loadCars(final ActionEvent event) {
		try {
			final Unmarshaller unmarshaller = JAXBContext.newInstance(CarList.class).createUnmarshaller();
			cars.set((CarList) unmarshaller.unmarshal(new File("cars.xml")));
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void saveCars(final ActionEvent event) {
		try {
			final Marshaller marshaller = JAXBContext.newInstance(CarList.class).createMarshaller();
			marshaller.marshal(cars.get(), new File("cars.xml"));
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
	}
}
