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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	private Grid reportGrid;
	
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
	private ListBox groupSelectorListBox = new ListBox();
	
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
	 */
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	
//	/**
//	 * Instanziierung des asynchronen Interfaces, um auf doe Methoden der ReportAdministrationImpl zuzugreifen.
//	 */
//	private ReportGeneratorAsync reportGenerator = null;
//	
	
	
	
	
	public ReportShowForm () {
		
		reportGrid = new Grid (5, 2);
		
		Label newReportLabel = new Label ("Neuen Report erstellen");
		reportGrid.setWidget(0, 0, newReportLabel);
		
		Label groupLabel = new Label ("Deine Gruppen: ");
		reportGrid.setWidget(1, 0, groupLabel);
		reportGrid.setWidget(1, 1, groupSelectorListBox);
		
		Label startDateLabel = new Label ("Startdatum waehlen: ");
		reportGrid.setWidget(2, 0, startDateLabel);
		startDateBox.setValue(new java.util.Date());
		reportGrid.setWidget(2, 1, startDateBox);
		
		Label endDateLabel = new Label ("Enddatum waehlen: ");
		reportGrid.setWidget(3, 0, endDateLabel);
		endDateBox.setValue(new java.util.Date());
		reportGrid.setWidget(3, 1, endDateBox);
		
		Label showReportButtonLabel = new Label ();
		reportGrid.setWidget(4, 0, showReportButtonLabel);
		reportGrid.setWidget(4, 1, showReportButton);
		
		mainPanel.add(reportGrid);
	
//		reportGenerator.getGroupsOf(selectedUser, new GetGroupsOfUserCallback());	
		
	}
	
	public void onLoad() {
		
		RootPanel.get("reportMain").add(mainPanel);
		
//		if(reportGenerator == null) {
//			reportGenerator = ClientsideSettings.getReportGenerator();
//		}
		
		if (selectedUser == null) {
			Window.alert("User kommt nicht an");	
		}else {
			Window.alert(selectedUser.getGmailAddress());
		}
		
		
		if(groupsOfCurrentUser != null) {
			for(Group g : groupsOfCurrentUser) {
				//Hinzufuegen der einzelnen Gruppen zur DropList
				groupSelectorListBox.addItem(g.getName());		
			}
		}else {
			Window.alert("Hat nicht geklappt");
		}
	}
	

	public User getUser() {
		return selectedUser;	
	}
		
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	private class GroupSelectorListBoxChangeHandler implements ChangeHandler{
			
		public void onChange(ChangeEvent event) {
			selectedGroup = groupsOfCurrentUser.get(groupSelectorListBox.getSelectedIndex());	
		}
	}
				
	private class ShowReportClickHandler implements ClickHandler {
		
			public void onClick(ClickEvent event) {
				//Eingegebenes Startdate festhalten
				sqlStartDate = new java.sql.Date(startDateBox.getValue().getTime());
				
				//Eingegebenes Enddate festhalten
				sqlEndDate = new java.sql.Date(endDateBox.getValue().getTime());
				
				//Ausfuehren der Report-Erstellung
				reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate, new CreateAllListitemsOfGroupReport());
			}
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
	
	private class GetGroupsOfUserCallback implements AsyncCallback<ArrayList<Group>> {
		
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler " + caught.toString());	
			}

			@Override
			public void onSuccess(ArrayList<Group> result) {
				groupsOfCurrentUser = result;
				for (int i = 0; i < result.size(); i++) {
					groupSelectorListBox.addItem(result.get(i).getName());
					selectedGroup = result.get(0);	
				}
			}
	}
	
}
