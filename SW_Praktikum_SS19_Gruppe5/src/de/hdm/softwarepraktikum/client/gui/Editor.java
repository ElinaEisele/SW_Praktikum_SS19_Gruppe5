package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

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
		
		header = new Header();
		navigator = new HorizontalPanel();
		trailer= new Trailer();
		
		RootPanel.get("Details").clear();
		RootPanel.get("Navigator").clear();
		
		RootPanel.get("Details").setVisible(true);
		RootPanel.get("Navigator").setVisible(true);
		
		RootPanel.get("Trailer").add(trailer);
		RootPanel.get("Navigator").add(navigator);
		RootPanel.get("Header").add(header);
	
	}
	

}
