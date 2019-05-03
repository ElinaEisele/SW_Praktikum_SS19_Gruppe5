package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse, die die Header Klasse und Content Klasse zusammenfasst.
 * 
 * @author ElinaEisele
 */

public class ShoppinglistForm extends VerticalPanel {
	
	StartHeader startHeader = new StartHeader();
	GroupHeader groupHeader = new GroupHeader();
	ListHeader listHeader = new ListHeader();
	EditorHeader editorHeader = new EditorHeader();
	
	Content content = new Content();

	public void onLoad() {
		super.onLoad();

		this.add(listHeader);
		this.add(content);

	}

}
