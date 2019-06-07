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

import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.client.gui.report.ReportMain;
import de.hdm.softwarepraktikum.client.gui.report.ReportShowForm;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.LoginServiceAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse, welche den <code>EntryPoint</code> des Reports beinhaltet.
 * 
 * @author TimBeutelspacher
 */
public class ReportGeneratorEntry implements EntryPoint{

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte mit Google-Account anmelden.");
	private Anchor signInLink = new Anchor("Login");
	private ReportMain report = new ReportMain();
	private Button loginButton = new Button("Login");
//	private Button button =  new Button("TESTtest123");
//	private User u = CurrentUser.getUser();
	
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new LoginServiceCallback());
		Window.alert("nach login");
		
		//Test-Button
//		RootPanel.get("reportMain").add(button);
//		report.loadForms();
		
//		if(u == null) {
//			Window.alert("Hier fehlt der user!");
//		}

	}
	
//	public User getU() {
//		return u;
//	}
//
//	public void setU(User u) {
//		this.u = u;
//	}


	private class LoginServiceCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
// auskommentiert lassen, sonst taucht Fehlermeldung auf, da noch Fake Google User Objekt
//				Window.alert(caught.toString());
		}

//		@Override
//		public void onSuccess(User user) {
//
//			CurrentReportUser.setUser(user);
//
//			if (user.isLoggedIn()) {
//				ReportMain report = new ReportMain();
//				report.loadForms();
//			} else {
//				loadLogin();
//			}
//		}

		@Override
		public void onSuccess(User u) {
			CurrentUser.setUser(u);
			
			if (u.isLoggedIn()) {
				if (u.getName() == null) {
					Anchor reportGeneratorLink = new Anchor();
					reportGeneratorLink.setHref(GWT.getHostPageBaseURL());
					
					RootPanel.get("ReportHeader").setVisible(false);
					RootPanel.get("ReportAside").setVisible(false);
//					RootPanel.get("ReportMain").add(new RegistrationForm(reportGeneratorLink, u));
				} else {
					ReportMain report = new ReportMain();
					report.loadForms();
				}
			}else {
				loadLogin();
			}
		}

	}

	
	public void loadLogin() {

		signInLink.setHref(CurrentUser.getUser().getLoginUrl());

		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);

		RootPanel.get("ReportMain").add(loginPanel);

		loginLabel.setStylePrimaryName("loginLabel");
		loginButton.setStylePrimaryName("loginButton");
		
		loginButton.addClickHandler(new LoginClickHandler());

		RootPanel.get("ReportHeader").setVisible(false);
		RootPanel.get("ReportAside").setVisible(false);

	}

	private class LoginClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Window.open(signInLink.getHref(), "_self", "");
		}
		
	}
	
}