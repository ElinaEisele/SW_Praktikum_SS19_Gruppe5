package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse, die den Inhalt der Gruppen bzw. der Listen darstellt
 * 
 * @author: ElinaEisele
 */

public class Content extends VerticalPanel{
	
	SearchBar searchBar = new SearchBar();
		
	public void onLoad() {
		super.onLoad();
		
		this.add(searchBar);
		
	}

}
