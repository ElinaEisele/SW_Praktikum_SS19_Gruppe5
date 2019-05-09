package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.gui.Editor;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

public class ShoppinglistEditorEntryLogin implements EntryPoint{
	
	private LoginServiceAsync loginService = null;
	private Button loginButton = new Button("Login");
	private Anchor signInLink = new Anchor("Login");
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte melde dich mit deinem Google-Account an!");

	@Override
	public void onModuleLoad() {
		loginService = ClientsideSettings.getLoginService();
		loginService.login(GWT.getHostPageBaseURL()+"SW_Praktikum_SS19_Gruppe5.html", new LoginServiceCallback());
	}
	
	private class LoginServiceCallback implements AsyncCallback<User>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(User u) {
			CurrentUser.setUser(u);
			
			if (u.isLoggedIn()) {
				if (u.getName() == null) {
					Anchor shoppinglistEditorLink = new Anchor();
					shoppinglistEditorLink.setHref(GWT.getHostPageBaseURL() + "SW_PRaktikum_SS19_Gruppe5.html");
					
					RootPanel.get("Navigator").setVisible(false);
					RootPanel.get("Details").add(new RegistrationForm(shoppinglistEditorLink, u));
				} else {
					Editor editor = new Editor();
					editor.loadForms();
				}
			}else {
				loadLogin();
			}
		}
		
	}
	
	public void loadLogin() {
		
		RootPanel.get("Details").setVisible(false);
		RootPanel.get("Navigator").setVisible(false);
		RootPanel.get("Container").add(loginPanel);
		
		loginLabel.setStylePrimaryName("loginLabel");
		loginButton.setStylePrimaryName("loginButton");
		
		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
		
		signInLink.setHref(CurrentUser.getUser().getLoginUrl());
		
		loginButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.open(signInLink.getHref(), "_self", "");
			}
			
		});
	}
	
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
