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
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung einer Dialogbox, wenn ein User eine Gruppe verlassen
 * moechte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class LeaveGroupDialogBox extends DialogBox {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	private User u = CurrentUser.getUser();
	private Group selectedGroup = null;
	private GroupShoppinglistTreeViewModel gstvm = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmationLabel = new Label("Möchtest du wirklich die Gruppe verlassen?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Bestätigen");
	private Button cancelButton = new Button("Abbrechen");

	public LeaveGroupDialogBox() {

		this.setGlassEnabled(true);

		cancelButton.setStylePrimaryName("NavButton");
		confirmButton.setStylePrimaryName("NavButton");
		buttonPanel.setStyleName("ButtonPanel");
		confirmationLabel.setStyleName("Header");
		
		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());

		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(confirmationLabel);
		mainPanel.add(buttonPanel);

		this.add(mainPanel);
		this.center();
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
			LeaveGroupDialogBox.this.hide();
		}

	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Click-/EventHandler
	 * ***************************************************************************
	 */

	/**
	 * Durch Betätigen der Bestätigen-Schaltfläche wird das <code>User</code>-Objekt
	 * aus dem <code>Group</code>-Objekt entfernt.
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				shoppinglistAdministration.removeUserFromGroup(u, selectedGroup, new RemoveUserCallback());
				RootPanel.get("aside").clear();
				RootPanel.get("main").clear();

				NavigatorPanel np = new NavigatorPanel();
				RootPanel.get("aside").add(np);
				LeaveGroupDialogBox.this.hide();

			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}

	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */
	/**
	 * Zum Entfernen der Gruppe aus dem <code>CellTree</code>.
	 *
	 */
	private class RemoveUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			setSelectedGroup(null);
			gstvm.removeGroup(selectedGroup);

		}

	}

}
