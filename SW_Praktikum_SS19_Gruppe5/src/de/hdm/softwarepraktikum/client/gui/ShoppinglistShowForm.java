package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;

/**
 * Klasse zur Uebersicht einer Shoppingliste mit einem Header inklusiv
 * Buttons zum bearbeiten der Shoppingliste und mit der Anzeige des Inhalt der
 * Shoppingliste.
 * 
 * @author ElinaEisele, JonasWagenkencht
 */

public class ShoppinglistShowForm extends VerticalPanel {
	
	ShoppinglistHeader shoppinglistHeader = null;
	ShoppinglistContent shoppinglistContent = null;
	
	
	public void onLoad() {
		super.onLoad();
		
		shoppinglistHeader = new ShoppinglistHeader();
		shoppinglistContent = new ShoppinglistContent();
		
		shoppinglistHeader.setStylePrimaryName("shoppinglistHeader");
		shoppinglistContent.setStylePrimaryName("shoppinglistContent");
		
		this.add(shoppinglistHeader);
		this.add(shoppinglistContent);


	}


	public void setSelected(ShoppinglistDD s) {
		// TODO Auto-generated method stub
		
	}

}
