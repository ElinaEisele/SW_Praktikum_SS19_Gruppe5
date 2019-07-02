package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse für die Anordnung der Buttons im <code>ShoppinglistHeader</code>, der
 * in der <code>ShoppinglistShowForm</code> angezeigt wird.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistHeader extends HorizontalPanel {

	private GroupShoppinglistTreeViewModel gstvm = null;
	private ShoppinglistShowForm shoppinglistShowForm = null;

	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;

	private Label shoppinglistHeaderLabel = null;
	private Button newListitem = null;
	private Button deleteShoppinglist = null;
	private Button editShoppinglistName = null;
	private Button showUserRetailerAllocation = null;
	private Button filter = null;

	public ShoppinglistHeader() {

		if (selectedShoppinglist == null) {
			shoppinglistHeaderLabel = new Label("Keine Shoppinglist ausgewählt");
		} else {
			shoppinglistHeaderLabel.setText(selectedShoppinglist.getName());
		}

		shoppinglistHeaderLabel.setStyleName("ListLabel");

		newListitem = new Button();
		deleteShoppinglist = new Button();
		editShoppinglistName = new Button();
		showUserRetailerAllocation = new Button();
		filter = new Button();

		Image newListitemImg = new Image();
		newListitemImg.setUrl("images/shopping-cart.png");
		newListitemImg.setSize("30px", "30px");
		newListitem.getElement().appendChild(newListitemImg.getElement());
		newListitem.setStyleName("ShoppinglistHeaderButton");
		newListitem.addClickHandler(new NewListitemClickHandler());
		newListitem.setTitle("Eintrag hinzufügen");

		Image deleteShoppinglistImg = new Image();
		deleteShoppinglistImg.setUrl("images/delete.png");
		deleteShoppinglistImg.setSize("30px", "30px");
		deleteShoppinglist.getElement().appendChild(deleteShoppinglistImg.getElement());
		deleteShoppinglist.setStyleName("ShoppinglistHeaderButton");
		deleteShoppinglist.addClickHandler(new DeleteShoppinglistClickHandler());
		deleteShoppinglist.setTitle("Einkaufsliste löschen");
		

		Image editShoppinglistNameImg = new Image();
		editShoppinglistNameImg.setUrl("images/edit.png");
		editShoppinglistNameImg.setSize("30px", "30px");
		editShoppinglistName.getElement().appendChild(editShoppinglistNameImg.getElement());
		editShoppinglistName.setStyleName("ShoppinglistHeaderButton");
		editShoppinglistName.addClickHandler(new EditShoppinglistNameClickHandler());
		editShoppinglistName.setTitle("Einkauflistenname ändern");

		Image allocationImg = new Image();
		allocationImg.setUrl("images/collaboration.png");
		allocationImg.setSize("30px", "30px");
		showUserRetailerAllocation.getElement().appendChild(allocationImg.getElement());
		showUserRetailerAllocation.setStyleName("ShoppinglistHeaderButton");
		showUserRetailerAllocation.addClickHandler(new ShowUserRetailerAllocationClickHandler());
		showUserRetailerAllocation.setTitle("Einzelhändlerzuweisung verwalten");


		Image filterImg = new Image();
		filterImg.setUrl("images/filter.png");
		filterImg.setSize("30px", "30px");
		filter.getElement().appendChild(filterImg.getElement());
		filter.addClickHandler(new FilterClickHandler());
		filter.setStyleName("ShoppinglistHeaderButton");
		filter.setTitle("Einkaufsliste filtern");
	}

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * 
	 */
	public void onLoad() {

		this.add(shoppinglistHeaderLabel);
		this.add(newListitem);
		this.add(editShoppinglistName);
		this.add(deleteShoppinglist);
		this.add(showUserRetailerAllocation);
		this.add(filter);

	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public ShoppinglistShowForm getShoppinglistShowForm() {
		return shoppinglistShowForm;
	}

	public void setShoppinglistShowForm(ShoppinglistShowForm shoppinglistShowForm) {
		this.shoppinglistShowForm = shoppinglistShowForm;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	/**
	 * Sobald eine <code>Shoppinglist</code> ausgewaehlt wird das Label mit den
	 * entsprechenden Informationen bef�llt.
	 * 
	 * @param s, das zu setzende <code>Shoppinglist</code> Objekt.
	 */
	public void setSelectedShoppinglist(Shoppinglist s) {
		if (s != null) {
			selectedShoppinglist = s;
			shoppinglistHeaderLabel.setText(selectedShoppinglist.getName());

		} else {
			this.clear();
		}
	}

	/**
	 * Sobald eine <code>Group</code> ausgewaehlt wird, wird das Label mit dem
	 * Gruppenname befuellt.
	 * 
	 * @param g das zu setzende <code>Group</code> Objekt.
	 */
	public void setSelected(Group g) {
		if (g != null) {
			selectedGroup = g;

		} else {
			this.clear();
		}
	}

	public Group getSelected() {
		return selectedGroup;
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * ClickHandler dient dem Erzeugen einer <code>NewListitemForm</code> Instanz.
	 */
	private class NewListitemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				NewListitemForm nlf = new NewListitemForm();
				nlf.setGstvm(ShoppinglistHeader.this.gstvm);
				nlf.setShoppinglistHeader(ShoppinglistHeader.this);
				nlf.setSelectedShoppinglist(selectedShoppinglist);
				nlf.setSelectedGroup(selectedGroup);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, nlf);
				ssf.setSelected(selectedShoppinglist);
				ssf.setSelectedGroup(selectedGroup);

			} else {
				Notification.show("Es wurde keine Einkaufsliste ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem Erzeugen einer <code>EditShoppinglistNameForm</code>
	 * Instanz.
	 */
	private class EditShoppinglistNameClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				EditShoppinglistNameForm esndb = new EditShoppinglistNameForm();

				esndb.setGstvm(ShoppinglistHeader.this.gstvm);
				esndb.setShoppinglistHeader(ShoppinglistHeader.this);
				esndb.setSelectedShoppinglist(selectedShoppinglist);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, esndb);
				ssf.setSelected(selectedShoppinglist);

			} else {
				Notification.show("Es wurde keine Einkaufsliste ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem Erzeugen einer
	 * <code>DeleteShoppinglistDialogBox</code> Instanz.
	 */
	private class DeleteShoppinglistClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				DeleteShoppinglistDialogBox dsdb = new DeleteShoppinglistDialogBox();

				dsdb.setSelectedShoppinglist(selectedShoppinglist);
				dsdb.setSelectedGroup(selectedGroup);
				dsdb.show();
			} else {
				Notification.show("Es wurde keine Einkaufsliste ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem Erzeugen einer <code>UserRetailerAllocationForm</code>
	 * Instanz.
	 */
	private class ShowUserRetailerAllocationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedShoppinglist != null) {
				UserRetailerAllocationForm suraf = new UserRetailerAllocationForm();

				suraf.setGstvm(ShoppinglistHeader.this.gstvm);
				suraf.setShoppinglistHeader(ShoppinglistHeader.this);
				suraf.setSelectedShoppinglist(selectedShoppinglist);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, suraf);
				ssf.setSelected(selectedShoppinglist);

			} else {
				Notification.show("Es wurde keine Einkaufsliste ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem Erzeugen einer <code>ShoppinglistFilterForm</code>
	 * Instanz.
	 */
	private class FilterClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ShoppinglistFilterForm sff = new ShoppinglistFilterForm();
			sff.setShoppinglistHeader(ShoppinglistHeader.this);
			sff.setSelectedShoppinglist(selectedShoppinglist);
			sff.setSelectedGroup(selectedGroup);
			ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, sff);
			ssf.setSelected(selectedShoppinglist);
			ssf.setSelectedGroup(selectedGroup);

		}

	}

}
