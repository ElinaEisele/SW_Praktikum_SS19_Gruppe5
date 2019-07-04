package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
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
 * Klasse zum Anzeigen eines Formulars, um die Zuweisungen zu Einzelhändlern von
 * einem Nutzer anzusehen und hinzuzufügen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class UserRetailerAllocationForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistHeader shoppinglistHeader = null;

	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;
	private User selectedUser = null;
	private Retailer selectedRetailer = null;

	private ArrayList<Retailer> assigndRetailers = new ArrayList<Retailer>();
	private ArrayList<User> userArrayList;
	private ArrayList<Retailer> retailerArrayList;

	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addAllocationPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Label userRetailerAllocationLabel = new Label("Wer kauft wo ein?");
	private Label headerLabel1 = new Label("Händler");
	private Label headerLabel2 = new Label("User");

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private FlexTable allocationFlexTable = new FlexTable();

	private ListBox retailerListBox = new ListBox();
	private ListBox userListBox = new ListBox();

	private Button backButton = new Button("Zurück");
	private Button saveButton = new Button("Speichern");
	private Button removeButton = null;

	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
	 * angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets
	 * bestimmt.
	 */
	public UserRetailerAllocationForm() {

		userRetailerAllocationLabel.setStyleName("UserRetailerAll");
		allocationFlexTable.setStyleName("FlexTable");
		addAllocationPanel.setStyleName("AllocationPanel");
		buttonPanel.setStyleName("ButtonPanel");
		
		addAllocationPanel.add(retailerListBox);
		addAllocationPanel.add(userListBox);
		buttonPanel.add(saveButton);
		buttonPanel.add(backButton);

		mainPanel.add(userRetailerAllocationLabel);
		mainPanel.add(allocationFlexTable);
		mainPanel.add(addAllocationPanel);
		mainPanel.add(buttonPanel);
		
		userListBox.addChangeHandler(new UserListBoxChangeHandler());
		retailerListBox.addChangeHandler(new RetailerListBoxChangeHandler());

		
		backButton.setStyleName("NavButton");
		backButton.addClickHandler(new BackClickhandler());

		
		saveButton.setStyleName("NavButton");
		saveButton.addClickHandler(new SaveClickHandler());

		headerLabel1.setStyleName("HeaderLabel");
		headerLabel2.setStyleName("HeaderLabel");
	}

	public void onLoad() {

		allocationFlexTable.setWidget(0, 0, headerLabel1);
		allocationFlexTable.setWidget(0, 1, headerLabel2);
		allocationFlexTable.setText(0, 2, "");

		shoppinglistAdministration.getAssignedRetailersOf(selectedShoppinglist, new AssignedRetailerCallback());

		shoppinglistAdministration.getUserRetailerAllocation(selectedShoppinglist,
				new UserRetailerAllocationCallback());

		/**
		 * Zum Befüllen der Dropdown-Liste mit <code>User</code>.
		 */
		selectedGroup = shoppinglistHeader.getSelected();
		shoppinglistAdministration.getUsersOf(selectedGroup, new GetAllUserCallback());
		
		shoppinglistAdministration.getRetailersOf(selectedShoppinglist, new GetAllRetailersCallback());
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
	 * Clickhandler zur Rückkehr zur ShoppinglistShowForm
	 * 
	 */
	private class BackClickhandler implements ClickHandler {

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
	 * Clickhandler zum Löschen der Zuweisung.
	 */
	private class RemoveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			int rowIndex = allocationFlexTable.getCellForEvent(event).getRowIndex();
			String retailerName = allocationFlexTable.getText(rowIndex, 0);

			for (int i = 0; i < assigndRetailers.size(); i++) {
				if (assigndRetailers.get(i).getName() == retailerName) {
					selectedRetailer = assigndRetailers.get(i);

				}
			}

			int removedIndex = assigndRetailers.indexOf(selectedRetailer);
			assigndRetailers.remove(removedIndex);
			allocationFlexTable.removeRow(rowIndex);

			shoppinglistAdministration.deleteAssignment(selectedRetailer, selectedShoppinglist,
					new DeleteAsssignmentCallback());

			retailerListBox.clear();
			shoppinglistAdministration.getRetailersOf(selectedShoppinglist, new GetAllRetailersCallback());

			for (int i = 0; i < assigndRetailers.size(); i++) {
				allocationFlexTable.removeAllRows();

			}
			allocationFlexTable.setText(0, 0, "Händler");
			allocationFlexTable.setText(0, 1, "User");
			allocationFlexTable.setText(0, 2, "Zuweisung löschen");

			shoppinglistAdministration.getUserRetailerAllocation(selectedShoppinglist,
					new UserRetailerAllocationCallback());

		}

	}

	/**
	 * ClickHandler zum Speichern von Zuweisungen
	 */
	private class SaveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {

				if (assigndRetailers.contains(selectedRetailer)) {
					Notification.show("Dieser Händler wurde schon einem Nutzer zugewiesen.");
					return;
				} else {
					assigndRetailers.add(selectedRetailer);
					shoppinglistAdministration.assignUser(selectedUser, selectedRetailer, selectedShoppinglist,
							new CreateAllocationCallback());
				}

			} else {
				Notification.show("Keine Einkaufsliste ausgewählt");
			}
		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ChangeHandler
	 * ***************************************************************************
	 */

	/**
	 * ChangeHandler zum erkennen welches <code>User</code> Objekt der
	 * Dropdown-Liste ausgewählt wurde und dieses selectedUser zuordnen
	 */
	private class UserListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int item = userListBox.getSelectedIndex();
			selectedUser = userArrayList.get(item);
		}
	}

	/**
	 * ChangeHandler zum erkennen welches <code>Retailer</code> Objekt der
	 * Dropdown-Liste ausgewählt wurde und dieses selectedRetailer zuordnen
	 */
	private class RetailerListBoxChangeHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {
			int item = retailerListBox.getSelectedIndex();
			selectedRetailer = retailerArrayList.get(item);
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
	private class GetAllUserCallback implements AsyncCallback<ArrayList<User>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
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
	 * Zum Befüllen der Drop-down Liste mit <code>Retailer</code>.
	 */
	private class GetAllRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			retailerArrayList = result;
			for (int i = 0; i < result.size(); i++) {
				retailerListBox.addItem(result.get(i).getName());
				selectedRetailer = result.get(0);
			}
		}
	}

	/**
	 * Zum Befüllen des FlexTable mit <code>Retailer</code>- und
	 * <code>User</user>-Objekten sowie hinzufügen eines Buttons zum löschen.
	 */
	private class CreateAllocationCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {

			Button removeButton = new Button("Entfernen");
			removeButton.setStyleName("NavButton2");
			removeButton.addClickHandler(new RemoveClickHandler());

			int row = allocationFlexTable.getRowCount();

			allocationFlexTable.setText(row, 0, selectedRetailer.getName());
			allocationFlexTable.setText(row, 1, selectedUser.getName());
			allocationFlexTable.setWidget(row, 2, removeButton);

		}

	}

	/**
	 * Zum Befüllen des FlexTable mit <code>Retailer</code>- und
	 * <code>User</user>-Objekten.
	 */
	private class AssignedRetailerCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {

			for (Retailer r : result) {
				assigndRetailers.add(r);

			}

		}

	}

	/**
	 * Zum Befüllen des FlexTable mit <code>Retailer</code>- und
	 * <code>User</user>-Objekten sowie hinzufügen eines Buttons zum löschen.
	 */
	private class UserRetailerAllocationCallback implements AsyncCallback<Map<String, String>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Map<String, String> result) {

			for (String key : result.keySet()) {
				removeButton = new Button("Entfernen");
				removeButton.setStyleName("NavButton2");
				removeButton.addClickHandler(new RemoveClickHandler());

				int row = allocationFlexTable.getRowCount();

				allocationFlexTable.setText(row, 0, key);
				allocationFlexTable.setText(row, 1, result.get(key));
				allocationFlexTable.setWidget(row, 2, removeButton);
				
//				ShoppinglistShowForm ssf = new ShoppinglistShowForm(shoppinglistHeader, UserRetailerAllocationForm.this);
//				RootPanel.get("main").clear();
//				RootPanel.get("main").add(ssf);
				
			}
			

		}

	}

	/**
	 * Erfolgreiches löschen der Zuweisung ohne Rückgabe
	 */
	private class DeleteAsssignmentCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {

		}

	}

}
