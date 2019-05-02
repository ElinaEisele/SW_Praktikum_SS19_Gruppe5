package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class GroupHeader extends HorizontalPanel {

	Label groupHeaderLabel = new Label("Hier steht der Name der ausgew�hlten Gruppe");
	Button addUser = new Button("Nutzer hinzuf�gen Bild");
	Button leaveGroup = new Button("Gruppe verlassen Bild");
	Button deleteGroup = new Button("Gruppe l�schen Bild");

	public void onLoad() {
		super.onLoad();

		this.add(groupHeaderLabel);
		this.add(addUser);
		this.add(leaveGroup);
		this.add(deleteGroup);

	}

}
