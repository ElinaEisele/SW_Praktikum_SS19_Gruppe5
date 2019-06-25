package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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
	private Shoppinglist selectedShoppinglist = null;
	private NavigatorPanel navigatorPanel = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox newShoppinglistNameTextBox = new TextBox();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button();
	private Button cancelButton = new Button();
	private Grid editShoppinglistNameGrid = null;

	/**
	 * In diesem Konstruktor werden die Button-Widgets sowie das beschreibende Label
	 * der Form hinzugefügt.
	 * 
	 */
	public EditShoppinglistNameForm() {

		editShoppinglistNameGrid = new Grid(3, 2);
		mainPanel.add(editShoppinglistNameGrid);

		Label headerLabel = new Label("Shoppinglist Name ändern");
		editShoppinglistNameGrid.setWidget(0, 0, headerLabel);

		Label newShoppinglistNameLabel = new Label("Neuer Name: ");
		editShoppinglistNameGrid.setWidget(1, 0, newShoppinglistNameLabel);
		editShoppinglistNameGrid.setWidget(1, 1, newShoppinglistNameTextBox);

		Image DiscardImg = new Image();
		DiscardImg.setUrl("images/cancel.png");
		DiscardImg.setSize("16px", "16px");
		cancelButton.getElement().appendChild(DiscardImg.getElement());
		cancelButton.setStyleName("ShoppinglistHeaderButton");
		cancelButton.addClickHandler(new CancelClickHandler());

		Image SaveImg = new Image();
		SaveImg.setUrl("images/tick.png");
		SaveImg.setSize("16px", "16px");
		confirmButton.getElement().appendChild(SaveImg.getElement());
		confirmButton.setStyleName("ShoppinglistHeaderButton");
		confirmButton.addClickHandler(new ConfirmClickHandler());

		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
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
				if (newShoppinglistNameTextBox.getText().length() <= 23) {
					selectedShoppinglist.setName(newShoppinglistNameTextBox.getText());

					shoppinglistAdministration.save(selectedShoppinglist, new EditNameCallback());
				} else {
					Window.alert("Bitte gib eine kürzeren Namen ein");

				}
			} else {
				Notification.show("Keine Einkaufsliste ausgewählt");
			}
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
