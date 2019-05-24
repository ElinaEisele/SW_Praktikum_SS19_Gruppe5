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
import de.hdm.softwarepraktikum.shared.bo.Listitem;

/**
 * Klasse zum Anzeigen eines Dialogs, wenn ein Listitem geloescht werden soll.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class DeleteListitemDialogBox extends DialogBox {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Listitem slectedListitem = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmationLabel = new Label(
			"Sind Sie sicher, dass Sie die ausgewaehlte Shoppinglist loeschen moechten?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Loeschen");
	private Button cancelButton = new Button("Abbrechen");

	public DeleteListitemDialogBox() {

		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());

		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(confirmationLabel);
		mainPanel.add(buttonPanel);

	}

	public void onLoad() {
		this.setGlassEnabled(true);
		this.add(mainPanel);
		this.center();
	}

	public Listitem getSlectedListitem() {
		return slectedListitem;
	}

	public void setSlectedListitem(Listitem slectedListitem) {
		this.slectedListitem = slectedListitem;
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
			if (slectedListitem != null) {

				shoppinglistAdministration.delete(slectedListitem, new DeleteListitemCallback());
				DeleteListitemDialogBox.this.hide();
			} else {
				Notification.show("Es wurde kein Eintrag zum loeschen ausgewaehlt.");
			}
		}

	}

	private class DeleteListitemCallback implements AsyncCallback<Void> {

		Listitem listitem = null;

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: /n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			if (listitem != null) {
				setSlectedListitem(null);
				RootPanel.get("main").clear();

			}
		}

	}

}
