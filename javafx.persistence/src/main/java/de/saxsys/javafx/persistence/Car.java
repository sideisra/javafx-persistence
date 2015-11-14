package de.saxsys.javafx.persistence;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {

 private final StringProperty name = new SimpleStringProperty();

 public Car(final String name) {
  this.name.set(name);
 }

 public final StringProperty nameProperty() {
  return this.name;
 }

 public String getName() {
  return this.nameProperty().get();
 }

 public void setName(final String name) {
  this.nameProperty().set(name);
 }

}
