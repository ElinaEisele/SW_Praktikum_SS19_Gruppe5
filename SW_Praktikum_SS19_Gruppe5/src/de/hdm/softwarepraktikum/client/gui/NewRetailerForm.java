package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung eines Formulars, um einen neuen Haendler anzulegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewRetailerForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Shoppinglist selectedShoppinglist = null;

	private VerticalPanel mainPanel = new VerticalPanel();

	private TextBox retailerNameTextBox = new TextBox();

	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Anlegen");
	private Button cancelButton = new Button("Abbrechen");

	private Grid newRetailerGrid;

	public NewRetailerForm() {

		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		newRetailerGrid = new Grid(4, 2);
		mainPanel.add(newRetailerGrid);

		Label newRetailerLabel = new Label("Name des Einzelhaendlers: ");
		newRetailerGrid.setWidget(1, 0, newRetailerLabel);
		newRetailerGrid.setWidget(1, 1, retailerNameTextBox);

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(newRetailerLabel);
		mainPanel.add(buttonPanel);

	}

	public void onLoad() {
		this.add(mainPanel);
	}

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedGroup(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	/**
	 * ClickHandler zum schliessen der DialogBox.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			RootPanel.get("main").add(ssf);
		}
	}

	/**
	 * ClickHandler zum erstellen eines Retailer Objekts.
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				shoppinglistAdministration.createRetailer(retailerNameTextBox.getText(), new CreateRetailerCallback());

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
		}
	}

	/**
	 * Textbox inhalt zuruecksetzen und ShoppinglistShowForm aktualisieren
	 */
	private class CreateRetailerCallback implements AsyncCallback<Retailer> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: /n" + caught.toString());
		}

		@Override
		public void onSuccess(Retailer result) {

			retailerNameTextBox.setText("");
			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			RootPanel.get("main").add(ssf);
		}

	}

}
