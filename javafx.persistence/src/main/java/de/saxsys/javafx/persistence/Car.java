package de.saxsys.javafx.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "car")
public class Car {

 private final StringProperty manufacturer = new SimpleStringProperty();
 private final StringProperty model = new SimpleStringProperty();

 public final StringProperty manufacturerProperty() {
  return this.manufacturer;
 }

 @XmlAttribute
 public final String getManufacturer() {
  return this.manufacturerProperty().get();
 }

 public final void setManufacturer(final String manufacturer) {
  this.manufacturerProperty().set(manufacturer);
 }

 public final StringProperty modelProperty() {
  return this.model;
 }

 @XmlAttribute
 public final String getModel() {
  return this.modelProperty().get();
 }

 public final void setModel(final String model) {
  this.modelProperty().set(model);
 }

}
