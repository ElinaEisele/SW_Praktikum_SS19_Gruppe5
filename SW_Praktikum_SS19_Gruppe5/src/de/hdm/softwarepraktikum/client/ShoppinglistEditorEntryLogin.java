package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.gui.Editor;
import de.hdm.softwarepraktikum.client.gui.GroupShowForm;
import de.hdm.softwarepraktikum.client.gui.Header;
import de.hdm.softwarepraktikum.client.gui.GroupShoppinglistTreeViewModel;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistShowForm;
import de.hdm.softwarepraktikum.client.gui.Trailer;
import de.hdm.softwarepraktikum.shared.LoginInfo;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

public class ShoppinglistEditorEntryLogin implements EntryPoint{
		
	private Header header = null;
	private VerticalPanel navigator = null;
	private GroupShowForm groupShowForm = null;
	private HorizontalPanel hp = null;
	private Trailer trailer = null;
	private VerticalPanel vp = null;
	
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte mit Google-Account anmelden.");
	private Anchor signInLink = new Anchor("Login");
	
	@Override
	public void onModuleLoad() {
		
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new LoginServiceCallback());
	
	}
	
	private class LoginServiceCallback implements AsyncCallback<User>{
		
		@Override
		public void onFailure(Throwable caught) {
//			Window.alert(caught.toString()); // hat Server Error verursacht
		}

		@Override
		public void onSuccess(User user) {
			
			CurrentUser.setUser(user);
			
			if (user.isLoggedIn()) {
				Editor editor = new Editor();
				editor.loadForms();
			} else {
				loadLogin();
			}
		}
		
//		@Override
//		public void onSuccess(User u) {
//			CurrentUser.setUser(u);
//			
//			if (u.isLoggedIn()) {
//				if (u.getName() == null) {
//					Anchor shoppinglistEditorLink = new Anchor();
//					shoppinglistEditorLink.setHref(GWT.getHostPageBaseURL());
//					
//					RootPanel.get("header").setVisible(false);
//					RootPanel.get("aside").setVisible(false);
//					RootPanel.get("main").add(new RegistrationForm(shoppinglistEditorLink, u));
//				} else {
//					Editor editor = new Editor();
//					editor.loadForms();
//				}
//			}else {
//				loadLogin();
//			}
//		}
		


	}
	

//	public void loadEditor() {
//		
//		header = new Header();
//		navigator = new VerticalPanel();
//		groupShowForm = new GroupShowForm();
//		hp = new HorizontalPanel();
//		trailer = new Trailer();
//		vp = new VerticalPanel();
//		
//		hp.add(navigator);
//		hp.add(groupShowForm);
//		vp.add(header);
//		vp.add(hp);
//		vp.add(trailer);
//		
//		RootPanel.get("main").add(vp);
//	}

	
	public void loadLogin() {
		
		signInLink.setHref(CurrentUser.getUser().getLoginUrl());
		
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);

		RootPanel.get("header").setVisible(false);
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
