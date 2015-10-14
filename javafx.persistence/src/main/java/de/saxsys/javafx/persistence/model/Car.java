package de.saxsys.javafx.persistence.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {
	
	private Long id;
	private final StringProperty manufacturer = new SimpleStringProperty();
	private final StringProperty model = new SimpleStringProperty();
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public final StringProperty manufacturerProperty() {
		return this.manufacturer;
	}
	
	public final String getManufacturer() {
		return this.manufacturerProperty().get();
	}
	
	public final void setManufacturer(final String manufacturer) {
		this.manufacturerProperty().set(manufacturer);
	}
	
	public final StringProperty modelProperty() {
		return this.model;
	}
	
	public final String getModel() {
		return this.modelProperty().get();
	}
	
	public final void setModel(final String model) {
		this.modelProperty().set(model);
	}
}
