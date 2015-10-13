package de.saxsys.javafx.persistence;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarList {

 ListProperty<Car> cars = new SimpleListProperty<>(FXCollections.observableArrayList());

 public ListProperty<Car> carsProperty() {
  return this.cars;
 }

 public ObservableList<Car> getCars() {
  return cars.get();
 }

 public void setCars(final ObservableList<Car> cars) {
  this.cars.set(cars);
 }

}
