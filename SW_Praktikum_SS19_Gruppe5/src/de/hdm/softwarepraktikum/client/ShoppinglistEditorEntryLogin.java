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
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

public class ShoppinglistEditorEntryLogin implements EntryPoint {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte mit Google-Account anmelden.");
	private Anchor signInLink = new Anchor("Login");

	@Override
	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new LoginServiceCallback());

	}

	private class LoginServiceCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}

//		@Override
//		public void onSuccess(User user) {
//			
//			CurrentUser.setUser(user);
//			
//			if (user.isLoggedIn()) {
//				Editor editor = new Editor();
//				editor.loadForms();
//			} else {
//				loadLogin();
//			}
//		}

		@Override
		public void onSuccess(User u) {

			CurrentUser.setUser(u);

			if (u.isLoggedIn()) {
				if (u.getName() == null) {
					Anchor shoppinglistEditorLink = new Anchor();
					shoppinglistEditorLink.setHref(GWT.getHostPageBaseURL());

					RootPanel.get("header").setVisible(false);
					RootPanel.get("aside").setVisible(false);
					RootPanel.get("main").add(new RegistrationForm(shoppinglistEditorLink, u));
					
					Window.alert(u.getGmailAddress());
				} else {

					Editor editor = new Editor();
					editor.loadForms();
					
					Window.alert(u.getGmailAddress());
				}
			} else {
				loadLogin();
				
				Window.alert(u.getGmailAddress());
			}
		}

	}

	public void loadLogin() {

		signInLink.setHref(CurrentUser.getUser().getLoginUrl());

		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);

		RootPanel.get("main").add(loginPanel);

//		loginLabel.setStylePrimaryName("loginLabel");
//		loginButton.setStylePrimaryName("loginButton");
//		
//		loginButton.addClickHandler(new LoginClickHandler());

//		RootPanel.get("header").setVisible(false);
//		RootPanel.get("aside").setVisible(false);

	}

//	private class LoginClickHandler implements ClickHandler{
//
//		@Override
//		public void onClick(ClickEvent event) {
//			Window.open(signInLink.getHref(), "_self", "");
//		}
//		
//	}

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
