package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Klasse für die Überschrift und Buttons in der Listenansicht
 * 
 * @author ElinaEisele
 */

public class ListHeader extends HorizontalPanel {

	Label listHeaderLabel = new Label("Hier steht der Name der ausgewählten Liste");
	Button addListItem = new Button("Eitrag hinzufügen Bild");
	Button setUserRetailerAssignment = new Button("Nutzer Händler Zuweisung Bild");
	Button deleteShoppinglist = new Button("Einkaufsliste löschen Bild");

	public void onLoad() {
		super.onLoad();

		this.add(listHeaderLabel);
		this.add(addListItem);
		this.add(setUserRetailerAssignment);
		this.add(deleteShoppinglist);

	}

}
