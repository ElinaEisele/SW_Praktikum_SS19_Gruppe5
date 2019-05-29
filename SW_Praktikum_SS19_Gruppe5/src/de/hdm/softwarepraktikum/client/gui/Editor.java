package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.RootPanel;
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
	private NavigatorPanel navigatorPanel = null;
	private GroupShowForm groupShowForm = null;
	private Trailer trailer = null;

	public void loadForms() {
		
//		TreeViewModel model = new GroupShoppinglistTreeViewModel();
//		CellTree tree = new CellTree(model, null);

		header = new Header();
		navigatorPanel = new NavigatorPanel();
//		groupShowForm = new GroupShowForm();
		trailer = new Trailer();

		RootPanel.get("main").clear();
		RootPanel.get("aside").clear();

		RootPanel.get("main").setVisible(true);
		RootPanel.get("aside").setVisible(true);


		RootPanel.get("trailer").add(trailer);
		RootPanel.get("aside").add(navigatorPanel);

//		RootPanel.get("main").add(groupShowForm);
		RootPanel.get("header").add(header);

	}

}
