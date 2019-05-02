package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class North extends HorizontalPanel{
	
	Button b1 = new Button("North");

	public void onLoad() {
		super.onLoad();

		HorizontalPanel hpanel = new HorizontalPanel();
		this.add(hpanel);
		hpanel.add(b1);
	}


}
