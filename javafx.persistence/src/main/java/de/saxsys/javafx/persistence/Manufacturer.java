package de.saxsys.javafx.persistence;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Entity
@Access(AccessType.PROPERTY)
public class Manufacturer {

 @Transient
 private Long id;
 @Transient
 private final StringProperty name = new SimpleStringProperty();
 @Transient
 private ObservableList<Car> cars = FXCollections.observableArrayList();

 public Manufacturer() {
 }

 public Manufacturer(final String name) {
  this.name.set(name);
 }

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public Long getId() {
  return id;
 }

 public void setId(final Long id) {
  this.id = id;
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

 @Transient
 public ObservableList<Car> getCarsObservable() {
  return cars;
 }

 @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST)
 public List<Car> getCars() {
  return cars;
 }

 public void setCars(final List<Car> cars) {
  this.cars = FXCollections.observableArrayList(cars);
 }
}