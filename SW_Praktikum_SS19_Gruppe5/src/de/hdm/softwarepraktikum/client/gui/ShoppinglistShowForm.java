package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.Window;
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
	// private ShoppinglistContent shoppinglistContent = null;
	private ShoppinglistCellTable shoppinglistCellTable;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Shoppinglist selectedShoppinglist;
	private Group selectedGroup;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();

	public ShoppinglistShowForm(ShoppinglistHeader sh, NewListitemForm nlf) {
		shoppinglistHeader = sh;
		mainPanel.add(nlf);
	}

	public ShoppinglistShowForm(ShoppinglistHeader sh, AssignUserToRetailerForm autrf) {
		shoppinglistHeader = sh;
		mainPanel.add(autrf);
	}

	public ShoppinglistShowForm(ShoppinglistHeader sh, EditShoppinglistNameForm esnf) {
		shoppinglistHeader = sh;
		sh.setGstvm(gstvm);
		esnf.setGstvm(gstvm);
		mainPanel.add(esnf);
	}

	public ShoppinglistShowForm(ShoppinglistHeader sh, ShowUserRetailerAllocationForm suraf) {
		shoppinglistHeader = sh;
		mainPanel.add(suraf);
	}

	public ShoppinglistShowForm(ShoppinglistHeader sh, ShoppinglistCellTable slct) {
		shoppinglistHeader = sh;
		mainPanel.add(slct);
	}

	public ShoppinglistShowForm() {
		shoppinglistHeader = new ShoppinglistHeader();
		shoppinglistCellTable = new ShoppinglistCellTable();

		shoppinglistHeader.setStylePrimaryName("shoppinglistHeader");
		// shoppinglistContent.setStylePrimaryName("shoppinglistContent");
		mainPanel.add(shoppinglistCellTable);
	}

	public void onLoad() {

		this.add(shoppinglistHeader);
		this.add(mainPanel);
		shoppinglistCellTable.setShoppinglistShowForm(ShoppinglistShowForm.this);
		shoppinglistCellTable.setShoppinglistToDisplay(selectedShoppinglist);
		shoppinglistHeader.setShoppinglistShowForm(ShoppinglistShowForm.this);
		shoppinglistHeader.setShoppinglistToDisplay(selectedShoppinglist);

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
