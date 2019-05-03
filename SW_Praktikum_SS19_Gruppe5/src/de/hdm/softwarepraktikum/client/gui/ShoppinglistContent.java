package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ShoppinglistContent extends VerticalPanel{
	
	ShoppinglistSearchBar shoppinglistSearchBar = new ShoppinglistSearchBar();
	ShoppinglistCelltable schoppinglistCelltable = new ShoppinglistCelltable();
	
	public void onLoad() {
		super.onLoad();
		
		this.add(shoppinglistSearchBar);
		this.add(schoppinglistCelltable);
	}

}
