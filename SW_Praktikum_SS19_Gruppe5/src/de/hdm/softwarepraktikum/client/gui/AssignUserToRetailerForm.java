package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

public class AssignUserToRetailerForm extends HorizontalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistHeader shoppinglistHeader;

	private Shoppinglist shoppinglistToDisplay = null;
	private Group groupToDisplay = null;
	private User selectedUser = null;
	private Retailer selectedRetailer = null;

	private ArrayList<User> userArrayList;
	private ArrayList<Retailer> retailerArrayList;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Grid assignUserToRetailerGrid;
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private ListBox userListBox = new ListBox();
	private ListBox retailerListBox = new ListBox();

	private Button saveButton = new Button("Speichern");
	private Button discardButton = new Button("verwerfen und zurueck");

	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
	 * angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets
	 * bestimmt.
	 */
	public AssignUserToRetailerForm() {

		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		assignUserToRetailerGrid = new Grid(4, 2);

		Label assignUserToRetailerLabel = new Label("Nutzer einem Einzelhaendler zuweisen");
		assignUserToRetailerGrid.setWidget(0, 0, assignUserToRetailerLabel);

		Label userLabel = new Label("Nutzer: ");
		assignUserToRetailerGrid.setWidget(1, 0, userLabel);
		assignUserToRetailerGrid.setWidget(1, 1, userListBox);
		userListBox.addChangeHandler(new UserListBoxChangeHandler());

		Label retailerLabel = new Label("Haendler: ");
		assignUserToRetailerGrid.setWidget(2, 0, retailerLabel);
		assignUserToRetailerGrid.setWidget(2, 1, retailerListBox);
		retailerListBox.addChangeHandler(new RetailerListBoxChangeHandler());

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		assignUserToRetailerGrid.setWidget(3, 1, actionButtonsPanel);

		saveButton.addClickHandler(new NewAllocationClickHandler());
		saveButton.setEnabled(true);
		actionButtonsPanel.add(saveButton);

		discardButton.addClickHandler(new DiscardClickhandler());
		discardButton.setEnabled(true);
		actionButtonsPanel.add(discardButton);

		mainPanel.add(assignUserToRetailerGrid);
		/**
		 * Zum Befüllen der Dropdown-Liste mit <code>User</code> Namen.
		 */
		shoppinglistAdministration.getUsersOf(groupToDisplay, new GetAllUsersOfGroupCallback());

		/**
		 * Befüllen der Dropdown-Liste mit <code>Retailer</code> Namen.
		 */
		shoppinglistAdministration.getAllRetailers(new GetAllRetailersCallback());
		// shoppinglistAdministration.getRetailersOf(shoppinglist, callback);
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

	/**
	 * Zum Befüllen der Dropdown-Liste mit <code>User</code> Namen.
	 */
	private class GetAllUsersOfGroupCallback implements AsyncCallback<ArrayList<User>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<User> userArrayList) {
			for (int i = 0; i < userArrayList.size(); i++) {
				userListBox.addItem(userArrayList.get(i).getName());
				selectedUser = userArrayList.get(0);
			}
		}

	}

	/**
	 * Befüllen der Dropdown-Liste mit <code>Retailer</code> Namen.
	 */
	private class GetAllRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Retailer> retailerArrayList) {
			for (int i = 0; i < retailerArrayList.size(); i++) {
				retailerListBox.addItem(retailerArrayList.get(i).getName());
				selectedRetailer = retailerArrayList.get(0);
			}
		}
	}

	/**
	 * ChangeHandler zum erkennen welches <code>User</code> Objekt der
	 * Dropdown-Liste ausgewählt wurde und dieses selectedUser zuordnen.
	 */
	private class UserListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int item = userListBox.getSelectedIndex();
			selectedUser = userArrayList.get(item);
		}
	}

	/**
	 * ChangeHandler zum erkennen welches <code>Retailer</code> Objekt der
	 * Dropdown-Liste ausgewählt wurde und dieses selectedRetailer zuordnen .
	 */
	private class RetailerListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int item = retailerListBox.getSelectedIndex();
			selectedRetailer = retailerArrayList.get(item);
		}
	}

	/**
	 * Clickhandler zum verwerfen der Eingaben und zur Rückkehr zur
	 * ShoppinglistShowForm.
	 * 
	 */
	private class DiscardClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			RootPanel.get("main").add(ssf);
		}

	}

	/**
	 * Clickhandler zum erstellen der Zuweisung.
	 * 
	 */
	private class NewAllocationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {

				User user = selectedUser;
				Retailer retailer = selectedRetailer;

				shoppinglistAdministration.assignUser(user, retailer, shoppinglistToDisplay,
						new CreateAllocationCallback());

			} else {
				Notification.show("Keine Shoppinglist ausgewaehlt");
			}
		}
	}

	/**
	 * Nach dem erfolgreichen Erstellen der Zuweisung wird das Formular geschlossen
	 * und die aktuell ausgewählte Shoppinglist erneut geöffnet.
	 * 
	 */
	private class CreateAllocationCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Anlegen eines neuen Eintrags ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			RootPanel.get("main").add(ssf);
		}
	}

}
