package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

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
	private Retailer selectedRetailer = null;
	private User selectedUser = null;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();

	public ListitemShowForm(ListitemHeader lh, ListitemForm lf) {
		listitemHeader = lh;
		mainPanel.add(listitemHeader);
		mainPanel.add(lf);
		RootPanel.get("main").clear();
		RootPanel.get("main").add(mainPanel);
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

		listitemHeader.setListitemShowForm(ListitemShowForm.this);
		listitemHeader.setShoppinglistToDisplay(selectedShoppinglist);
		listitemHeader.setListitemToDisplay(selectedListitem);
		listitemHeader.setSelectedGroup(selectedGroup);
		listitemForm.setListitemShowForm(ListitemShowForm.this);
		listitemForm.setGroupToDisplay(selectedGroup);
		listitemForm.setShoppinglistToDisplay(selectedShoppinglist);
		listitemForm.setSelectedListitem(selectedListitem);
		if(selectedUser != null) {
			listitemForm.setSelectedUser(selectedUser);
		}
		if (selectedRetailer !=null) {
			listitemForm.setSelectedRetailer(selectedRetailer);
		}

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

	public Listitem getSelectedListitem() {
		return selectedListitem;
	}

	public void setSelectedListitem(Listitem selectedListitem) {
		this.selectedListitem = selectedListitem;
	}

	public Retailer getSelectedRetailer() {
		return selectedRetailer;
	}

	public void setSelectedRetailer(Retailer selectedRetailer) {
		selectedUser = null;
		this.selectedRetailer = selectedRetailer;	
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		selectedRetailer = null;
		this.selectedUser = selectedUser;
	}

}
