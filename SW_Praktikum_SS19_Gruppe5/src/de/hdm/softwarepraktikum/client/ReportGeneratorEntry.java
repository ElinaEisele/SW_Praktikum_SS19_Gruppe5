package de.hdm.softwarepraktikum.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.client.gui.report.ReportMain;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.bo.User;
/**
 * Entry-Point-Klasse des Projekts <b>SW_Praktikum_SS19_Gruppe5</b> fuer den
 * Report.
 * @author LeoniFriedrich
 *
 */
public class ReportGeneratorEntry implements EntryPoint{
	

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte mit Google-Account anmelden.");
	private Anchor signInLink = new Anchor("Login");

	/**
	 * 
	 * Da diese Klasse das Interface <code>EntryPoint</code> implementiert,
	 * benoetigen wir die Methode <code>onModuleLoad()</code>. Diese
	 * Methode wird zu Beginn des Seitenaufrufs abgerufen.
	 */

	public void onModuleLoad() {
	/*
	 * Ueber diese Methoden werden Instanzen der Asynchronen Interfaces gebildet
	 */
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "ReportGenerator.html", new LoginServiceCallback());
		Window.alert("nach login");
	}

	/**
	 * 
	 * Bei erfolgreichem RPC callback wird zu Beginn die 
	 * <code>CurrentUser<code> gesetzt. Anschlieﬂend erfolgt eine Abfrage ob der User bereits im System eingeloggt ist. 
	 * Falls dies zutrifft, wird die <code>report.loadForms<code> aufgerufen.
	 * Falls die nicht zutrifft, wird die <code>loadLogin()<code> aufgerufen, indem sich der User am System
	 * anmelden kann.
	 */
	
	private class LoginServiceCallback implements AsyncCallback<User> {
		
		/**
		 * bei fehlerhaftem RPC Callback wird eine Fehlermeldung geworfen 
		 */
		@Override
		public void onFailure(Throwable caught) {
			// auskommentiert lassen, sonst taucht Fehlermeldung auf, da noch Fake Google User Objekt
			//				Window.alert(caught.toString());
		}

		@Override
		public void onSuccess(User user) {

			CurrentReportUser.setUser(user);

			if (user.isLoggedIn()) {
				ReportMain report = new ReportMain();
				report.loadForms();
			} else {
				loadLogin();
			}
		}

//			@Override
//			public void onSuccess(User u) {
//				CurrentUser.setUser(u);
//				
//				if (u.isLoggedIn()) {
//					if (u.getName() == null) {
//						Anchor shoppinglistEditorLink = new Anchor();
//						shoppinglistEditorLink.setHref(GWT.getHostPageBaseURL());
//						
//						RootPanel.get("header").setVisible(false);
//						RootPanel.get("aside").setVisible(false);
//						RootPanel.get("main").add(new RegistrationForm(shoppinglistEditorLink, u));
//					} else {
//						Editor editor = new Editor();
//						editor.loadForms();
//					}
//				}else {
//					loadLogin();
//				}
//			}

	}

	/**
	 * Diese Methode wird aufgerufen, falls der User nicht am System eingeloggt ist.
	 */
	public void loadLogin() {

		signInLink.setHref(CurrentReportUser.getUser().getLoginUrl());

		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);

		RootPanel.get("reportMain").add(loginPanel);

//			loginLabel.setStylePrimaryName("loginLabel");
//			loginButton.setStylePrimaryName("loginButton");
//			
//			loginButton.addClickHandler(new LoginClickHandler());

//			RootPanel.get("header").setVisible(false);
//			RootPanel.get("aside").setVisible(false);

	}

//		private class LoginClickHandler implements ClickHandler{
//
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.open(signInLink.getHref(), "_self", "");
//			}
//			
//		}

	/**
	 * Die Klasse <code>CurrentUser</code> repraesentiert den aktuell am System
	 * angemeldeten User. Da weitere GUI-Klassen das angemeldetet User-Objekt
	 * verwenden, muss diese jederzeit aufrufbar sein.
	 */
	public static class CurrentReportUser {

		private static User u = null;

		public static User getUser() {
			return u;
		}

		public static void setUser(User u) {
			CurrentReportUser.u = u;
		}

	}
	
}