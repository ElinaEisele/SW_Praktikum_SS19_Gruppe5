package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diese Klasse stellt die Texte für Impressum und Credits mittels
 * HTML-Format dar. Die Inhalte werden in einer DialogBox angezeigt.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Trailer extends VerticalPanel{
	
	Label impressum = null;
	
	public void onLoad() {
		super.onLoad();
		
		impressum = new Label("Hier steht das Impressum.");
		
		this.add(impressum);
	}

}
