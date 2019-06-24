package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Uebersicht einer Shoppingliste mit einem Header und mit der
 * Anzeige des Inhalt der Shoppingliste.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistShowForm extends VerticalPanel {

	private ShoppinglistHeader shoppinglistHeader = null;
	private FilteredShoppinglistCellTable filteredshoppinglistCellTable = null;
	private ShoppinglistCellTable shoppinglistCellTable;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Retailer selectedRetailer = null;
	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;
	private User selectedUser = null;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();

	public ShoppinglistShowForm(ShoppinglistHeader sh, NewListitemForm nlf) {
		shoppinglistHeader = sh;
		mainPanel.add(shoppinglistHeader);
		mainPanel.add(nlf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);

	}

	public ShoppinglistShowForm(ShoppinglistHeader sh, EditShoppinglistNameForm esnf) {
		shoppinglistHeader = sh;
		sh.setGstvm(gstvm);
		esnf.setGstvm(gstvm);
		mainPanel.add(shoppinglistHeader);
		mainPanel.add(esnf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public ShoppinglistShowForm(ShoppinglistHeader sh, ShowUserRetailerAllocationForm suraf) {
		shoppinglistHeader = sh;
		mainPanel.add(shoppinglistHeader);
		mainPanel.add(suraf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}
	
	public ShoppinglistShowForm(ShoppinglistHeader sh, FilteredShoppinglistCellTable fsct) {
		shoppinglistHeader = sh;
		shoppinglistHeader.setSelected(selectedGroup);
		shoppinglistHeader.setShoppinglistToDisplay(selectedShoppinglist);		
		filteredshoppinglistCellTable = fsct;		
		mainPanel.add(shoppinglistHeader);
		mainPanel.add(fsct);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public ShoppinglistShowForm(ShoppinglistHeader sh, NewRetailerForm nrf) {
		shoppinglistHeader = sh;
		mainPanel.add(nrf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}
	
	public ShoppinglistShowForm(ShoppinglistHeader sh, ShoppinglistFilterForm sff) {
		shoppinglistHeader = sh;
		mainPanel.add(sh);

		mainPanel.add(sff);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public ShoppinglistShowForm() {
		shoppinglistHeader = new ShoppinglistHeader();
		shoppinglistCellTable = new ShoppinglistCellTable();
		filteredshoppinglistCellTable = new FilteredShoppinglistCellTable();

		shoppinglistHeader.setStylePrimaryName("shoppinglistHeader");

		mainPanel.add(shoppinglistCellTable);
	}

	public void onLoad() {
		
		this.clear();
		
		if(shoppinglistCellTable != null) {
			shoppinglistCellTable.setShoppinglistShowForm(ShoppinglistShowForm.this);
			shoppinglistCellTable.setShoppinglistToDisplay(selectedShoppinglist);
			shoppinglistCellTable.setSelectedGroup(selectedGroup);
			shoppinglistHeader.setShoppinglistShowForm(ShoppinglistShowForm.this);
			shoppinglistHeader.setShoppinglistToDisplay(selectedShoppinglist);
			shoppinglistHeader.setGroupToDisplay(selectedGroup);

			ShoppinglistSearchBar ssb = new ShoppinglistSearchBar();
			ssb.setSelectedShoppinglist(selectedShoppinglist);
			ssb.setSelectedGroup(selectedGroup);
			ssb.setShoppinglistHeader(shoppinglistHeader);
			
			this.add(shoppinglistHeader);
			this.add(ssb);
			
		}else if (filteredshoppinglistCellTable != null) {

			filteredshoppinglistCellTable.setShoppinglistShowForm(ShoppinglistShowForm.this);
			filteredshoppinglistCellTable.setShoppinglistToDisplay(selectedShoppinglist);
			filteredshoppinglistCellTable.setSelectedGroup(selectedGroup);
			filteredshoppinglistCellTable.setSelectedUser(selectedUser);
			filteredshoppinglistCellTable.setSelectedRetailer(selectedRetailer);
			
		} else {
			Window.alert("kein CellTable");
		}

		this.add(mainPanel);

	}

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
	}

	public FilteredShoppinglistCellTable getFilteredshoppinglistCellTable() {
		return filteredshoppinglistCellTable;
	}

	public void setFilteredshoppinglistCellTable(FilteredShoppinglistCellTable filteredshoppinglistCellTable) {
		this.filteredshoppinglistCellTable = filteredshoppinglistCellTable;
		shoppinglistCellTable = null;
		mainPanel.clear();
		mainPanel.add(filteredshoppinglistCellTable);
	}


	public void setSelected(Shoppinglist s) {
		selectedShoppinglist = s;

	}

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public Retailer getSelectedRetailer() {
		return selectedRetailer;
	}

	public void setSelectedRetailer(Retailer selectedRetailer) {
		selectedUser = null;
		this.selectedRetailer = selectedRetailer;		
		filteredshoppinglistCellTable.setSelectedRetailer(selectedRetailer);
		
	}


	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		selectedRetailer = null;
		this.selectedUser = selectedUser;
		filteredshoppinglistCellTable.setSelectedUser(selectedUser);

	}
	
	

}
