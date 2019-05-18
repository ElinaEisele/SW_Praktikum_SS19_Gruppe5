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
	GroupForm groupForm;
	
	public void onLoad() {
		super.onLoad();
		
		shoppinglistSearchBar = new ShoppinglistSearchBar();
		shoppinglistCellTable = new ShoppinglistCellTable();
		groupForm = new GroupForm();
		
		shoppinglistSearchBar.setStylePrimaryName("shoppinglistSearchBar");
		shoppinglistCellTable.setStylePrimaryName("shoppinglistCellTable");
		
		this.add(shoppinglistSearchBar);
		this.add(shoppinglistCellTable);
		this.add(groupForm);
	}

}
