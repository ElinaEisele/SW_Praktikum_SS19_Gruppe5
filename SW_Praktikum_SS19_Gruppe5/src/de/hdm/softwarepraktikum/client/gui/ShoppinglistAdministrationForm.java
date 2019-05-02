package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShoppinglistAdministrationForm extends VerticalPanel {
	
	StartHeader startHeader = new StartHeader();
	GroupHeader groupHeader = new GroupHeader();
	ListHeader listHeader = new ListHeader();
	EditorHeader editorHeader = new EditorHeader();

	public void onLoad() {
		super.onLoad();

		this.add(listHeader);

	}

}
