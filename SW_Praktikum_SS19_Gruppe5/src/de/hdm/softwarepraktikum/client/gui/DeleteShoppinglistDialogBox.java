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
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung einer DialogBox, wenn man eine Shoppingliste loeschen
 * will.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class DeleteShoppinglistDialogBox extends DialogBox {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private NavigatorPanel navigatorPanel;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();
	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmationLabel = new Label(
			"Sind Sie sicher, dass Sie die ausgewaehlte Shoppinglist loeschen moechten?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Loeschen");
	private Button cancelButton = new Button("Abbrechen");

	public DeleteShoppinglistDialogBox() {

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

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteShoppinglistDialogBox.this.hide();

		}

	}

	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				shoppinglistAdministration.delete(selectedShoppinglist,
						new DeleteShoppinglistCallback(selectedShoppinglist));
				DeleteShoppinglistDialogBox.this.hide();
			} else {
				Notification.show("Es wurde keine Einkaufsliste ausgewaehlt.");
			}
		}

	}

	private class DeleteShoppinglistCallback implements AsyncCallback<Void> {

		Shoppinglist shoppinglist = null;

		DeleteShoppinglistCallback(Shoppinglist s) {
			this.shoppinglist = s;
		}

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: /n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			if (shoppinglist != null) {
				RootPanel.get("main").clear();
				RootPanel.get("aside").clear();
				navigatorPanel = new NavigatorPanel();
				gstvm.removeShoppinglistOfGroup(selectedShoppinglist, selectedGroup);
				setSelectedShoppinglist(null);
				RootPanel.get("aside").add(navigatorPanel);
			}
		}

	}

}
