package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse, um die Navigation in Gruppen und Einkaufslisten darzustellen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class NavigatorPanel extends VerticalPanel {
	
	VerticalPanel navigatorPlatzhalter = null;
	Label navigatorLabel = null;
	
	/**
	 * An dieser Stelle erfolgt die Implementierung des CellTrees.
	 */

	public void onLoad() {
		super.onLoad();
		
		navigatorPlatzhalter = new VerticalPanel();
		navigatorPlatzhalter.setStyleName("swp-navigatorPlatzhalter");
		
		navigatorLabel = new Label("Gruppen/Shoppinglisten Navigator");
		navigatorPlatzhalter.add(navigatorLabel);
		
		this.add(navigatorPlatzhalter);
		
	}

}
