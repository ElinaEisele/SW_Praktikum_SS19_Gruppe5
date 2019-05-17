package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse zur Darstellung der Shoppinglist Uebersiche unterhalb des Headers.
 * Hier werden Such- und Filterfunktionen sowie die eigentliche Shoppingliste 
 * dargestellt.
 * 
 * @author ElinaEisele, Jonas Wagenknecht
 *
 */
public class ShoppinglistContent extends VerticalPanel{
	
	ShoppinglistContent shoppinglistContent;
	ShoppinglistSearchBar shoppinglistSearchBar ;
	ShoppinglistCellTable shoppinglistCellTable ;
	
	public void onLoad() {
		super.onLoad();
		
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
