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
	
	ShoppinglistSearchBar shoppinglistSearchBar = null;
	ShoppinglistCellTable shoppinglistCellTable = null;
	
	public void onLoad() {
		super.onLoad();
		
		shoppinglistSearchBar = new ShoppinglistSearchBar();
		shoppinglistCellTable = new ShoppinglistCellTable();
		
		shoppinglistSearchBar.setStylePrimaryName("shoppinglistSearchBar");
		shoppinglistCellTable.setStylePrimaryName("shoppinglistCellTable");
		
		this.add(shoppinglistSearchBar);
		this.add(shoppinglistCellTable);
	}

}
