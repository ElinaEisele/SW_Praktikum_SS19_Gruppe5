package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.ShoppinglistNavigator;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistForm;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistCelltable;
import de.hdm.softwarepraktikum.client.gui.GroupNavigator;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistForm;

/**
 * Entry-Point Klasse des Projekts <b>SW_Praktikum_SS19_Gruppe5</b> für den Editor.
 * 
 * @author ElinaEisele
 * 
 */
public class ShoppinglistEditorEntry implements EntryPoint {

	static interface ShoppinglistTreeResources extends CellTree.Resources{
		@Override
		@Source("cellTreeClosedItem.gif")
		ImageResource cellTreeClosedItem();
		
		@Override
		@Source("cellTreeOpenItem.gif")
		ImageResource cellTreeOpenItem();
		
		@Override
		@Source("ShoppinglistCellTree.css")
		CellTree.Style cellTreeStyle();
		
	}
	
	ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	
	/**
	 * Da diese Klasse die Implementierung des Interrface <code>EntryPoint</code>
	 * sicherstellt, wird die Methode <code>public void onModuleLoad()</code> benoetigt.
	 * Diese ist wie die <code>main()</code>-Methode in Java-Applikationen
	 * fuer GWT zu verstehen.
	 */
	@Override
	public void onModuleLoad() {
		
		ShoppinglistNavigator navigator = new ShoppinglistNavigator();
		ShoppinglistForm slaForm = new ShoppinglistForm();
		
		GroupNavigator groupNavigator = new GroupNavigator();
		ShoppinglistForm shoppinglistForm = new ShoppinglistForm();
				
		RootPanel.get().add(groupNavigator);
		RootPanel.get().add(slaForm);
	
		
		
	}
	

}
