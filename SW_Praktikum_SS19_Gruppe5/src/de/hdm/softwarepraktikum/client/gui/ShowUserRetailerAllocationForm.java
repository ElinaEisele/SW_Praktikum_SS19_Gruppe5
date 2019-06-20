package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
	private ArrayList<Retailer> assigndRetailers  = new ArrayList<Retailer>();
	
	private Shoppinglist shoppinglistToDisplay = null;
	private Group groupToDisplay = null;
	private User selectedUser = null;
	private Retailer selectedRetailer = null;

	private ArrayList<User> userArrayList;
	private ArrayList<Retailer> retailerArrayList;
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addAllocationPanel = new HorizontalPanel();
	private Label userRetailerAllocationLabel = new Label("Wer kauft wo ein?");

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private FlexTable allocationFlexTable = new FlexTable();

	private ListBox retailerListBox = new ListBox();
	private ListBox userListBox = new ListBox();

	private Button backButton = new Button("zurueck");
	private Button saveButton = new Button("Hinzufügen");
	private Button removeButton = null;


	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
	 * angeordnet, dessen Gr��e sich aus dem Platzbedarf der enthaltenen Widgets
	 * bestimmt.
	 */
	public ShowUserRetailerAllocationForm() {
		
		addAllocationPanel.add(retailerListBox);
		addAllocationPanel.add(userListBox);
		addAllocationPanel.add(saveButton);
		addAllocationPanel.add(backButton);
		
		mainPanel.add(userRetailerAllocationLabel);
		mainPanel.add(allocationFlexTable);
		mainPanel.add(addAllocationPanel);


		userListBox.addChangeHandler(new UserListBoxChangeHandler());
		retailerListBox.addChangeHandler(new RetailerListBoxChangeHandler());
		
		backButton.addClickHandler(new BackClickhandler());
		backButton.setEnabled(true);
		saveButton.addClickHandler(new SaveClickHandler());

	}

	public void onLoad() {
			
		allocationFlexTable.setText(0, 0, "Händler");
		allocationFlexTable.setText(0, 1, "User");
		allocationFlexTable.setText(0, 2, "Zuweisung löschen");
		
		shoppinglistAdministration.getAssigndRetailersOf(shoppinglistToDisplay, new AssigndRetailerCallback());
		
		shoppinglistAdministration.getUserRetailerAllocation(shoppinglistToDisplay, new UserRetailerAllocationCallback());
				
		/**
		 * Zum Bef�llen der Dropdown-Liste mit <code>User</code>.
		 */
		groupToDisplay = shoppinglistHeader.getGroupToDisplay();

		shoppinglistAdministration.getUsersOf(groupToDisplay, new GetAllUserCallback());
		shoppinglistAdministration.getAllRetailers(new GetAllRetailersCallback());
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
	 * Zum Befüllen der Drop-down Liste mit <code>Retailer</code>.
	 */
	private class GetAllRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

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
	 * ChangeHandler zum erkennen welches <code>User</code> Objekt der
	 * Dropdown-Liste ausgew�hlt wurde und dieses selectedUser zuordnen .
	 */
	private class UserListBoxChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent event) {
			int item = userListBox.getSelectedIndex();
			selectedUser = userArrayList.get(item);
		}
	}
	
	private class RetailerListBoxChangeHandler implements ChangeHandler{

		@Override
		public void onChange(ChangeEvent event) {
			int item = retailerListBox.getSelectedIndex();
			selectedRetailer = retailerArrayList.get(item);
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
	 * Clickhandler zum Löschen der Zuweisung.
	 */
	private class RemoveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
		
			int rowIndex = allocationFlexTable.getCellForEvent(event).getRowIndex();
			String retailerName = allocationFlexTable.getText(rowIndex, 0);
						
			for (int i = 0; i<assigndRetailers.size(); i++) {
				if (assigndRetailers.get(i).getName() == retailerName) {
					selectedRetailer = assigndRetailers.get(i);
					
				}
			}
						
			int removedIndex = assigndRetailers.indexOf(selectedRetailer);		
			assigndRetailers.remove(removedIndex);
			allocationFlexTable.removeRow(rowIndex);
						
			shoppinglistAdministration.deleteAssignment(selectedRetailer, shoppinglistToDisplay, new DeleteAsssignmentCallback());

			retailerListBox.clear();
			shoppinglistAdministration.getAllRetailers(new GetAllRetailersCallback());
			
			for (int i = 0; i < assigndRetailers.size(); i++) {
				allocationFlexTable.removeAllRows();
			
			}
			allocationFlexTable.setText(0, 0, "Händler");
			allocationFlexTable.setText(0, 1, "User");
			allocationFlexTable.setText(0, 2, "Zuweisung löschen");
			
			shoppinglistAdministration.getUserRetailerAllocation(shoppinglistToDisplay, new UserRetailerAllocationCallback());

		
		}
	
	}

	/**
	 * ClickHandler zum Speichern von Zuweisungen
	 */
	private class SaveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				
				if (assigndRetailers.contains(selectedRetailer)) {
					Notification.show("Dieser Händler wurde schon einem Nutzer zugewiesen.");
					return;
				} else{
					assigndRetailers.add(selectedRetailer);
					shoppinglistAdministration.assignUser(selectedUser, selectedRetailer, shoppinglistToDisplay,
							new CreateAllocationCallback());
				}
				
				
				

			} else {
				Notification.show("Keine Shoppinglist ausgewaehlt");
			}
		}
		
	}
	
	private class CreateAllocationCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Anlegen einer neuen Zuweisung ist fehlgeschlagen!");

		}

		@Override
		public void onSuccess(Void result) {
			
			Button removeButton = new Button("löschen");
			removeButton.addClickHandler(new RemoveClickHandler());
			
			int row = allocationFlexTable.getRowCount();
			
			allocationFlexTable.setText(row, 0, selectedRetailer.getName());
			allocationFlexTable.setText(row, 1, selectedUser.getName());
			allocationFlexTable.setWidget(row, 2, removeButton);
			
		}
		
	}
	
	private class AssigndRetailerCallback implements AsyncCallback<ArrayList<Retailer>>{

		@Override
		public void onFailure(Throwable caught) {
			// 
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			
			
			for (Retailer r : result) {
				assigndRetailers.add(r);

			}
			
			
		}
		
	}
	
	
	private class UserRetailerAllocationCallback implements AsyncCallback<Map<String, String>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Map<String, String> result) {
			
			for (String key : result.keySet()) {
				removeButton = new Button("löschen");
				removeButton.addClickHandler(new RemoveClickHandler());

				int row = allocationFlexTable.getRowCount();
				
				allocationFlexTable.setText(row, 0, key);
				allocationFlexTable.setText(row, 1, result.get(key));
				allocationFlexTable.setWidget(row, 2, removeButton);
				
			}
			
		}
		
	}
	
	private class DeleteAsssignmentCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			
		}
		
	}
		

}
