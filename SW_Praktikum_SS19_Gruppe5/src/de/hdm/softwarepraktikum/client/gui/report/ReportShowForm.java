package de.hdm.softwarepraktikum.client.gui.report;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;


/**
 * Diese Klasse stellt den "main"-Teil der Report-HTML Seite dar.
 * 
 * @author TimBeutelspacher LeoniFriedrich
 */

public class ReportShowForm extends VerticalPanel{

	/**
	 * Benoetigte Panel werden hier instanziiert.
	 */
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	
	private User selectedUser = CurrentUser.getUser(); 
	
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
	 * Speicher fuer das Startdate als SQL-Date
	 */
	private Date sqlStartDate = null;
	
	/**
	 * Speicher fuer das Enddate als SQL-
	 */
	private Date sqlEndDate = null;
	
	/**
	 * Speicher fuer die ausgewaehlte Gruppe
	 */
	private Group selectedGroup = null;
	
	/**
	 * Speicher fuer alle Gruppen eines Users
	 */
	private ArrayList<Group> groupsOfCurrentUser = null;

	/**
	 * Instanziierung des asynchronen Interfaces, um auf die Methoden der ShoppinglistAdministrationImpl zuzugreifen.
	 * 
	 */
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	/**
	 * Instanziierung des asynchronen Interfaces, um auf doe Methoden der ReportAdministrationImpl zuzugreifen.
	 */
	private ReportGeneratorAsync reportGenerator = null;
	
	public ReportShowForm () {
	/**
	 * Befuellen der Dropdown-Liste mit den<code>Gruppen</code> Namen eines Users.
	 */
		
//	shoppinglistAdministration.getGroupsOf(selectedUser, new GetGroupsOfUserCallback());
//	Window.alert(selectedUser.getName());
		shoppinglistAdministration.getAllGroups(new GetAllGroupsCallback());
	}
	
	public void onLoad() {
		if (selectedUser == null) {
		Window.alert("User kommt nicht an");	
		}else {
			Window.alert(selectedUser.getGmailAddress());
		}
		
		/**
		 *  Alle Gruppen des aktuellen Users werden zwischenespeichert.
		 */
//		groupsOfCurrentUser = this.shoppinglistAdministration.getGroupsOf(user, new GetGroupsCallback());
		
		if(reportGenerator == null) {
			ClientsideSettings.getReportGenerator();
		}
		
		/**
		 * Gruppennamen werden der Drop-Downliste hinzugefuegt..
		 */
		if(groupsOfCurrentUser != null) {
			for(Group g : groupsOfCurrentUser) {
				//Hinzufuegen der einzelnen Gruppen zur DropList
				groupSelector.addItem(g.getName());		
			}
			
		}
		
		mainPanel.add(groupSelector);
		
		startDateBox.setValue(new java.util.Date());
		mainPanel.add(startDateBox);
		
		endDateBox.setValue(new java.util.Date());
		mainPanel.add(endDateBox);
		
		addPanel.add(showReportButton);
		
		this.add(mainPanel);
		this.add(addPanel);
		
		
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
				
				//Ausfuehren der Report-Erstellung
				reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate, new CreateAllListitemsOfGroupReport());
				
			}
		});
	}
	

	private class CreateAllListitemsOfGroupReport implements AsyncCallback<AllListitemsOfGroupReport> {

		@Override
		public void onFailure(Throwable caught) {
			//Ausgeben einer Log-Message, wenn ein Fehler auftritt
			ClientsideSettings.getLogger().severe("Erzeugen des Reports fehlgeschlagen");
		}

		@Override
		public void onSuccess(AllListitemsOfGroupReport report) {
			if(report != null) {
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(report);
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new HTML(writer.getReportText()));
			}
			
		}
		
	}
	
	public User getUser() {
		return selectedUser;	
		}
		
		public void setSelectedUser(User selectedUser) {
			this.selectedUser = selectedUser;
		}
		/**
		 * Befuellen der Dropdown-Liste mit <code>Gruppen</code> Namen.
		 */
	
	private class GetGroupsOfUserCallback implements AsyncCallback<ArrayList<Group>> {
		
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			Window.alert("Fehler" + caught.toString());	
			}

			@Override
			public void onSuccess(ArrayList<Group> result) {
				groupsOfCurrentUser = result;
				for (int i = 0; i < result.size(); i++) {
					groupSelector.addItem(result.get(i).getName());
					
			}
	
	}

			
	}
	private class GetAllGroupsCallback implements AsyncCallback <ArrayList<Group>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("nicht erfolgreich");
		}

		@Override
		public void onSuccess(ArrayList<Group> result) {
			Window.alert("erfolgreich");
			
		}
		
	}
	
}
