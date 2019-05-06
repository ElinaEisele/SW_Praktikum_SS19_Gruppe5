package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

/**
 * Klasse fuer die Ueberschrift und Buttons in der Gruppenansicht
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class GroupHeader extends HorizontalPanel {

	Label groupHeaderLabel = new Label("Hier steht der Name der ausgewählten Gruppe");
	
	PushButton leaveGroup = new PushButton(new Image("images/logout.png"));
	PushButton deleteGroup = new PushButton(new Image("images/delete.png"));
	PushButton addUser = new PushButton(new Image("war/images/add-user.png"));

	public void onLoad() {
		super.onLoad();

		this.add(groupHeaderLabel);
		this.add(addUser);
		this.add(leaveGroup);
		this.add(deleteGroup);

	}

}
