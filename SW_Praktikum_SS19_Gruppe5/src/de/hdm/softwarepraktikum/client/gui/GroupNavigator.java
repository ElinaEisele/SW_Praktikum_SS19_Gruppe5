package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GroupNavigator extends VerticalPanel{
	
	public void onLoad() {
		super.onLoad();
		
		Button b1 = new Button("Click me 1");
		
		this.add(b1);
	}

}
