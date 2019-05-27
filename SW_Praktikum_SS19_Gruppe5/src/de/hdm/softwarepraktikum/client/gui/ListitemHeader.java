package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung des Headers eines Listitems zum Setzen une Entfernen
 * des Listitems als Standarditems und zum Loeschen des Listitems.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */
public class ListitemHeader extends HorizontalPanel {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();
	private ListitemShowForm listitemShowForm;
	private Listitem listitemToDisplay = null;

	private Label listitemHeaderLabel;

	private Button deleteListitem;
	private Button setStandard;
	private Button removeStandard;

	public ListitemHeader() {

		Listitem l = new Listitem();
		listitemToDisplay = l;

		listitemHeaderLabel = new Label("Listitem Header");
		// listitemHeaderLabel.setText(listitemToDisplay.getName());
		listitemHeaderLabel.setText("Listitem  Tets");
		listitemHeaderLabel.setStyleName("ListLabel");
		deleteListitem = new Button("Eintrag loeschen");

		setStandard = new Button("Standardartikel");
		removeStandard = new Button("Nicht mehr Standardartikel");

		if (listitemToDisplay.isStandard() == true) {
			removeStandard.setEnabled(false);
			removeStandard.setEnabled(true);
		} else {
			removeStandard.setEnabled(true);
			removeStandard.setEnabled(false);
		}

		Image deleteListitemImg = new Image();
		deleteListitemImg.setUrl("images/deleteListitemImg.png");
		deleteListitemImg.setSize("16px", "16px");
		deleteListitem.getElement().appendChild(deleteListitemImg.getElement());
		deleteListitem.setStyleName("ShoppinglistHeaderButton");
		deleteListitem.addClickHandler(new DeleteListitemClickHandler());

		Image setStandardImg = new Image();
		setStandardImg.setUrl("images/setStandardImg.png");
		setStandardImg.setSize("16px", "16px");
		setStandard.getElement().appendChild(setStandardImg.getElement());
		setStandard.setStyleName("ShoppinglistHeaderButton");
		setStandard.addClickHandler(new SetStandardClickHandler());

		Image removeStandardImg = new Image();
		removeStandardImg.setUrl("images/man-pushing-a-shopping-cart.png");
		removeStandardImg.setSize("16px", "16px");
		removeStandard.getElement().appendChild(removeStandardImg.getElement());
		removeStandard.setStyleName("ShoppinglistHeaderButton");
		removeStandard.addClickHandler(new RemoveStandardClickHandler());

	}

	public void onLoad() {

		this.add(listitemHeaderLabel);
		this.add(deleteListitem);
		this.add(setStandard);
		this.add(removeStandard);

	}

	public ListitemShowForm getListitemShowForm() {
		return listitemShowForm;
	}

	public void setListitemShowForm(ListitemShowForm listitemShowForm) {
		this.listitemShowForm = listitemShowForm;
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
				dldb.show();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewaehlt.");
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
				slondb.show();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewaehlt.");
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
				sloffdb.show();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewaehlt.");
			}
		}

	}

}
