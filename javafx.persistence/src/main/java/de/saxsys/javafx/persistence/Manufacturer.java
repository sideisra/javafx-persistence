package de.saxsys.javafx.persistence;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Manufacturer {

 private final StringProperty name = new SimpleStringProperty();
 private ObservableList<Car> cars = FXCollections.observableArrayList();

 public Manufacturer(final String name) {
  this.name.set(name);
 }

 public StringProperty nameProperty() {
  return this.name;
 }

 public String getName() {
  return this.nameProperty().get();
 }

 public final void setName(final String name) {
  this.nameProperty().set(name);
 }

 public ObservableList<Car> getCars() {
  return cars;
 }

 public void setCars(final List<Car> cars) {
  this.cars = FXCollections.observableArrayList(cars);
 }
}
