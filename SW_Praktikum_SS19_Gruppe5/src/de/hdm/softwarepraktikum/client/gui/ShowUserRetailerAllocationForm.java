package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
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

/**
 * Klasse zum Anzeigen eines Formulars, um die Zuweisungen zu Einzelh�ndlern von
 * einem Nutzer anzusehen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class ShowUserRetailerAllocationForm extends VerticalPanel {
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistHeader shoppinglistHeader;
	
	private Shoppinglist shoppinglistToDisplay = null;
	private Group groupToDisplay = null;
	private User selectedUser = null;

	private ArrayList<User> userArrayList;
	private ArrayList<Retailer >retailerArrayList;
	private VerticalPanel mainPanel = new VerticalPanel();
	private Grid shoppinglistGrid;

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private FlexTable userRetailerAllocationFlexTable = new FlexTable();

	private ListBox userListBox = new ListBox();

	private Button showButton = new Button("Anzeigen");
	private Button backButton = new Button("zurueck");

	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
	 * angeordnet, dessen Gr��e sich aus dem Platzbedarf der enthaltenen Widgets
	 * bestimmt.
	 */
	public ShowUserRetailerAllocationForm() {

		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		shoppinglistGrid = new Grid(4, 2);
		mainPanel.add(shoppinglistGrid);
		mainPanel.add(userRetailerAllocationFlexTable);

		Label showUserRetailerAllocationLabel = new Label("Zuweisung anzeigen fuer:");
		shoppinglistGrid.setWidget(0, 0, showUserRetailerAllocationLabel);

		Label userLabel = new Label("Benutzername: ");
		shoppinglistGrid.setWidget(1, 0, userLabel);
		shoppinglistGrid.setWidget(1, 1, userListBox);
		userListBox.addChangeHandler(new UserListBoxChangeHandler());

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		shoppinglistGrid.setWidget(2, 1, actionButtonsPanel);

		showButton.addClickHandler(new ShowAllocationClickHandler());
		showButton.setEnabled(true);
		actionButtonsPanel.add(showButton);

		backButton.addClickHandler(new BackClickhandler());
		backButton.setEnabled(true);
		actionButtonsPanel.add(backButton);

		

	}

	public void onLoad() {
		
		/**
		 * Zum Bef�llen der Dropdown-Liste mit <code>User</code>.
		 */
		groupToDisplay = shoppinglistHeader.getGroupToDisplay();

		shoppinglistAdministration.getUsersOf(groupToDisplay, new GetAllUserCallback());
		
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
	 * Zum Bef�llen der Dropdown-Liste mit <code>User</code>.
	 */
	private class GetAllUserCallback implements AsyncCallback<ArrayList<User>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<User> result) {
			
			userArrayList = result;
			
			for (int i = 0; i < result.size(); i++) {
				userListBox.addItem(result.get(i).getName());
				selectedUser = result.get(0);
			}

		}

	}

	/**
	 * ChangeHandler zum erkennen welches <code>User</code> Objekt der
	 * Dropdown-Liste ausgew�hlt wurde und dieses selectedUser zuordnen .
	 */
	private class UserListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int item = userListBox.getSelectedIndex();
			selectedUser = userArrayList.get(item);
		}
	}

	/**
	 * Clickhandler zur R�ckkehr zur ShoppinglistShowForm
	 * 
	 */
	private class BackClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(shoppinglistToDisplay);
			ssf.setSelectedGroup(groupToDisplay);
			RootPanel.get("main").add(ssf);
		}

	}

	/**
	 * Clickhandler zum Anzeigen der Zuweisung.
	 * 
	 */
	private class ShowAllocationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
								
				shoppinglistAdministration.getRetailersOf(shoppinglistToDisplay, selectedUser,
						new ShowAllocationCallback());
				

			} else {
				Notification.show("Es wurde keine Gruppe ausgewaehlt.");
			}
		}

	}

	/**
	 * Anzeige der Zuweisung.
	 * 
	 */
	private class ShowAllocationCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Anzeigen ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			
			retailerArrayList = result;
						
			userRetailerAllocationFlexTable.setText(0, 0, "User");
			userRetailerAllocationFlexTable.setText(0, 1, "Haendler");
			
			userRetailerAllocationFlexTable.setText(1, 0, "Kein Nutzer ausgewaehlt");
			userRetailerAllocationFlexTable.setText(1, 1, "Kein Haendler zugwiesen");
			userRetailerAllocationFlexTable.setText(1, 0, selectedUser.getName());

			
			int i = 1;
			for (Retailer r : retailerArrayList) {
				userRetailerAllocationFlexTable.setText(i, 1, r.getName());
				i++;
			}
			
			for (Retailer r : retailerArrayList) {
				userRetailerAllocationFlexTable.setText(i, 0, null);
				userRetailerAllocationFlexTable.setText(i, 1, null);
				i++;
			}
			
			retailerArrayList = null;
			
			
		}
	}

}
