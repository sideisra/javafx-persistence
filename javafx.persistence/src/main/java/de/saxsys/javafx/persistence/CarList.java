package de.saxsys.javafx.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "cars")
public class CarList {

	ListProperty<Car> cars = new SimpleListProperty<>(FXCollections.observableArrayList());

	public ListProperty<Car> carsProperty() {
		return this.cars;
	}

	@XmlElement(name = "car")
	public ObservableList<Car> getCars() {
		return cars.get();
	}

	public void setCars(final ObservableList<Car> cars) {
		this.cars.set(cars);
	}

}
