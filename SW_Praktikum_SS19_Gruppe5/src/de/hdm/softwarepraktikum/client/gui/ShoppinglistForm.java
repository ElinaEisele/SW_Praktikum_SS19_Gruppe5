package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse, die die Header Klasse und Content Klasse zusammenfasst.
 * 
 * @author ElinaEisele, JonasWagenkencht
 */

public class ShoppinglistForm extends VerticalPanel {
	
	ShoppinglistHeader shoppinglistHeader = new ShoppinglistHeader();
	ShoppinglistContent shoppinglistContent = new ShoppinglistContent();
	
	
	public void onLoad() {
		super.onLoad();

		this.add(shoppinglistHeader);
		this.add(shoppinglistContent);


	}

}
