package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;

/**
 * Klasse zur Darstellung einer Dialogbox, wenn ein User ein Listitem als
 * Standardlistitem entfernen will.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class StandardListitemOffDialogBox extends DialogBox {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Listitem selectedListitem = null;
	private Group selectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmLabel = new Label("Dieses Listitem als Standardlistitem entfernen?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Bestaetigen");
	private Button cancelButton = new Button("Abbrechen");

	public StandardListitemOffDialogBox() {

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(confirmLabel);
		mainPanel.add(buttonPanel);

	}

	public void onLoad() {
		this.setGlassEnabled(true);
		this.add(mainPanel);
		this.center();
	}

	public Listitem getSelectedListitem() {
		return selectedListitem;
	}

	public void setSelectedListitem(Listitem selectedListitem) {
		this.selectedListitem = selectedListitem;
	}
	
	

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}



	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			StandardListitemOffDialogBox.this.hide();

		}

	}

	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedListitem != null) {
				shoppinglistAdministration.setStandardListitem(selectedListitem, selectedGroup, false, new RemoveStandardCallback());
				Notification.show("Eintrag nicht mehr als Standard gesetzt");
				StandardListitemOffDialogBox.this.hide();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewaehlt.");
			}
		}

	}

	private class RemoveStandardCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: /n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

		}
	}
}
