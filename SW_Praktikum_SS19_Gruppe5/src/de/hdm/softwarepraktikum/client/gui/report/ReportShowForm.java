package de.hdm.softwarepraktikum.client.gui.report;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;


/**
 * Diese Klasse stellt den "main"-Teil der Report-HTML Seite dar.
 * 
 * @author TimBeutelspacher & LeoniFriedrich
 */

public class ReportShowForm extends VerticalPanel{

	/**
	 * Benoetigte Panel werden hier instanziiert.
	 */
	private VerticalPanel mainPanel = new VerticalPanel();

	private HorizontalPanel addPanel = new HorizontalPanel();

	private Grid reportGrid; 
	
	/**
	 * Speicher fuer das Start- und Enddatum 
	 */
	private Date sqlStartDate = null;
	private Date sqlEndDate = null;
	
	/**
	 * Speicher für Filteroption kein Datum in SQL
	 */
	private Boolean noDate = false;
	
	/**
	 * Speicher fuer den momentanen User
	 */
	private User selectedUser = CurrentUser.getUser();
	
	/**
	 * Speicher fuer die ausgewaehlte Gruppe
	 */
	private Group selectedGroup = null;
	
	/**
	 *Speicher fuer den ausgewaehlten Retailer 
	 */
	
	private Retailer selectedRetailer = null;
	
	/**
	 * Speicher fuer alle Gruppen eines Users
	 */
	private ArrayList<Group> groupsOfCurrentUser;
	
	/**
	 * Speicher fuer alle Retailer
	 */

	private ArrayList<Retailer> allRetailers;
	
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	private Button showReportButton = new Button("Report anzeigen");
	private Button getBackButton = new Button("Zurück");
	private DateBox startDateBox = new DateBox();
	private DateBox endDateBox = new DateBox();
	private ListBox groupSelectorListBox = new ListBox();
	private ListBox retailerSelectorListBox = new ListBox();
	private CheckBox dateCheckBox = new CheckBox();
	
	/**
	 * Instanziierung des asynchronen Interfaces, um auf die Methoden der 
	 * ShoppinglistAdministrationImpl zuzugreifen.
	 */	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	/**
	 * Instanziierung des asynchronen Interfaces, um auf die Methoden der 
	 * ReportGeneratorImpl zuzugreifen.
	 */
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	

	
	public ReportShowForm () {
		
		Label newReportLabel = new Label ("Neuen Report erstellen");
		newReportLabel.setStyleName("Title");
		
		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		reportGrid = new Grid (5, 4);
		
		Label groupLabel = new Label ("Deine Gruppen: ");
		groupLabel.setStyleName("Heading");
		reportGrid.setWidget(0, 0, groupLabel);
		reportGrid.setWidget(0, 1, groupSelectorListBox);
		
		Label startDateLabel = new Label ("Startdatum wählen: ");
		startDateLabel.setStyleName("Heading");
		reportGrid.setWidget(1, 0, startDateLabel);
		startDateBox.setValue(new java.util.Date());
		reportGrid.setWidget(1, 1, startDateBox);
		reportGrid.setWidget(1, 2, dateCheckBox);
		
		Label dateCheckBoxLabel = new Label("Nicht nach Datum filtern");
		dateCheckBoxLabel.setStyleName("Heading");
		reportGrid.setWidget(1, 3, dateCheckBoxLabel);
		dateCheckBox.addClickHandler(new NoDateClickHandler());
		
		Label endDateLabel = new Label ("Enddatum wählen: ");
		endDateLabel.setStyleName("Heading");
		reportGrid.setWidget(2, 0, endDateLabel);
		endDateBox.setValue(new java.util.Date());
		reportGrid.setWidget(2, 1, endDateBox);
		
		Label retailerLabel = new Label ("Händler wählen: ");
		retailerLabel.setStyleName("Heading");
		reportGrid.setWidget(3, 0, retailerLabel);
		reportGrid.setWidget(3, 1, retailerSelectorListBox);
		
		Label showReportButtonLabel = new Label ();
		showReportButtonLabel.setStyleName("Heading");
		reportGrid.setWidget(4, 0, showReportButtonLabel);
		reportGrid.setWidget(4, 3, showReportButton);
		showReportButton.addClickHandler(new ShowReportClickHandler());
		showReportButton.setStyleName("NavButton");
		
		mainPanel.add(newReportLabel);
		mainPanel.add(reportGrid);	
		
		/**
		 * Zum Befuellen der Dropdown-Liste mit <code>Group</code>-Objekten.
		 */
		reportGenerator.getAllGroupsOf(selectedUser, new GetAllGroupsOfCallback());
		
		/**
		 * Zum Befuellen der Dropdown-Liste mit <code>Retailer</code>-Objekten.
		 */
		reportGenerator.getAllRetailers(new GetAllRetailersCallback());			
	}
	
	/**
	 * In dieser Methode werden die Widgets dem entsprechenden div-Element
	 * hinzugefuegt.
	 * 
	 */
	public void onLoad() {	
		RootPanel.get("reportMain").add(mainPanel);
	}
	
	/**
	 * ***************************************************************************
	 * Abschnitt der Getter- und Setter-Methoden
	 * ***************************************************************************
	 */

	public User getUser() {
		return selectedUser;	
	}
		
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}	

	public Group getGroup() {
		return selectedGroup;	
	}
		
	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */
				
	/**
	 * 
	 * ClickHandler, um zur Anzeige des Reports mit den gewählten Filteroptionen zu gelangen.
	 *
	 */
	private class ShowReportClickHandler implements ClickHandler {
		
			public void onClick(ClickEvent event) {
				
				selectedGroup = groupsOfCurrentUser.get(groupSelectorListBox.getSelectedIndex());
				
				sqlStartDate = new java.sql.Date(startDateBox.getValue().getTime());
				sqlEndDate = new java.sql.Date(endDateBox.getValue().getTime());
				
				selectedRetailer = allRetailers.get(retailerSelectorListBox.getSelectedIndex());
				
				if(noDate == true && selectedRetailer.getId() == 0) {
					Notification.show("Du musst mindestens ein Datum oder einen Händler auswählen.");
					
				}else {
					if (noDate == true) {
						reportGenerator.createAllListitemsOfGroupReport(selectedGroup, selectedRetailer, new CreateAllListitemsOfGroupReport());
					
					}else if (selectedRetailer.getId() == 0){
						reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate, new CreateAllListitemsOfGroupReport());
					
					}else {
						reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate, selectedRetailer, new CreateAllListitemsOfGroupReport());
					}
				}
			}
	}
	
	/**
	 * 
	 * ClickHandler, um anzugeben, dass nicht nach Datum gefiltert werden soll.
	 *
	 */
	private class NoDateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			noDate = true;			
		}
		
	}
	
	/**
	 * 
	 * ClickHandler, um aus der Reportansicht zurück zur <code>ReportShowForm</code> zu gelangen.
	 *
	 */
	private class GetBackClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			ReportShowForm rsf = new ReportShowForm();
			RootPanel.get("reportMain").clear();
			RootPanel.get("reportMain").add(rsf);
			
		}
		
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * 
	 * Zum Anzeigen des Reports mit den gewählten Filteroptionen.
	 *
	 */
	private class CreateAllListitemsOfGroupReport implements AsyncCallback<AllListitemsOfGroupReport> {

		@Override
		public void onFailure(Throwable caught) {
			//Ausgeben einer Log-Message, wenn ein Fehler auftritt
			ClientsideSettings.getLogger().severe("Erzeugen des Reports fehlgeschlagen");
		}

		@Override
		public void onSuccess(AllListitemsOfGroupReport result) {
			if(result != null) {
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(result);
				addPanel.add(new HTML(writer.getReportText()));
				
				RootPanel.get("reportMain").clear();
				RootPanel.get("reportMain").add(addPanel);
				RootPanel.get("reportMain").add(getBackButton);
				getBackButton.addClickHandler(new GetBackClickHandler());
			}
		}
		
	}
	
	/**
	 * 
	 * Zum Befuellen der Dropdown-Liste mit <code>Group</code>-Objekten.
	 *
	 */
	private class GetAllGroupsOfCallback implements AsyncCallback<ArrayList<Group>> {
		
			@Override
			public void onFailure(Throwable caught) {
				Notification.show("Fehler " + caught.toString());	
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
	
	/**
	 * 
	 * Zum Befuellen der Dropdown-Liste mit <code>Retailer</code>-Objekten.
	 *
	 */
	private class GetAllRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Keine Retailer");
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			ArrayList<Retailer> result1 = new ArrayList<Retailer>();
			Retailer noRetailer = new Retailer(0, "Kein Haendler ausgewaehlt");
			result1.add(noRetailer);
			result1.addAll(result);
			allRetailers = result1;
			
			for (int i = 0; i < result1.size(); i++) {
				retailerSelectorListBox.addItem(result1.get(i).getName());
				selectedRetailer = result1.get(0);
			}
			
		}
		
	}
}
