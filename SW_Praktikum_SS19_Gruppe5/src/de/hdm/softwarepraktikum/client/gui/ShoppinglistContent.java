package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse zur Darstellung der Shoppinglist Uebersicht unterhalb des Headers.
 * Hier werden Such- und Filterfunktionen sowie die eigentliche
 * <code>Shoppinglist</code> dargestellt.
 * 
 * @author ElinaEisele, Jonas Wagenknecht
 *
 */
public class ShoppinglistContent extends VerticalPanel {

	private ShoppinglistSearchBar shoppinglistSearchBar;
	private ShoppinglistCellTable shoppinglistCellTable;

	public void onLoad() {
		
		shoppinglistSearchBar = new ShoppinglistSearchBar();
		shoppinglistSearchBar.setShoppinglistCellTable(shoppinglistCellTable);
		shoppinglistCellTable = new ShoppinglistCellTable();

		shoppinglistSearchBar.setStylePrimaryName("shoppinglistSearchBar");
		shoppinglistCellTable.setStylePrimaryName("shoppinglistCellTable");

// die SearchBar ist noch nicht fertig!		
//		this.add(shoppinglistSearchBar);
// cellTabel hat noch Fehler!		
//		this.add(shoppinglistCellTable);
	}

}
