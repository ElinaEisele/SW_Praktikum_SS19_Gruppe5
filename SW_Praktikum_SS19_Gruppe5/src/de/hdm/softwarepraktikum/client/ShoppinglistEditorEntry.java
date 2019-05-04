package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.NavigatorPanel;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistShowForm;
import de.hdm.softwarepraktikum.client.gui.Header;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistCellTable;

/**
 * Entry-Point Klasse des Projekts <b>SW_Praktikum_SS19_Gruppe5</b> für den Editor.
 * 
 * @author ElinaEisele , JonasWagenknecht
 * 
 */
public class ShoppinglistEditorEntry implements EntryPoint {
	
	ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	
	Header header = null;
	NavigatorPanel shoppinglistNavigator = null;
	ShoppinglistShowForm shoppinglistShowForm = null;
	
	
	/**
	 * Da diese Klasse die Implementierung des Interrface <code>EntryPoint</code>
	 * sicherstellt, wird die Methode <code>public void onModuleLoad()</code> benoetigt.
	 * Diese ist wie die <code>main()</code>-Methode in Java-Applikationen
	 * fuer GWT zu verstehen.
	 */
	@Override
	public void onModuleLoad() {
		
		header = new Header();
		shoppinglistNavigator = new NavigatorPanel();
		shoppinglistShowForm = new ShoppinglistShowForm();
		
						
		RootPanel.get("Header").add(header);
		RootPanel.get("Navigator").add(shoppinglistNavigator);
		RootPanel.get("Details").add(shoppinglistShowForm);
		
		
	}
	

}
