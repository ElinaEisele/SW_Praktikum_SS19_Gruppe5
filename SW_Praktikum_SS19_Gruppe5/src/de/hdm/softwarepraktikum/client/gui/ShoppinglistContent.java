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
<<<<<<< HEAD
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
=======
public class ShoppinglistContent extends VerticalPanel {

//	private ShoppinglistSearchBar shoppinglistSearchBar;
//	private ShoppinglistCellTable shoppinglistCellTable;
//
//	public void onLoad() {
//		this.clear();
//		shoppinglistSearchBar = new ShoppinglistSearchBar();
//	//	shoppinglistSearchBar.setShoppinglistCellTable(shoppinglistCellTable);
//		shoppinglistCellTable = new ShoppinglistCellTable();
//
//		shoppinglistSearchBar.setStylePrimaryName("shoppinglistSearchBar");
//		shoppinglistCellTable.setStylePrimaryName("shoppinglistCellTable");
//
//// die SearchBar ist noch nicht fertig!		
////		this.add(shoppinglistSearchBar);
//// cellTabel hat noch Fehler!		
//		this.add(shoppinglistCellTable);
//	}
>>>>>>> refs/heads/master

}
