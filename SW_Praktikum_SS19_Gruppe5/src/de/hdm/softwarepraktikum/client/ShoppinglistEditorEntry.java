package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.Dockpanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ShoppinglistEditorEntry implements EntryPoint {

	public void onModuleLoad() {
		
		Dockpanel d1 = new Dockpanel();
		HorizontalPanel h1 = new HorizontalPanel();		
		
		h1.add(d1);
			
		RootPanel.get().add(h1);
		
	}
	
	
}
