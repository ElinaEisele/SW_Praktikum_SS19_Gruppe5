package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Center extends HorizontalPanel {

	Button b1 = new Button("Center");

	public void onLoad() {
		super.onLoad();

		HorizontalPanel hpanel = new HorizontalPanel();
		this.add(hpanel);
		hpanel.add(b1);
	}

}