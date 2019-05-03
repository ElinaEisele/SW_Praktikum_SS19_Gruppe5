package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.ShoppinglistNavigator;
import de.hdm.softwarepraktikum.client.gui.Content;
import de.hdm.softwarepraktikum.client.gui.Celltable;
import de.hdm.softwarepraktikum.client.gui.GroupNavigator;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistForm;

/**
 * Entry-Point Klasse des Projekts <b>MaulTasche</b> für den Editor.
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
	
	@Override
	public void onModuleLoad() {
		
		ShoppinglistNavigator navigator = new ShoppinglistNavigator();
		Content slaForm = new Content();
		
		GroupNavigator groupNavigator = new GroupNavigator();
		ShoppinglistForm shoppinglistForm = new ShoppinglistForm();
				
		RootPanel.get().add(groupNavigator);
		RootPanel.get().add(slaForm);
	
		
		
	}
	

}
