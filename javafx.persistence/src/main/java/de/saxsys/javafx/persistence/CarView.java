package de.saxsys.javafx.persistence;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class CarView {
 @FXML
 private ListView<Manufacturer> lvManufacturer;

 @FXML
 private Button btnAddManufacturer;

 @FXML
 private Button btnRemoveManufacturer;

 @FXML
 private ListView<Car> lvCars;

 @FXML
 private Button btnAddCar;

 @FXML
 private Button btnRemove;

 @FXML
 private TextField txtNewManufacturerName;
 @FXML
 private TextField txtNewCarName;

 private final ListProperty<Manufacturer> manufacturers = new SimpleListProperty<>(FXCollections.observableArrayList());

 @FXML
 private void initialize() {
  lvCars.setCellFactory(param -> new TextFieldListCell<Car>(new StringConverter<Car>() {

   @Override
   public String toString(final Car object) {
    return object.getName();
   }

   @Override
   public Car fromString(final String string) {
    throw new RuntimeException("should not happen");
   }

  }));
  lvManufacturer.setCellFactory(param -> new TextFieldListCell<Manufacturer>(new StringConverter<Manufacturer>() {

   @Override
   public String toString(final Manufacturer object) {
    return object.getName();
   }

   @Override
   public Manufacturer fromString(final String string) {
    throw new RuntimeException("should not happen");
   }
  }));
  lvManufacturer.setItems(manufacturers);
  lvManufacturer.getSelectionModel().selectedItemProperty()
    .addListener((ChangeListener<Manufacturer>) (observable, oldValue, newValue) -> {
     lvCars.setItems(newValue.getCars());
    });
 }

 @FXML
 void addCar(final ActionEvent event) {
  final String newCarName = txtNewCarName.getText();
  if (newCarName != null && !newCarName.isEmpty()) {
   final Manufacturer selectedManufacturer = lvManufacturer.getSelectionModel().getSelectedItem();
   if (selectedManufacturer != null) {
    selectedManufacturer.getCars().add(new Car(newCarName));
   }
  }
 }

 @FXML
 void removeCar(final ActionEvent event) {
  final Manufacturer selectedManufacturer = lvManufacturer.getSelectionModel().getSelectedItem();
  final Car selectedCar = lvCars.getSelectionModel().getSelectedItem();
  if (selectedManufacturer != null && selectedCar != null) {
   selectedManufacturer.getCars().remove(selectedCar);
  }
 }

 @FXML
 void addManufacturer(final ActionEvent event) {
  final String newManufacturerName = txtNewManufacturerName.getText();
  if (newManufacturerName != null && !newManufacturerName.isEmpty()) {
   manufacturers.add(new Manufacturer(newManufacturerName));
  }
 }

 @FXML
 void removeManufacturer(final ActionEvent event) {
  final Manufacturer selectedItem = lvManufacturer.getSelectionModel().getSelectedItem();
  if (selectedItem != null) {
   manufacturers.remove(selectedItem);
  }
 }

}
