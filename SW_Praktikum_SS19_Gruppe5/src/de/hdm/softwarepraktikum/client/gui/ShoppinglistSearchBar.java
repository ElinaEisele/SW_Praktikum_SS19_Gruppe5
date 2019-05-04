package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestBox;

/**
 * Klasse, die Such- und Filterfunktionen einer Shoppingliste zusammenfasst.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistSearchBar extends HorizontalPanel{
	
	SuggestBox search = new SuggestBox();
	
	public void onLoad() {
		super.onLoad();
		
		this.add(search);
		
	}

}
