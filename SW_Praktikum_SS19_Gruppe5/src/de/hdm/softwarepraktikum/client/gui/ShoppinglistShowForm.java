package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Uebersicht einer Shoppingliste mit einem Header und mit der
 * Anzeige des Inhalt der Shoppingliste.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistShowForm extends VerticalPanel {

	private ShoppinglistHeader shoppinglistHeader = null;
	private ShoppinglistContent shoppinglistContent = null;
	private NewListitemForm newListitemForm = null;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Shoppinglist selectedShoppinglist;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();

	public ShoppinglistShowForm(ShoppinglistHeader sh, NewListitemForm nlf) {
		shoppinglistHeader = sh;
		mainPanel.add(nlf);

	}

	public ShoppinglistShowForm() {
		shoppinglistHeader = new ShoppinglistHeader();
		shoppinglistContent = new ShoppinglistContent();

		shoppinglistHeader.setStylePrimaryName("shoppinglistHeader");
		shoppinglistContent.setStylePrimaryName("shoppinglistContent");

		mainPanel.add(shoppinglistContent);
	}

	public void onLoad() {
		shoppinglistHeader.setShoppinglistShowForm(ShoppinglistShowForm.this);

		this.add(shoppinglistHeader);
		this.add(mainPanel);

	}

	public void setSelected(Shoppinglist s) {
		selectedShoppinglist = s;

	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

}
