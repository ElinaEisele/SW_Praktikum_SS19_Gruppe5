package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Klasse fuer die Ueberschrift und Buttons in der Listenansicht
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistHeader extends HorizontalPanel {

	Label listLabel;
	Button addListitem;
	Button deleteShoppinglist;
	Button assignUserToRetailer;
	Button editShoppinglist;


	public void onLoad() {
		super.onLoad();
		
		listLabel = new Label("getListName");
		addListitem = new Button("Eintrag hinzufügen");
		deleteShoppinglist = new Button("Einkaufsliste löschen");
		assignUserToRetailer = new Button("Nutzer zuordnen");
		editShoppinglist = new Button("Editieren");
	
		listLabel.setStyleName("ListLabel");
				
		Image addListitemImg = new Image();
		addListitemImg.setUrl("images/shopping-cart.png");
		addListitemImg.setSize("16px", "16px");
		addListitem.getElement().appendChild(addListitemImg.getElement());
		addListitem.setStyleName("ShoppinglistHeaderButton");
		
		Image deleteShoppinglistImg = new Image();
		deleteShoppinglistImg.setUrl("images/delete.png");
		deleteShoppinglistImg.setSize("16px", "16px");
		deleteShoppinglist.getElement().appendChild(deleteShoppinglistImg.getElement());
		deleteShoppinglist.setStyleName("ShoppinglistHeaderButton");
		
		Image assignUserToRetailerImg = new Image();
		assignUserToRetailerImg.setUrl("images/man-pushing-a-shopping-cart.png");
		assignUserToRetailerImg.setSize("16px", "16px");
		assignUserToRetailer.getElement().appendChild(assignUserToRetailerImg.getElement());
		assignUserToRetailer.setStyleName("ShoppinglistHeaderButton");
		
		Image editShoppinglistImg = new Image();
		editShoppinglistImg.setUrl("images/edit.png");
		editShoppinglistImg.setSize("16px", "16px");
		editShoppinglist.getElement().appendChild(editShoppinglistImg.getElement());
		editShoppinglist.setStyleName("ShoppinglistHeaderButton");
		
		this.add(listLabel);
		this.add(addListitem);
		this.add(assignUserToRetailer);
		this.add(editShoppinglist);
		this.add(deleteShoppinglist);

		

	}

}
