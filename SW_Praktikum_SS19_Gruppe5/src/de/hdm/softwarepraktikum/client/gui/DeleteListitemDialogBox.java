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
 * Klasse zum Anzeigen eines Dialogs, wenn ein Listitem geloescht werden soll.
 * 
 * @author ElinaEisele, JonasWagenknecht, Leoni Friedrich
 *
 */
public class DeleteListitemDialogBox extends DialogBox {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Listitem selectedListitem = null;
	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmationLabel = new Label(
			"Sind Sie sicher, dass Sie das ausgwaehlte Listitem loeschen moechten?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Loeschen");
	private Button cancelButton = new Button("Abbrechen");

	public DeleteListitemDialogBox() {

		this.setGlassEnabled(true);

		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

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



	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteListitemDialogBox.this.hide();

		}

	}

	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedListitem != null) {
				shoppinglistAdministration.delete(selectedListitem, new DeleteListitemCallback(selectedListitem));
				DeleteListitemDialogBox.this.hide();
			} else {
				Notification.show("Es wurde kein Listitem ausgewählt.");
			}
		}

	}

	private class DeleteListitemCallback implements AsyncCallback<Void> {

		Listitem listitem = null;

		DeleteListitemCallback(Listitem l) {
			this.listitem = l;
		}

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: /n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			if (listitem != null) {
				setSelectedListitem(null);

				RootPanel.get("main").clear();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
//				ssf.setGstvm(gstvm);
//				gstvm.setSelectedGroup(null);
//				gstvm.setSelectedShoppinglist(object);

				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(ssf);
				

			}
		}

	}
}
