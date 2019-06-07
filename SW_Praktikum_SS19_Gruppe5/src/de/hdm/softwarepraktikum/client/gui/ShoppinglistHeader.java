package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	private Button assignUserToRetailer;
	private Button editShoppinglistName;
	private Button showUserRetailerAllocation;

	public ShoppinglistHeader() {

		if (shoppinglistToDisplay == null) {
			shoppinglistHeaderLabel = new Label("Keine Shoppinglist ausgewaehlt");
		} else {
			shoppinglistHeaderLabel.setText(shoppinglistToDisplay.getName());
		}

		shoppinglistHeaderLabel.setStyleName("ListLabel");
		newListitem = new Button("Eintrag hinzufuegen");
		deleteShoppinglist = new Button("Einkaufsliste loeschen");
		assignUserToRetailer = new Button("Nutzer zuordnen");
		editShoppinglistName = new Button("Editieren");
		showUserRetailerAllocation = new Button("Nutzer Einzelhaendler zuweisung anzeigen");

		Image newListitemImg = new Image();
		newListitemImg.setUrl("images/shopping-cart.png");
		newListitemImg.setSize("16px", "16px");
		newListitem.getElement().appendChild(newListitemImg.getElement());
		newListitem.setStyleName("ShoppinglistHeaderButton");
		newListitem.addClickHandler(new NewListitemClickHandler());

		Image deleteShoppinglistImg = new Image();
		deleteShoppinglistImg.setUrl("images/delete.png");
		deleteShoppinglistImg.setSize("16px", "16px");
		deleteShoppinglist.getElement().appendChild(deleteShoppinglistImg.getElement());
		deleteShoppinglist.setStyleName("ShoppinglistHeaderButton");
		deleteShoppinglist.addClickHandler(new DeleteShoppinglistClickHandler());

		Image assignUserToRetailerImg = new Image();
		assignUserToRetailerImg.setUrl("images/man-pushing-a-shopping-cart.png");
		assignUserToRetailerImg.setSize("16px", "16px");
		assignUserToRetailer.getElement().appendChild(assignUserToRetailerImg.getElement());
		assignUserToRetailer.setStyleName("ShoppinglistHeaderButton");
		assignUserToRetailer.addClickHandler(new AssignUserToRetailerClickHandler());

		Image editShoppinglistNameImg = new Image();
		editShoppinglistNameImg.setUrl("images/edit.png");
		editShoppinglistNameImg.setSize("16px", "16px");
		editShoppinglistName.getElement().appendChild(editShoppinglistNameImg.getElement());
		editShoppinglistName.setStyleName("ShoppinglistHeaderButton");
		editShoppinglistName.addClickHandler(new EditShoppinglistNameClickHandler());

		Image showUserRetailerAllocationImg = new Image();
		showUserRetailerAllocationImg.setUrl("images/showUserRetailerAllocation.png");
		showUserRetailerAllocationImg.setSize("16px", "16px");
		showUserRetailerAllocation.getElement().appendChild(showUserRetailerAllocationImg.getElement());
		showUserRetailerAllocation.setStyleName("ShoppinglistHeaderButton");
		showUserRetailerAllocation.addClickHandler(new ShowUserRetailerAllocationClickHandler());

	}

	public void onLoad() {

		this.add(shoppinglistHeaderLabel);
		this.add(newListitem);
		this.add(assignUserToRetailer);
		this.add(editShoppinglistName);
		this.add(deleteShoppinglist);
		this.add(showUserRetailerAllocation);

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
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, nlf);
				ssf.setSelected(shoppinglistToDisplay);

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem Erzeugen einer <code>AssignUserToRetailerForm</code>
	 * Instanz.
	 */
	private class AssignUserToRetailerClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (shoppinglistToDisplay != null) {

				AssignUserToRetailerForm autrdb = new AssignUserToRetailerForm();
				autrdb.setGstvm(ShoppinglistHeader.this.gstvm);
				autrdb.setShoppinglistHeader(ShoppinglistHeader.this);
				autrdb.setShoppinglistToDisplay(shoppinglistToDisplay);
				autrdb.setGroupToDisplay(groupToDisplay);
			
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, autrdb);
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
				// suraf.setGroupToDisplay(groupToDisplay);
				ShoppinglistShowForm ssf = new ShoppinglistShowForm(ShoppinglistHeader.this, suraf);
				ssf.setSelected(shoppinglistToDisplay);

			} else {
				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
			}
		}

	}

}
