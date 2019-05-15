package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung des Headers der Applikation. Dieses umfasst ein Button
 * fuer den Editor, einen fuer den ReportGenerator, einen fuer den Logout und
 * den Namen der Anwendung.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Header extends HorizontalPanel {

	private User user = CurrentUser.getUser();

	Button editorButton;
	Button reportButton;
	Button platzhalterButton;
	Button abmeldenButton;

	public void onLoad() {

		Button editorButton = new Button("Editor");
		Button reportButton = new Button("Report");
		Button platzhalterButton = new Button("|");
		Button abmeldenButton = new Button("Abmelden");

		abmeldenButton.addClickHandler(new LogoutClickHandler());

		editorButton.setStyleName("HeaderButton");
		reportButton.setStyleName("HeaderButton");
		platzhalterButton.setStyleName("HeaderPlatzhalterButton");
		platzhalterButton.getElement().setAttribute("disabled", "disabled");
		abmeldenButton.setStyleName("HeaderButton");

		this.add(editorButton);
		this.add(reportButton);
		this.add(platzhalterButton);
		this.add(abmeldenButton);

	}

	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			user.setLogoutUrl(user.getLogoutUrl());
			Window.open(user.getLogoutUrl(), "_self", "");

		}

	}

}
