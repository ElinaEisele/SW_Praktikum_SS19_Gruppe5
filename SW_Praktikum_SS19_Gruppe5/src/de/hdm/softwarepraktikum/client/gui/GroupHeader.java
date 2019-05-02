package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class GroupHeader extends HorizontalPanel {

	Label groupHeaderLabel = new Label("Hier steht der Name der ausgewählten Gruppe");
	Button addUser = new Button("Nutzer hinzufügen Bild");
	Button leaveGroup = new Button("Gruppe verlassen Bild");
	Button deleteGroup = new Button("Gruppe löschen Bild");

	public void onLoad() {
		super.onLoad();

		this.add(groupHeaderLabel);
		this.add(addUser);
		this.add(leaveGroup);
		this.add(deleteGroup);

	}

}
