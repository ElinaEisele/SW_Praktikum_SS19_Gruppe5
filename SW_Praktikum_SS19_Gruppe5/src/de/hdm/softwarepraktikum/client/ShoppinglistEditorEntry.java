package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.ShoppinglistNavigator;
import de.hdm.softwarepraktikum.client.gui.Content;



/**
 * Entry-Point Klasse des Projekts <b>MaulTasche</b> f�r den Editor.
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
		
		RootPanel.get().add(navigator);
		RootPanel.get().add(slaForm);
		
		
	}
	

}
