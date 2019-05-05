package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * Klasse, um die Navigation in Gruppen und Einkaufslisten darzustellen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class NavigatorPanel extends CellTree {
	
	public NavigatorPanel(GroupShoppinglistTreeViewModel viewModel, String value) {
		super(viewModel, value);
		// TODO Auto-generated constructor stub
	}

	GroupShoppinglistTreeViewModel gstvm = null;
	
	VerticalPanel navigatorPlatzhalter = null;
	Label navigatorLabel = null;
	
	/**
	 * An dieser Stelle erfolgt die Implementierung des CellTrees.
	 */

//	public void onLoad() {
//		super.onLoad();
//		
//		navigatorPlatzhalter = new VerticalPanel();
//		
//		navigatorLabel = new Label("Hier befindet sich der Gruppen/Shoppinglisten Navigator");
//		navigatorPlatzhalter.add(navigatorLabel);
//		
//		this.add(navigatorPlatzhalter);
//		
//	}

}
