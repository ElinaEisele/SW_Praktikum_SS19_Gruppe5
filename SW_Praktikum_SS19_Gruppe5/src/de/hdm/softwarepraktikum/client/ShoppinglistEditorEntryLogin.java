package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.gui.Editor;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.client.gui.Trailer;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

public class ShoppinglistEditorEntryLogin implements EntryPoint {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private VerticalPanel loginHeader = new VerticalPanel();
	private Label nameLabel = new Label("MaulTasche");
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte mit Google-Account anmelden.");
	private Anchor signInLink = new Anchor("Login");

	Trailer tr = new Trailer();
	
	@Override
	public void onModuleLoad() {
		
		nameLabel.setStyleName("Name");
		loginHeader.setStyleName("LoginHeader");
		loginPanel.setStyleName("LoginHeader");
		loginLabel.setStyleName("Login");
		signInLink.setStyleName("LoginLink");
		
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new LoginServiceCallback());
		
	}

	private class LoginServiceCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}


		@Override
		public void onSuccess(User u) {

			CurrentUser.setUser(u);

			if (u.isLoggedIn()) {
				if (u.getName() == null) {
					Anchor shoppinglistEditorLink = new Anchor();
					shoppinglistEditorLink.setHref(GWT.getHostPageBaseURL());
					
					RootPanel.get("header").add(nameLabel);
					RootPanel.get("main").add(new RegistrationForm(shoppinglistEditorLink, u));
					
				} else {

					Editor editor = new Editor();
					editor.loadForms();
					
				}
			} else {
				loadLogin();
				
			}
		}

	}

	public void loadLogin() {

		signInLink.setHref(CurrentUser.getUser().getLoginUrl());

		
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		loginHeader.add(nameLabel);
		

		RootPanel.get("LoginHeader").add(loginHeader);
		RootPanel.get("main").add(loginPanel);
		RootPanel.get("trailer").add(tr);

//		loginLabel.setStylePrimaryName("loginLabel");
//		loginButton.setStylePrimaryName("loginButton");

	}


	/**
	 * Die Klasse <code>CurrentUser</code> repräsentiert den aktuell am System
	 * angemeldeten User. Da weitere GUI-Klassen das angemeldetet User-Objekt
	 * verwenden, muss diese jederzeit aufrufbar sein.
	 */
	public static class CurrentUser {

		private static User u = null;

		public static User getUser() {
			return u;
		}

		public static void setUser(User u) {
			CurrentUser.u = u;
		}

	}

}
