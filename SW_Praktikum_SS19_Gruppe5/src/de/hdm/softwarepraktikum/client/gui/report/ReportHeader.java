package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung des Headers des Reports. Dieses umfasst ein Button
 * fuer den Editor, einen fuer den ReportGenerator, einen fuer den Logout und
 * den Namen der Anwendung.
 * 
 * @author FelixRapp & CarlaHofmann
 *
 */
public class ReportHeader extends HorizontalPanel {

	private User user = CurrentUser.getUser();

	private Button editorButton;
	private Button reportButton;
	private Button platzhalterButton;
	private Button abmeldenButton;
	private Anchor reportLink;
	private Anchor editorLink;

	public void onLoad() {

		editorButton = new Button("Editor");
		reportButton = new Button("Report");
		platzhalterButton = new Button("|");
		abmeldenButton = new Button("Abmelden");
		reportLink = new Anchor("Report");
		editorLink = new Anchor("Editor");

		editorButton.addClickHandler(new EditorClickHandler());
		reportButton.addClickHandler(new ReportClickHandler());
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
	
	/**
	 * Durch ein Klick auf den Logout-Button wird der User auf die
	 * Startseite weitergeleitet
	 */
	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.alert(CurrentUser.getUser().getName() + " loggt sich jetzt aus.");
			Window.open(user.getLogoutUrl(), "_self", "");

		}

	}
	
	/**
	 * Durch ein Klick auf den Editor-Button wird die Editorseite
	 * aufgerufen.
	 */
	private class EditorClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			editorLink.setHref(GWT.getHostPageBaseURL()+"SW_Praktikum_SS19_Gruppe5.html");
			Window.open(editorLink.getHref(), "_self", "");
		}
		
	}
	
	/**
	 * Durch ein Klick auf den Report-Button wird die 
	 * Reportseite aktualisiert.
	 */
	private class ReportClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Window.Location.reload();
		}
		
	}

}
