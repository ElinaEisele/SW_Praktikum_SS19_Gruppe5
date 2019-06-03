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
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse um bei einer Shoppinglist den Namen zu aendern.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class EditShoppinglistNameForm extends VerticalPanel {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	private ShoppinglistHeader shoppinglistHeader;
	private Shoppinglist selectedShoppinglist = null;
	private GroupShoppinglistTreeViewModel gstvm = null;
	private Shoppinglist shoppinglistToDisplay = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox newShoppinglistNameTextBox = new TextBox();

	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Aendern");
	private Button cancelButton = new Button("Abbrechen");

	private Grid editShoppinglistNameGrid;

	public EditShoppinglistNameForm() {

		editShoppinglistNameGrid = new Grid(3, 2);
		mainPanel.add(editShoppinglistNameGrid);

		Label headerLabel = new Label("Shoppinglist Name aendern");
		editShoppinglistNameGrid.setWidget(0, 0, headerLabel);

		Label newShoppinglistNameLabel = new Label("Neuer Name: ");
		editShoppinglistNameGrid.setWidget(1, 0, newShoppinglistNameLabel);
		editShoppinglistNameGrid.setWidget(1, 1, newShoppinglistNameTextBox);

		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());

		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
		mainPanel.add(buttonPanel);

	}

	public void onLoad() {
		RootPanel.get("main").add(mainPanel);
	}

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}
	

	public Shoppinglist getShoppinglistToDisplay() {
		return shoppinglistToDisplay;
	}

	public void setShoppinglistToDisplay(Shoppinglist shoppinglistToDisplay) {
		this.shoppinglistToDisplay = shoppinglistToDisplay;
	}


	/**
	 * Clickhandler zum verwerfen der Eingaben und zur Rückkehr zur
	 * ShoppinglistShowForm.
	 * 
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				RootPanel.get("main").clear();
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(shoppinglistToDisplay);
				RootPanel.get("main").add(ssf);
			}
		}

	}

	/**
	 * Clickhandler zum aendern des Name.
	 * 
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				shoppinglistToDisplay.setName(newShoppinglistNameTextBox.getText());

				shoppinglistAdministration.save(shoppinglistToDisplay, new EditNameCallback());

			} else {
				Notification.show("Keine Shoppinglist ausgewaehlt");
			}
		}
	}

	/**
	 * Nach dem erfolgreichen aendern des Namen wird das Formular geschlossen und
	 * die aktuell ausgewählte Shoppinglist erneut geöffnet.
	 * 
	 */
	private class EditNameCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Anlegen eines neuen Eintrags ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("main").clear();
			GroupShowForm gsf = new GroupShowForm();
			RootPanel.get("main").add(gsf);
		}
	}
}
