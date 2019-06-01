package de.hdm.softwarepraktikum.client.gui.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.client.gui.Editor;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Diese Klasse stellt die Hauptform des ReportGenerator Clients dar. Hier werden alle relevanten HTML-Layout Elemente
 * zu einer Form zusammengefuehrt.
 * 
 * @author LeoniFriedrich, TimBeutelspacher
 */
public class ShoppinglistAdministrationReport implements  EntryPoint {

	/**
	 * Benoetigte Panel werden hier instanziiert.
	 */
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	
	/**
	 * Button zum anzeigen des Reports
	 */
	private Button showReportButton = new Button("Report anzeigen");
	
	/**
	 * DateBox-Widget zum auswaehlen des Startdatums
	 */
	private DateBox startDateBox = new DateBox();
	
	/**
	 * DateBox-Widget zum auswaehlen des Startdatums
	 */
	private DateBox endDateBox = new DateBox();
	
	/**
	 * Drop-Down-Liste zur Gruppenauswahl
	 */
	private ListBox groupSelector = new ListBox();
	
	/**
	 * Speicher für das Startdate als SQL-Date
	 */
	private java.sql.Date sqlStartDate = null;
	
	/**
	 * Speicher für das Enddate als SQL-
	 */
	private java.sql.Date sqlEndDate = null;
	
	/**
	 * Speicher für die ausgewaehlte Gruppe
	 */
	private Group selectedGroup = null;
	
	/**
	 * Speicher für alle Gruppen eines Users
	 */
	private ArrayList<Group> groupsOfCurrentUser = null;
	/**
	 * Instanziierung des asynchronen Interfaces, um auf die Methoden der ShoppinglistAdministrationImpl zuzugreifen.
	 */
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	
	/**
	 * Attribute für LogIn
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte mit Google-Account anmelden.");
	private Anchor signInLink = new Anchor("Login");
	
	
	
	/**
	 * 
	 */
	public void onModuleLoad() {
		
		// Alle Gruppen des aktuellen Users werden zwischenespeichert.
		groupsOfCurrentUser = this.shoppinglistAdministration.getGroupsOf(CurrentUser.getUser(), new GetGroupsCallback());
		if(groupsOfCurrentUser != null) {
			for(Group g : groupsOfCurrentUser) {
				//Hinzufuegen der einzelnen Gruppen zur DropList
				groupSelector.addItem(g.getName());		
			}
		}
		
		
		
		
		mainPanel.add(groupSelector);
		
		startDateBox.setValue(new Date());
		mainPanel.add(startDateBox);
		
		endDateBox.setValue(new Date());
		mainPanel.add(endDateBox);
		
		addPanel.add(showReportButton);
		
		
		RootPanel.get("ReportMain").add(mainPanel);
		
		
		groupSelector.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				selectedGroup = groupsOfCurrentUser.get(groupSelector.getSelectedIndex());
			}
			
		});
		
		showReportButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//Eingegebenes Startdate festhalten
				sqlStartDate = new java.sql.Date(startDateBox.getValue().getTime());
				
				//Eingegebenes Enddate festhalten
				sqlEndDate = new java.sql.Date(endDateBox.getValue().getTime());
				
				//Ausführen der Report-Erstellung
				reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate);
				
			}
		});
	}
	
	
	
	
	private class GetGroupsCallback implements AsyncCallback<ArrayList<Group>>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: \n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Group> result) {
			
		
		}
		
	}
	
/**
 * 	******************************************************************************************************************
 * Abschnitt für LogIn BEGINN
 * ******************************************************************************************************************
 */

	
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

	private class LoginServiceCallback implements AsyncCallback<User>{
			
		@Override
		public void onFailure(Throwable caught) {
		// auskommentiert lassen, sonst taucht Fehlermeldung auf, da noch Fake Google User Objket
		//			Window.alert(caught.toString());
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
	}
			
			
			
	/**
	 * Die Klasse <code>CurrentUser</code> repraesentiert den aktuell am System angemeldeten User.
	 * Da weitere GUI-Klassen das angemeldetet User-Objekt verwenden, muss diese jederzeit aufrufbar sein.
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

