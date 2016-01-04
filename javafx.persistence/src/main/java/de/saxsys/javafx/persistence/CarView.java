package de.saxsys.javafx.persistence;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
  private Button btnRemoveCar;

  @FXML
  private TextField txtNewManufacturerName;
  @FXML
  private TextField txtNewCarName;

  private final CarDb carDb = new CarDb();

  private final BooleanProperty loading = new SimpleBooleanProperty();

  private final ListProperty<Manufacturer> manufacturers = new SimpleListProperty<>(
      FXCollections.observableArrayList());

  @FXML
  private void initialize() {
    btnAddCar.disableProperty().bind(loading);
    btnAddManufacturer.disableProperty().bind(loading);
    btnRemoveCar.disableProperty().bind(loading);
    btnRemoveManufacturer.disableProperty().bind(loading);

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
          lvCars.setItems(newValue.carsProperty());
        });
    loadManufacturers();
  }

  private void loadManufacturers() {
    final Service<List<Manufacturer>> service = new Service<List<Manufacturer>>() {
      @Override
      protected Task<List<Manufacturer>> createTask() {
        return new Task<List<Manufacturer>>() {

          @Override
          protected List<Manufacturer> call() throws Exception {
            System.out.println("loading manufacturer in " + Thread.currentThread().getName());
            return carDb.readManufacturer();
          }
        };
      }
    };
    service.setOnSucceeded(event -> {
      System.out.println("setting loaded manufacturers in " + Thread.currentThread().getName());
      manufacturers.addAll(service.getValue());
      loading.set(false);
    });
    loading.set(true);
    service.start();
  }

  @FXML
  void addCar(final ActionEvent event) {
    final String newCarName = txtNewCarName.getText();
    if (newCarName != null && !newCarName.isEmpty()) {
      final Manufacturer selectedManufacturer = lvManufacturer.getSelectionModel().getSelectedItem();
      if (selectedManufacturer != null) {
        selectedManufacturer.carsProperty().add(new Car(newCarName));
        carDb.saveManufacturer(selectedManufacturer);
      }
    }
  }

  @FXML
  void removeCar(final ActionEvent event) {
    final Manufacturer selectedManufacturer = lvManufacturer.getSelectionModel().getSelectedItem();
    final Car selectedCar = lvCars.getSelectionModel().getSelectedItem();
    if (selectedManufacturer != null && selectedCar != null) {
      selectedManufacturer.carsProperty().remove(selectedCar);
      carDb.saveManufacturer(selectedManufacturer);
    }
  }

  @FXML
  void addManufacturer(final ActionEvent event) {
    final String newManufacturerName = txtNewManufacturerName.getText();
    if (newManufacturerName != null && !newManufacturerName.isEmpty()) {
      final Manufacturer newManufacturer = new Manufacturer(newManufacturerName);
      manufacturers.add(newManufacturer);
      carDb.saveManufacturer(newManufacturer);
    }
  }

  @FXML
  void removeManufacturer(final ActionEvent event) {
    final Manufacturer selectedItem = lvManufacturer.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
      manufacturers.remove(selectedItem);
      carDb.deleteManufacturer(selectedItem);
    }
  }

}
