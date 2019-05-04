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
	
	ShoppinglistSearchBar shoppinglistSearchBar = new ShoppinglistSearchBar();
	ShoppinglistCellTable schoppinglistCelltable = new ShoppinglistCellTable();
	
	public void onLoad() {
		super.onLoad();
		
		this.add(shoppinglistSearchBar);
		this.add(schoppinglistCelltable);
	}

}
