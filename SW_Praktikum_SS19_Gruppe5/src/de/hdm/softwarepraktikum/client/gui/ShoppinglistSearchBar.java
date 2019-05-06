package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;

/**
 * Klasse, die Such- und Filterfunktionen einer Shoppingliste zusammenfasst.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistSearchBar extends HorizontalPanel{
	
	Label searchLabel = null;
	SuggestBox search = null;
	
	public void onLoad() {
		super.onLoad();
		
		searchLabel = new Label("Suche: ");
		search = new SuggestBox();
		
		this.add(searchLabel);
		this.add(search);
		
	}

}
