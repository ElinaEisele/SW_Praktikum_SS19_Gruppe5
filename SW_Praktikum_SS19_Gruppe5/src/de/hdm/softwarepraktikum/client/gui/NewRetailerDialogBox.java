package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
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
 * Klasse zur Darstellung einer Dialogbox, um einen neuen Haendler anzulegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewRetailerDialogBox extends DialogBox {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Shoppinglist selectedShoppinglist = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label newRetailerLabel = new Label("Name des Einzehändlers: ");
	private TextBox retailerName = new TextBox();

	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Anlegen");
	private Button cancelButton = new Button("Abbrechen");

	public NewRetailerDialogBox() {
		this.setGlassEnabled(true);

		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());

		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(newRetailerLabel);
		mainPanel.add(buttonPanel);

		this.add(mainPanel);
		this.center();
	}

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedGroup(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	/**
	 * ClickHandler zum schließen der DialogBox.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NewRetailerDialogBox.this.hide();
		}
	}

	/**
	 * ClickHandler zum erstellen eines Retailer Objekts.
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				shoppinglistAdministration.createRetailer(retailerName.getText(), new CreateRetailerCallback());
				NewRetailerDialogBox.this.hide();
			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
		}
	}

	/**
	 * Textbox inhalt zurücksetzen und ShoppinglistShowForm aktualisieren
	 */
	private class CreateRetailerCallback implements AsyncCallback<Retailer> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: /n" + caught.toString());
		}

		@Override
		public void onSuccess(Retailer result) {

			retailerName.setText("");
			RootPanel.get("main").clear();
			RootPanel.get("main").add(w);
			RootPanel.get("main").add(w);
		}

	}

}
