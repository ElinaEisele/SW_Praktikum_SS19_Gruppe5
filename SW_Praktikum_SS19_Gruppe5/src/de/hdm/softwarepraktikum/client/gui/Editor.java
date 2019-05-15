package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * Diese Klasse stellt den Startpunkt des Editor-Clients dar. Alle dazu
 * relevanten HTML-Elemente werden in dieser Klasse zusammgefügt.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Editor {
	
	private Header header = null;
	
	// wird noch zu CellTable, aktuell nur Platzhalter
	private HorizontalPanel navigator = null;
	
	private Trailer trailer = null;
	
	public void loadForms() {
		
		TreeViewModel model = new NavigatorPanel();
		CellTree tree = new CellTree(model, null);
		
		header = new Header();
		navigator = new HorizontalPanel();
		trailer= new Trailer();
		
		RootPanel.get("main").clear();
		RootPanel.get("aside").clear();
		
		RootPanel.get("main").setVisible(true);
		RootPanel.get("aside").setVisible(true);
		
		RootPanel.get("footer").add(trailer);
		RootPanel.get("aside").add(tree);
		RootPanel.get("header").add(header);

	
	}
	

}
