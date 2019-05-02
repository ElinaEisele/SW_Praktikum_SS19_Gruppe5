package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class StartHeader extends HorizontalPanel {

	Button addNewGroup = new Button("Neue Gruppe hinzufügen");

	public void onLoad() {
		super.onLoad();

		this.add(addNewGroup);

	}

}
