package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse, die den Inhalt der Gruppen darstellt.
 * 
 * @author ElinaEisele
 */

public class ShoppinglistContent extends VerticalPanel{
	
	SearchBar searchBar = new SearchBar();
		
	public void onLoad() {
		super.onLoad();
		
		this.add(searchBar);
		
	}

}
