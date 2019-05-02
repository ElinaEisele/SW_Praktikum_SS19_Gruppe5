package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Dockpanel extends HorizontalPanel{
	
	North north = new North();
	West west = new West();
	Center center = new Center();

	public void onLoad() {
		super.onLoad();
		HorizontalPanel h1 = new HorizontalPanel();
		this.add(h1);

		DockPanel dockPanel = new DockPanel();
		dockPanel.setBorderWidth(1);
		h1.add(dockPanel);

		dockPanel.add(north, DockPanel.NORTH);
		dockPanel.add(west, DockPanel.WEST);
//		dockPanel.add(new HTML("EAST"), DockPanel.EAST);
//		dockPanel.add(new HTML("SOUTH"), DockPanel.SOUTH);
		dockPanel.add(center, DockPanel.CENTER);
		
	}

}
