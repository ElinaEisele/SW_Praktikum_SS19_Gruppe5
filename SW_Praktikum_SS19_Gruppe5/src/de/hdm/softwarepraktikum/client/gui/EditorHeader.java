package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/*
 * Klasse für die Ueberschrift zur bearbeitenden Liste
 * 
 * @author: ElinaEisele
 * 
 */

public class EditorHeader extends HorizontalPanel {

	Label editorHeaderLabel = new Label("Hier steht der Name der zu bearbeitenden Liste");

	public void onLoad() {
		super.onLoad();

		this.add(editorHeaderLabel);
	}

}
