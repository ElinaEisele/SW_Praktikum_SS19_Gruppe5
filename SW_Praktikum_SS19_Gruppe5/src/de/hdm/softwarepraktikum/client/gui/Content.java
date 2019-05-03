package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse, die den Inhalt der Gruppen bzw. der Listen darstellt
 * 
 * @author ElinaEisele, JonasWagenkencht
 */

public class Content extends VerticalPanel{
	
	SearchBar searchBar = new SearchBar();
	GroupView groupView = new GroupView();
	Celltable celltable = new Celltable();
		
	public void onLoad() {
		super.onLoad();
		
		this.add(searchBar);
		this.add(groupView);
		this.add(celltable);
	}

}
