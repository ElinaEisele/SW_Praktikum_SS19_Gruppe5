package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse zur Darstellung des Listitems unterhalb des Headers.
 * 
 * @author ElinaEisele, Jonas Wagenknecht
 *
 */
public class ListitemContent extends VerticalPanel {

	private ListitemForm listitemForm;

	public ListitemContent() {
		listitemForm = new ListitemForm();
	}

	public void onLoad() {

		this.add(listitemForm);

	}
}
