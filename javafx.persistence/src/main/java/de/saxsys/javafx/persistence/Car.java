package de.saxsys.javafx.persistence;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.FIELD)
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Transient
	private final StringProperty manufacturer = new SimpleStringProperty();
	@Transient
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

	@Access(AccessType.PROPERTY)
	public final String getManufacturer() {
		return this.manufacturerProperty().get();
	}

	public final void setManufacturer(final String manufacturer) {
		this.manufacturerProperty().set(manufacturer);
	}

	public final StringProperty modelProperty() {
		return this.model;
	}

	@Access(AccessType.PROPERTY)
	public final String getModel() {
		return this.modelProperty().get();
	}

	public final void setModel(final String model) {
		this.modelProperty().set(model);
	}
}
