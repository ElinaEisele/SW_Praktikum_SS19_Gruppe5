package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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
 * Klasse zur Darstellung eines Formulars, um Einträge nach Nutzer oder Händler
 * zu filtern
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class ShoppinglistFilterForm extends VerticalPanel {

	ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();

	private ShoppinglistHeader shoppinglistHeader = null;

	private Shoppinglist selectedShoppinglist = null;
	private Retailer selectedRetailer = null;
	private User selectedUser = null;
	private Group selectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel filterPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Label infoLabel = new Label("Filter deine Einkaufsliste!");
	private ListBox filterOptionsListBox = new ListBox();
	private ListBox filterDetailsListBox = new ListBox();
	private Button saveButton = new Button();
	private Button backButton = new Button();
	private String selectedOption = null;

	private ArrayList<Retailer> retailerArrayList = null;
	private ArrayList<User> userArrayList = null;

	public ShoppinglistFilterForm() {

		filterOptionsListBox.addItem("Händler");
		filterOptionsListBox.addItem("Nutzer");

		filterOptionsListBox.addChangeHandler(new OptionsChangeHandler());
		filterDetailsListBox.addChangeHandler(new DetailsChangeHandler());

		filterPanel.add(filterOptionsListBox);
		filterPanel.add(filterDetailsListBox);

		Image DiscardImg = new Image();
		DiscardImg.setUrl("images/cancel.png");
		DiscardImg.setSize("16px", "16px");
		backButton.getElement().appendChild(DiscardImg.getElement());
		backButton.setStyleName("ShoppinglistHeaderButton");
		backButton.addClickHandler(new CancelClickHandler());

		Image SaveImg = new Image();
		SaveImg.setUrl("images/tick.png");
		SaveImg.setSize("16px", "16px");
		saveButton.getElement().appendChild(SaveImg.getElement());
		saveButton.setStyleName("ShoppinglistHeaderButton");
		saveButton.addClickHandler(new SaveClickHandler());

		buttonPanel.add(saveButton);
		buttonPanel.add(backButton);

		mainPanel.add(infoLabel);
		mainPanel.add(filterPanel);
		mainPanel.add(buttonPanel);

	}

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * 
	 */
	public void onLoad() {

		shoppinglistAdministration.getRetailersOf(selectedShoppinglist, new RetailersCallback());

		this.add(mainPanel);

	}

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
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
	 * ClickHandler speichern der Filter Auswahl und Öffnen des
	 * <code>FilteredShoppinglistCellTable</code>
	 */
	private class SaveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedOption == "Händler") {

				RootPanel.get("main").clear();

				shoppinglistHeader.setSelectedShoppinglist(selectedShoppinglist);
				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setShoppinglistHeader(shoppinglistHeader);
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				ssf.setSelectedRetailer(selectedRetailer);

				RootPanel.get("main").add(ssf);

			} else if (selectedOption == "Nutzer") {

				RootPanel.get("main").clear();

				shoppinglistHeader.setSelectedShoppinglist(selectedShoppinglist);

				FilteredShoppinglistCellTable fsct = new FilteredShoppinglistCellTable();

				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				ssf.setShoppinglistHeader(shoppinglistHeader);
				ssf.setFilteredshoppinglistCellTable(fsct);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);
				ssf.setSelectedUser(selectedUser);

				RootPanel.get("main").add(ssf);

			}
		}

	}

	/**
	 * ClickHandler zum Schließen der Form und erneutem Öffnen der
	 * <code>ShoppinglistShowForm</code>
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(selectedShoppinglist);
			ssf.setSelectedGroup(selectedGroup);
			RootPanel.get("main").add(ssf);
		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ChangeHandler
	 * ***************************************************************************
	 */

	/**
	 * ChangeHandler zum erkennen welche Filter Auswahl der Dropdown-Liste
	 * ausgewählt wurde
	 */
	private class OptionsChangeHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {
			int item = filterOptionsListBox.getSelectedIndex();
			if (item == 0) {
				filterDetailsListBox.clear();

				shoppinglistAdministration.getRetailersOf(selectedShoppinglist, new RetailersCallback());
			}
			if (item == 1) {
				filterDetailsListBox.clear();
				shoppinglistAdministration.getAssigndUserOf(selectedShoppinglist, new UsersCallback());
			}

		}

	}

	/**
	 * ChangeHandler zum erkennen welche Filter Auswahl der Dropdown-Liste
	 * ausgewählt wurde
	 */
	private class DetailsChangeHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {
			int item = filterOptionsListBox.getSelectedIndex();
			if (item == 0) {
				int item2 = filterDetailsListBox.getSelectedIndex();
				selectedRetailer = retailerArrayList.get(item2);

			} else if (item == 1) {
				int item2 = filterDetailsListBox.getSelectedIndex();
				selectedUser = userArrayList.get(item2);
			}

		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */
	
	/**
	 * Zum Befüllen der Dropdown-Liste mit <code>User</code>.
	 */
	private class UsersCallback implements AsyncCallback<ArrayList<User>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<User> result) {

			userArrayList = result;
			for (int i = 0; i < result.size(); i++) {
				filterDetailsListBox.addItem(result.get(i).getName());
				selectedUser = result.get(0);
				selectedOption = "Nutzer";
			}
		}

	}

	/**
	 * Zum Befüllen der Dropdown-Liste mit <code>Retailer</code>.
	 */
	private class RetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			retailerArrayList = result;
			for (int i = 0; i < result.size(); i++) {
				filterDetailsListBox.addItem(result.get(i).getName());
				selectedRetailer = result.get(0);
				selectedOption = "Händler";

			}
		}

	}

}
