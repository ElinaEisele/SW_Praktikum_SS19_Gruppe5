package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zum Anzeigen einer Dialog Box, wenn ein Eintrag gelöscht werden soll.
 * 
 * @author ElinaEisele, JonasWagenknecht, LeoniFriedrich
 *
 */
public class DeleteListitemDialogBox extends DialogBox {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Listitem selectedListitem = null;
	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmationLabel = new Label("Möchtest du den ausgewählten Eintrag löschen?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Bestätigen");
	private Button cancelButton = new Button("Abbrechen");

	/**
	 * In diesem Konstruktor werden die Button-Widgets sowie das beschreibende Label
	 * der Dialog Box hinzugefügt.
	 * 
	 */
	public DeleteListitemDialogBox() {

		this.setGlassEnabled(true);

		cancelButton.setStylePrimaryName("NavButton");
		confirmButton.setStylePrimaryName("NavButton");

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());

		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(confirmationLabel);
		mainPanel.add(buttonPanel);

		this.add(mainPanel);
		this.center();
	}

	public Listitem getSelectedListitem() {
		return selectedListitem;
	}

	public void setSelectedListitem(Listitem selectedListitem) {
		this.selectedListitem = selectedListitem;
	}

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Schließen der Dialog Box.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteListitemDialogBox.this.hide();

		}

	}

	/**
	 * Schließen der Dialog Box und löschen des ausgewählten
	 * <code>Listitem</code>-Objekts.
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedListitem != null) {
				shoppinglistAdministration.delete(selectedListitem, new DeleteListitemCallback(selectedListitem));
				DeleteListitemDialogBox.this.hide();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewählt.");
			}
		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Das selektierte <code>Listitem</code>-Objekt wird null gesetzt und die
	 * <code>ShoppinglistShowForm</code> aufgerufen.
	 */
	private class DeleteListitemCallback implements AsyncCallback<Void> {

		Listitem listitem = null;

		DeleteListitemCallback(Listitem l) {
			this.listitem = l;
		}

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			if (listitem != null) {
				setSelectedListitem(null);

				RootPanel.get("main").clear();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(ssf);

			}
		}

	}
}
