package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung des Headers eines Listitems zum Setzen und Entfernen
 * des Listitems als Standarditems und zum Löeschen des Listitems.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */
public class ListitemHeader extends HorizontalPanel {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();
	private ListitemShowForm listitemShowForm = null;

	private Listitem listitemToDisplay = null;
	private Shoppinglist selectedShoppinglist = null;
	private Group selectedGroup = null;

	private Label listitemHeaderLabel = null;

	private Button deleteListitem = null;
	private Button setStandard = null;
	private Button removeStandard = null;

	public ListitemHeader() {

		listitemHeaderLabel = new Label("Listitem Header");
		listitemHeaderLabel.setText("Kein Eintrag ausgewählt");
		listitemHeaderLabel.setStyleName("ListLabel");
		deleteListitem = new Button();

		setStandard = new Button();
		removeStandard = new Button();

		Image deleteListitemImg = new Image();
		deleteListitemImg.setUrl("images/delete.png");
		deleteListitemImg.setSize("30px", "30px");
		deleteListitem.getElement().appendChild(deleteListitemImg.getElement());
		deleteListitem.setStyleName("ShoppinglistHeaderButton");
		deleteListitem.addClickHandler(new DeleteListitemClickHandler());

		Image setStandardImg = new Image();
		setStandardImg.setUrl("images/like.png");
		setStandardImg.setSize("30px", "30px");
		setStandard.getElement().appendChild(setStandardImg.getElement());
		setStandard.setStyleName("ShoppinglistHeaderButton");
		setStandard.addClickHandler(new SetStandardClickHandler());

		Image removeStandardImg = new Image();
		removeStandardImg.setUrl("images/like (1).png");
		removeStandardImg.setSize("30px", "30px");
		removeStandard.getElement().appendChild(removeStandardImg.getElement());
		removeStandard.setStyleName("ShoppinglistHeaderButton");
		removeStandard.addClickHandler(new RemoveStandardClickHandler());

	}

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * 
	 */
	public void onLoad() {

		shoppinglistAdministration.getProductnameOf(listitemToDisplay, new ProductNameAsyncCallback());

		this.add(listitemHeaderLabel);
		this.add(deleteListitem);
		if (listitemToDisplay.isStandard() == true) {
			this.add(removeStandard);
		} else {
			this.add(setStandard);
		}

	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public ListitemShowForm getListitemShowForm() {
		return listitemShowForm;
	}

	public void setListitemShowForm(ListitemShowForm listitemShowForm) {
		this.listitemShowForm = listitemShowForm;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public Listitem getListitemToDisplay() {
		return listitemToDisplay;
	}

	public void setListitemToDisplay(Listitem listitemToDisplay) {
		this.listitemToDisplay = listitemToDisplay;
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
	 * ClickHandler dient dem Aufruf der <code>DeleteListitemDialogBox</code>.
	 */
	private class DeleteListitemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (listitemToDisplay != null) {
				DeleteListitemDialogBox dldb = new DeleteListitemDialogBox();
				dldb.setSelectedListitem(listitemToDisplay);
				dldb.setSelectedShoppinglist(selectedShoppinglist);
				dldb.setSelectedGroup(selectedGroup);
				dldb.show();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem Aufruf der <code>StandardListitemOnDialogBox</code>.
	 */
	private class SetStandardClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (listitemToDisplay != null) {
				StandardListitemOnDialogBox slondb = new StandardListitemOnDialogBox();
				slondb.setSelectedListitem(listitemToDisplay);
				slondb.setSelectedGroup(selectedGroup);
				slondb.setSelectedShoppinglist(selectedShoppinglist);
				slondb.show();
				slondb.center();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewählt.");
			}
		}

	}

	/**
	 * ClickHandler dient dem Aufruf der <code>StandardListitemOffDialogBox</code>.
	 */
	private class RemoveStandardClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (listitemToDisplay != null) {
				StandardListitemOffDialogBox sloffdb = new StandardListitemOffDialogBox();
				sloffdb.setSelectedListitem(listitemToDisplay);
				sloffdb.setSelectedGroup(selectedGroup);
				sloffdb.setSelectedShoppinglist(selectedShoppinglist);
				sloffdb.show();
				sloffdb.center();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewählt.");
			}
		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */
	
	/**
	 * Nach erfolgreichem Laden wird der Name des Produkts gesetzt
	 */
	private class ProductNameAsyncCallback implements AsyncCallback<String> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(String result) {
			listitemHeaderLabel.setText(result);

		}

	}

}
