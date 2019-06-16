package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung eines Shoppinglist-Listitems.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class ListitemShowForm extends VerticalPanel {
	private ListitemHeader listitemHeader = null;
	private ListitemForm listitemForm;
	private ShoppinglistCellTable shoppinglistCellTable;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Listitem selectedListitem;
	private Shoppinglist selectedShoppinglist;
	private Group selectedGroup = null;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();

	public ListitemShowForm(ListitemHeader lh, ListitemForm lf) {
		listitemHeader = lh;
		lf.setSelectedListitem(selectedListitem);
		lf.setShoppinglistToDisplay(selectedShoppinglist);
		mainPanel.add(lf);

	}

	public ListitemShowForm(ListitemHeader lh, NewRetailerForm nrf) {
		listitemHeader = lh;
		mainPanel.add(nrf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);

	}

	public ListitemShowForm() {

		listitemHeader = new ListitemHeader();
		listitemForm = new ListitemForm();

		listitemHeader.setStylePrimaryName("listitemHeader");

		mainPanel.add(listitemForm);
	}

	public void onLoad() {

		listitemForm.setListitemShowForm(ListitemShowForm.this);
		listitemForm.setShoppinglistToDisplay(selectedShoppinglist);
		listitemForm.setSelectedListitem(selectedListitem);
		listitemHeader.setListitemShowForm(ListitemShowForm.this);
		listitemHeader.setShoppinglistToDisplay(selectedShoppinglist);
		listitemHeader.setListitemToDisplay(selectedListitem);
		listitemHeader.setSelectedGroup(selectedGroup);

		this.add(listitemHeader);
		this.add(mainPanel);

	}

	public void setSelected(Listitem l) {
		selectedListitem = l;

	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public ListitemHeader getListitemHeader() {
		return listitemHeader;
	}

	public void setListitemHeader(ListitemHeader listitemHeader) {
		this.listitemHeader = listitemHeader;
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

}
