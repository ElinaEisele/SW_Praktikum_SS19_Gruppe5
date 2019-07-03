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
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse um bei einem <code>Shoppinglist</code>-Objekt den Namen zu ändern.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class EditShoppinglistNameForm extends VerticalPanel {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private ShoppinglistHeader shoppinglistHeader = null;
	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistShowForm shoppinglistShowForm = null;
	private Shoppinglist selectedShoppinglist = null;
	private NavigatorPanel navigatorPanel = null;

	private Label infoLabel = new Label("Einkaufslistenname ändern");
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox newNameTextBox = new TextBox();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");
	private Grid editShoppinglistNameGrid = null;

	/**
	 * In diesem Konstruktor werden die Button-Widgets sowie das beschreibende Label
	 * der Form hinzugefügt.
	 * 
	 */
	public EditShoppinglistNameForm() {
		
		newNameTextBox.setText("Neuen Namen eingeben...");
		newNameTextBox.addClickHandler(new NameTextBoxClickHandler());
		newNameTextBox.setWidth("200px");

		cancelButton.setStyleName("NavButton");
		cancelButton.addClickHandler(new CancelClickHandler());

		confirmButton.setStyleName("NavButton");
		confirmButton.addClickHandler(new ConfirmClickHandler());

		infoLabel.setStyleName("Header");
		buttonPanel.setStyleName("ButtonPanel");
		
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(infoLabel);
		mainPanel.add(newNameTextBox);
		mainPanel.add(buttonPanel);

	}

	/**
	 * In dieser Methode werden die Widgets dem entsprechenden div-Element
	 * hinzugefügt.
	 * 
	 */
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

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Clickhandler zum verwerfen der Eingaben und zur Rückkehr zur
	 * ShoppinglistShowForm.
	 * 
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				RootPanel.get("main").clear();
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(selectedShoppinglist);
				RootPanel.get("main").add(ssf);
			}
		}

	}

	/**
	 * Clickhandler zum ändern des Namens.
	 * 
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				if (newNameTextBox.getValue() == "") {
					Notification.show("Bitte gib einen Namen für die neue Gruppe ein.");
				} else if (newNameTextBox.getValue().length() <= 23) {
					selectedShoppinglist.setName(newNameTextBox.getValue());
					shoppinglistAdministration.save(selectedShoppinglist, new EditNameCallback());
					shoppinglistShowForm = new ShoppinglistShowForm();
				} else {
					Notification.show("Bitte gib eine kürzeren Namen ein.");

				}
			} else {
				Notification.show("Keine Einkaufsliste ausgewählt.");
			}
		}
	}
	
	/**
	 * Beim Klick in das Text Feld wird dieses geleert.
	 *
	 */
	private class NameTextBoxClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			newNameTextBox.setText("");
		}
		
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Nach dem erfolgreichen Ändern des Namen wird das Formular geschlossen und die
	 * aktuell ausgewählte Einkaufsliste erneut geöffnet.
	 * 
	 */
	private class EditNameCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("main").clear();
			RootPanel.get("aside").clear();
			navigatorPanel = new NavigatorPanel();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(selectedShoppinglist);
			RootPanel.get("aside").add(navigatorPanel);

			RootPanel.get("main").add(ssf);

		}
	}
}
