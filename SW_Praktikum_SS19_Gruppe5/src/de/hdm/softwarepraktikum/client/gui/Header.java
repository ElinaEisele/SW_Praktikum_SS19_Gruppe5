package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.bo.User;


/**
 * Klasse zur Darstellung des Headers der Applikation. Dieses umfasst
 * ein Button fuer den Editor, einen fuer den ReportGenerator, 
 * einen fuer den Logout und den Namen der Anwendung.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Header extends HorizontalPanel{
	
	private User user = CurrentUser.getUser();
	
	Button editor = null;
	Button reportGenerator = null;
	Image logo = null;
	Button logout = null;
	
	public void onLoad() {
		
		editor = new Button("Editor");
		editor.setStyleName("editor-Button");
		
		reportGenerator = new Button("Report");
		reportGenerator.setStyleName("report-Button");
		
		logout = new Button("Logout");
		logout.addClickHandler(new LogoutClickHandler());
		
		logo = new Image();
		logo.setUrl("images/testlogo.png");
		logo.setPixelSize(100, 70);
		logo.setStyleName("logo");
		
		
//		logout = new Anchor("Logout");
//		logout.setHref(user.getLogoutUrl());
//		logout.addClickHandler(new LogoutClickHandler());
//		logout.setStyleName("logout-Button");
		
		this.add(editor);
		this.add(reportGenerator);
		this.add(logo);
		this.add(logout);
		
	}
	
	private class LogoutClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			user.setLogoutUrl(user.getLogoutUrl());
			Window.open(user.getLogoutUrl(), "_self", "");

		}
		
	}

}
