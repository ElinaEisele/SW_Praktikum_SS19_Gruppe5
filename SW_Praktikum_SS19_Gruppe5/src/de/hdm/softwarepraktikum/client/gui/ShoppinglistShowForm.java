package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Uebersicht einer Shoppingliste mit einem Header und mit der
 * Anzeige des Inhalt der Shoppingliste.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistShowForm extends VerticalPanel {

	private ShoppinglistHeader shoppinglistHeader = null;
	private ShoppinglistCellTable shoppinglistCellTable;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Shoppinglist selectedShoppinglist;
	private Group selectedGroup;
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

	public ShoppinglistShowForm(ShoppinglistHeader sh, ShoppinglistCellTable slct) {
		shoppinglistHeader = sh;
		mainPanel.add(slct);
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
		mainPanel.add(shoppinglistHeader);
		mainPanel.add(sff);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
	}

	public ShoppinglistShowForm() {
		shoppinglistHeader = new ShoppinglistHeader();
		shoppinglistCellTable = new ShoppinglistCellTable();

		shoppinglistHeader.setStylePrimaryName("shoppinglistHeader");

		mainPanel.add(shoppinglistCellTable);
	}

	public void onLoad() {

		shoppinglistCellTable.setShoppinglistShowForm(ShoppinglistShowForm.this);
		shoppinglistCellTable.setShoppinglistToDisplay(selectedShoppinglist);
		shoppinglistCellTable.setSelectedGroup(selectedGroup);
		shoppinglistHeader.setShoppinglistShowForm(ShoppinglistShowForm.this);
		shoppinglistHeader.setShoppinglistToDisplay(selectedShoppinglist);
		shoppinglistHeader.setGroupToDisplay(selectedGroup);

		this.add(shoppinglistHeader);
		this.add(mainPanel);

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

}
