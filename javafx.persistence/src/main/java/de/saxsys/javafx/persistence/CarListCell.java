package de.saxsys.javafx.persistence;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class CarListCell extends ListCell<Car> {

	@Override
	protected void updateItem(final Car car, final boolean empty) {
		super.updateItem(car, empty);
		if (empty) {
			setGraphic(null);
		} else {
			final Label lblModel = new Label(car.getModel());
			final Label lblManufacturer = new Label(car.getManufacturer());
			final VBox container = new VBox(lblModel, lblManufacturer);
			setGraphic(container);
		}
	}

}
