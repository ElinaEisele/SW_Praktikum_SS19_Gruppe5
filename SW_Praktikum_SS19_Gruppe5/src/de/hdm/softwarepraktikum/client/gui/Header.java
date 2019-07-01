package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ReportGeneratorEntry;
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

	private Button editorButton;
	private Button reportButton;
	private Button platzhalterButton;
	private Button logoutButton;
	private Anchor reportLink;
	private Anchor editorLink;

	private VerticalPanel logoutPanel;
	private Label userNameLabel;

	private ReportGeneratorEntry reportGenerator;

	public void onLoad() {

		editorButton = new Button("Editor");
		reportButton = new Button("Report");
		platzhalterButton = new Button("|");
		logoutButton = new Button("Abmelden");
		reportLink = new Anchor("Report");
		editorLink = new Anchor("Editor");

		logoutPanel = new VerticalPanel();

		userNameLabel = new Label(user.getName());
		logoutPanel.add(logoutButton);
		logoutPanel.add(userNameLabel);

		editorButton.addClickHandler(new EditorClickHandler());
		reportButton.addClickHandler(new ReportClickHandler());

		userNameLabel.addClickHandler(new LogoutClickHandler());
		logoutButton.addClickHandler(new LogoutClickHandler());

		editorButton.setStyleName("HeaderButton");
		reportButton.setStyleName("HeaderButton");
		platzhalterButton.setStyleName("HeaderPlatzhalterButton");
		platzhalterButton.getElement().setAttribute("disabled", "disabled");

		logoutButton.setStyleName("LogoutButton");
		userNameLabel.setStyleName("CurrentUser");
		logoutPanel.setStyleName("HeaderButton");

		this.add(editorButton);
		this.add(reportButton);
		this.add(platzhalterButton);
		this.add(logoutPanel);

	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Click-/EventHandler
	 * ***************************************************************************
	 */
	/**
	 * Durch ein Klick auf den Logout-Button wird der User auf die Begrüßungsseite
	 * weitergeleitet
	 */
	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.open(user.getLogoutUrl(), "_self", "");

		}

	}

	/**
	 * Durch ein Klick auf den Editor-Button wird die Editorseite aktualisiert.
	 */
	private class EditorClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Window.Location.reload();
		}

	}

	/**
	 * Durch ein Klick auf den Report-Button wird man auf die Report-Seite
	 * weitergeleitet.
	 */
	private class ReportClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.Location.assign("ReportGenerator.html");

		}

	}

}
