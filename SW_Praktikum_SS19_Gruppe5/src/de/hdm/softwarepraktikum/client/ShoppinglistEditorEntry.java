package de.hdm.softwarepraktikum.client;


import com.gargoylesoftware.htmlunit.javascript.host.Navigator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.GroupNavigator;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistAdministrationForm;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ShoppinglistEditorEntry implements EntryPoint {

	public void onModuleLoad() {
		
		GroupNavigator navigator = new GroupNavigator();
		ShoppinglistAdministrationForm slaForm = new ShoppinglistAdministrationForm();
		
		RootPanel.get().add(navigator);
		RootPanel.get().add(slaForm);
		
		
	}
	

}
