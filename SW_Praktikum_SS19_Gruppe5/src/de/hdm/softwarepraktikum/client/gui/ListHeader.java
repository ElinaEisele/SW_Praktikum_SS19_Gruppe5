package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Klasse f�r die �berschrift und Buttons in der Listenansicht
 * 
 * @author ElinaEisele
 */

public class ListHeader extends HorizontalPanel {

	Label listHeaderLabel = new Label("Hier steht der Name der ausgew�hlten Liste");
	Button addListItem = new Button("Eitrag hinzuf�gen Bild");
	Button setUserRetailerAssignment = new Button("Nutzer H�ndler Zuweisung Bild");
	Button deleteShoppinglist = new Button("Einkaufsliste l�schen Bild");

	public void onLoad() {
		super.onLoad();

		this.add(listHeaderLabel);
		this.add(addListItem);
		this.add(setUserRetailerAssignment);
		this.add(deleteShoppinglist);

	}

}
