package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Diese Klasse stellt den Startpunkt des Editor-Clients dar. Alle dazu
 * relevanten HTML-Elemente werden in dieser Klasse zusammgefügt.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Editor {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	private User u = CurrentUser.getUser();

	private String s;
	private Header header = null;
	private NavigatorPanel navigatorPanel = null;
	private GroupShowForm groupShowForm = null;
	private Trailer trailer = null;

	/**
	 * Methode, um die Startseite zu laden.
	 */
	public void loadForms() {

		header = new Header();
		navigatorPanel = new NavigatorPanel();
		trailer = new Trailer();

		RootPanel.get("main").clear();
		RootPanel.get("aside").clear();

		RootPanel.get("main").setVisible(true);
		RootPanel.get("aside").setVisible(true);

		RootPanel.get("trailer").add(trailer);
		RootPanel.get("aside").add(navigatorPanel);
		RootPanel.get("header").add(header);

	}

}
