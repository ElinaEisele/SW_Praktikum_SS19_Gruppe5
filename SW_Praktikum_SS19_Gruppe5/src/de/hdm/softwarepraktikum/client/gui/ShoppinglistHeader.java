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

	Label shoppinlistHeaderLabel = new Label("Hier steht der Name der ausgewählten Liste");
	
	Button addListitem;
	Button deleteShoppinglist;
	Button assignUserToRetailer;
	Button editShoppinglist;

	public void onLoad() {
		super.onLoad();
		
		addListitem = new Button();
		deleteShoppinglist = new Button();
		assignUserToRetailer = new Button();
		editShoppinglist = new Button();
	
		Image addListitemImg = new Image();
		addListitemImg.setUrl("images/shopping-cart.png");
		addListitemImg.setSize("32px", "32px");
		addListitem.getElement().appendChild(addListitemImg.getElement());
		
		Image deleteShoppinglistImg = new Image();
		deleteShoppinglistImg.setUrl("images/delete.png");
		deleteShoppinglistImg.setSize("32px", "32px");
		deleteShoppinglist.getElement().appendChild(deleteShoppinglistImg.getElement());
		
		Image assignUserToRetailerImg = new Image();
		assignUserToRetailerImg.setUrl("images/man-pushing-a-shopping-cart.png");
		assignUserToRetailerImg.setSize("32px", "32px");
		assignUserToRetailer.getElement().appendChild(assignUserToRetailerImg.getElement());
		
		Image editShoppinglistImg = new Image();
		editShoppinglistImg.setUrl("images/edit.png");
		editShoppinglistImg.setSize("32px", "32px");
		editShoppinglist.getElement().appendChild(editShoppinglistImg.getElement());
		
		this.add(shoppinlistHeaderLabel);
		this.add(addListitem);
		this.add(assignUserToRetailer);
		this.add(editShoppinglist);
		this.add(deleteShoppinglist);

		

	}

}
