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
import de.hdm.softwarepraktikum.client.gui.ShoppinglistSearchBar;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministration;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
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
	private HorizontalPanel addPanel1 = new HorizontalPanel();
	private HorizontalPanel addPanel2 = new HorizontalPanel();
	private Grid reportGrid;
	ReportSearchBar rsb = new ReportSearchBar();
	
	private User selectedUser = CurrentUser.getUser(); 
	
	/**
	 * Button zum anzeigen des Reports
	 */
	private Button showReportButton = new Button("Report anzeigen");
	
	/**
	 * Button zum Verlassen des Reports
	 */
	private Button getBackButton = new Button("Zur&uumlck");
	
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
	 * Drop-Down-Liste zur Haendlerwahl
	 */
	private ListBox retailerSelectorListBox = new ListBox();
	
	/**
	 * Checkbox fuer die Wahl ob das Datum beruecksichtigt wird. 
	 */
	private CheckBox dateCheckBox = new CheckBox();
	
	/**
	 * Speicher fuer das Startdate als SQL-Date
	 */
	private Date sqlStartDate = null;
	
	/**
	 * Speicher fuer das Enddate als SQL-
	 */
	private Date sqlEndDate = null;
	
	/**
	 * Speicher für Filteroption kein Datum
	 */
	private Boolean noDate = false;
	
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
	
	/**
	 * Instanziierung des asynchronen Interfaces, um auf die Methoden der ShoppinglistAdministrationImpl zuzugreifen.
	 */
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	/**
	 * Instanziierung des asynchronen Interfaces, um auf die Methoden der ReportAdministrationImpl zuzugreifen.
	 */
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	

	
	public ReportShowForm () {
		
		Label newReportLabel = new Label ("Neuen Report erstellen");
		newReportLabel.setStyleName("Title");
		
		reportGrid = new Grid (5, 4);
		
		Label groupLabel = new Label ("Deine Gruppen: ");
		reportGrid.setWidget(0, 0, groupLabel);
		reportGrid.setWidget(0, 1, groupSelectorListBox);
		
		Label startDateLabel = new Label ("Startdatum waehlen: ");
		reportGrid.setWidget(1, 0, startDateLabel);
		startDateBox.setValue(new java.util.Date());
		reportGrid.setWidget(1, 1, startDateBox);
		reportGrid.setWidget(1, 2, dateCheckBox);
		Label dateCheckBoxLabel = new Label("Nicht nach Datum filtern");
		reportGrid.setWidget(1, 3, dateCheckBoxLabel);
		dateCheckBox.addClickHandler(new NoDateClickHandler());
		
		Label endDateLabel = new Label ("Enddatum waehlen: ");
		reportGrid.setWidget(2, 0, endDateLabel);
		endDateBox.setValue(new java.util.Date());
		reportGrid.setWidget(2, 1, endDateBox);
		
		Label retailerLabel = new Label ("Haendler waehlen: ");
		reportGrid.setWidget(3, 0, retailerLabel);
		reportGrid.setWidget(3, 1, retailerSelectorListBox);
		
		Label showReportButtonLabel = new Label ();
		reportGrid.setWidget(4, 0, showReportButtonLabel);
		reportGrid.setWidget(4, 3, showReportButton);
		showReportButton.addClickHandler(new ShowReportClickHandler());
		
		mainPanel.add(newReportLabel);
		mainPanel.add(reportGrid);	
		
		reportGenerator.getAllGroupsOf(selectedUser, new GetAllGroupsOfCallback());
		
		reportGenerator.getAllRetailers(new GetAllRetailersCallback());
			
	}
	
	public void onLoad() {	
		
		RootPanel.get("reportMain").add(mainPanel);
	}
	

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
				
	private class ShowReportClickHandler implements ClickHandler {
		
			public void onClick(ClickEvent event) {
				
				selectedGroup = groupsOfCurrentUser.get(groupSelectorListBox.getSelectedIndex());
//				Window.alert(selectedGroup.getName());
				
				sqlStartDate = new java.sql.Date(startDateBox.getValue().getTime());
				sqlEndDate = new java.sql.Date(endDateBox.getValue().getTime());
//				Window.alert("Datum ist gesetzt.");
				
				selectedRetailer = allRetailers.get(retailerSelectorListBox.getSelectedIndex());
//				Window.alert("Dein Retailer ist: " + selectedRetailer.getName());
				
				if(noDate == true && selectedRetailer.getId() == 0) {
					Window.alert("Du musst mindestens ein Datum oder einen Händler auswählen.");
					
				}else {
					if (noDate == true) {
						reportGenerator.createAllListitemsOfGroupReport(selectedGroup, selectedRetailer, new CreateAllListitemsOfGroupReport());
//						Window.alert("if: noDate == true" );
					
					}else if (selectedRetailer.getId() == 0){
						reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate, new CreateAllListitemsOfGroupReport());
//						Window.alert("else if: selectedRetailer.getId() == 0");
					}else {
						reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate, selectedRetailer, new CreateAllListitemsOfGroupReport());
//						Window.alert("else:");
					}
				}
			
			}
	}
	
	private class NoDateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			noDate = true;			
		}
		
	}
	
	private class GetBackClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			ReportShowForm rsf = new ReportShowForm();
			RootPanel.get("reportMain").clear();
			RootPanel.get("reportMain").add(rsf);
			
		}
		
	}

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
				addPanel1.add(new HTML(writer.getReportTextHeader()));
				addPanel2.add(new HTML(writer.getReportText()));
				
				RootPanel.get("reportMain").clear();
				RootPanel.get("reportMain").add(addPanel1);
				RootPanel.get("reportMain").add(rsb);
				RootPanel.get("reportMain").add(addPanel2);
				RootPanel.get("reportMain").add(getBackButton);
				getBackButton.addClickHandler(new GetBackClickHandler());
			}
		}
	}

	
	private class GetAllGroupsOfCallback implements AsyncCallback<ArrayList<Group>> {
		
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Hier");
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
	
	private class GetAllRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Keine Retailer");
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
