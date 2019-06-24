package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse fuer die Anordnung der Buttons im <code>ShoppinglistHeader</code>, der
 * in der <code>ShoppinglistShowForm</code> angezeigt wird.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistHeader extends HorizontalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	private GroupShoppinglistTreeViewModel gstvm;
	private ShoppinglistShowForm shoppinglistShowForm;
	private Shoppinglist shoppinglistToDisplay = null;
	private Group groupToDisplay = null;

	private Label shoppinglistHeaderLabel;
	private Button newListitem;
	private Button deleteShoppinglist;
	private Button editShoppinglistName;
	private Button showUserRetailerAllocation;
	private Button filter;

	public ShoppinglistHeader() {

		if (shoppinglistToDisplay == null) {
			shoppinglistHeaderLabel = new Label("Keine Shoppinglist ausgewaehlt");
		} else {
			shoppinglistHeaderLabel.setText(shoppinglistToDisplay.getName());
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

		Image deleteShoppinglistImg = new Image();
		deleteShoppinglistImg.setUrl("images/delete.png");
		deleteShoppinglistImg.setSize("30px", "30px");
		deleteShoppinglist.getElement().appendChild(deleteShoppinglistImg.getElement());
		deleteShoppinglist.setStyleName("ShoppinglistHeaderButton");
		deleteShoppinglist.addClickHandler(new DeleteShoppinglistClickHandler());

		Image editShoppinglistNameImg = new Image();
		editShoppinglistNameImg.setUrl("images/edit.png");
		editShoppinglistNameImg.setSize("30px", "30px");
		editShoppinglistName.getElement().appendChild(editShoppinglistNameImg.getElement());
		editShoppinglistName.setStyleName("ShoppinglistHeaderButton");
		editShoppinglistName.addClickHandler(new EditShoppinglistNameClickHandler());

		Image allocationImg = new Image();
		allocationImg.setUrl("images/collaboration.png");
		allocationImg.setSize("16px", "16px");
		showUserRetailerAllocation.getElement().appendChild(allocationImg.getElement());
		showUserRetailerAllocation.setStyleName("ShoppinglistHeaderButton");
		showUserRetailerAllocation.addClickHandler(new ShowUserRetailerAllocationClickHandler());

		Image filterImg = new Image();
		filterImg.setUrl("images/filter.png");
		filterImg.setSize("16px", "16px");
		filter.getElement().appendChild(filterImg.getElement());
		filter.addClickHandler(new FilterClickHandler());
		filter.setStyleName("ShoppinglistHeaderButton");

	}

	public void onLoad() {

		this.add(shoppinglistHeaderLabel);
		this.add(newListitem);
		this.add(editShoppinglistName);
		this.add(deleteShoppinglist);
		this.add(showUserRetailerAllocation);
		this.add(filter);

	}

	public Group getGroupToDisplay() {
		return groupToDisplay;
	}

	public void setGroupToDisplay(Group groupToDisplay) {
		this.groupToDisplay = groupToDisplay;
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
	public void setShoppinglistToDisplay(Shoppinglist s) {
		if (s != null) {
			shoppinglistToDisplay = s;
			shoppinglistHeaderLabel.setText(shoppinglistToDisplay.getName());

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
			groupToDisplay = g;

		} else {
			this.clear();
		}
	}

	public Group getSelected() {
		return groupToDisplay;
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
			if (shoppinglistToDisplay != null) {
				NewListitemForm nlf = new NewListitemForm();
				nlf.setGstvm(ShoppinglistHeader.this.gstvm);
				nlf.setShoppinglistHeader(ShoppinglistHeader.this);
				nlf.setShoppinglistToDisplay(shoppinglistToDisplay);
				nlf.setGroupToDisplay(groupToDisplay);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, nlf);
				ssf.setSelected(shoppinglistToDisplay);
				ssf.setSelectedGroup(groupToDisplay);

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
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
			if (shoppinglistToDisplay != null) {
				EditShoppinglistNameForm esndb = new EditShoppinglistNameForm();

				esndb.setGstvm(ShoppinglistHeader.this.gstvm);
				esndb.setShoppinglistHeader(ShoppinglistHeader.this);
				esndb.setShoppinglistToDisplay(shoppinglistToDisplay);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, esndb);
				ssf.setSelected(shoppinglistToDisplay);

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
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
			if (shoppinglistToDisplay != null) {
				DeleteShoppinglistDialogBox dsdb = new DeleteShoppinglistDialogBox();

				dsdb.setSelectedShoppinglist(shoppinglistToDisplay);
				dsdb.setSelectedGroup(groupToDisplay);
				dsdb.show();
			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
		}

	}

	private class ShowUserRetailerAllocationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (shoppinglistToDisplay != null) {
				ShowUserRetailerAllocationForm suraf = new ShowUserRetailerAllocationForm();

				suraf.setGstvm(ShoppinglistHeader.this.gstvm);
				suraf.setShoppinglistHeader(ShoppinglistHeader.this);
				suraf.setShoppinglistToDisplay(shoppinglistToDisplay);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, suraf);
				ssf.setSelected(shoppinglistToDisplay);

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
		}

	}

	private class FilterClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ShoppinglistFilterForm sff = new ShoppinglistFilterForm();
			sff.setShoppinglistHeader(ShoppinglistHeader.this);
			sff.setSelectedShoppinglist(shoppinglistToDisplay);
			sff.setSelectedGroup(groupToDisplay);
			ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, sff);
			ssf.setSelected(shoppinglistToDisplay);
			ssf.setSelectedGroup(groupToDisplay);

		}

	}

}
