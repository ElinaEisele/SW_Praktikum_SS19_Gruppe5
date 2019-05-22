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

	private Shoppinglist selectedShoppinglist;

	public void onLoad() {

		shoppinglistHeader = new ShoppinglistHeader();
		shoppinglistContent = new ShoppinglistContent();

		shoppinglistHeader.setStylePrimaryName("shoppinglistHeader");
		shoppinglistContent.setStylePrimaryName("shoppinglistContent");

		this.add(shoppinglistHeader);
		this.add(shoppinglistContent);

	}

	public void setSelected(Shoppinglist s) {
		selectedShoppinglist = s;

	}

}
