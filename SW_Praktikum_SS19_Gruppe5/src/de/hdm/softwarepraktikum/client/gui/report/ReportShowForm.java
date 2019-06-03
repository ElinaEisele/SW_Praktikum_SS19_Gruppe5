package de.hdm.softwarepraktikum.client.gui.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistReportEntry.CurrentReportUser;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;

public class ReportShowForm extends VerticalPanel{

//	/**
//	 * Diese Klasse stellt den "main"-Teil der Report-HTML Seite dar.
//	 * 
//	 * @author TimBeutelspacher
//	 */
//	
//	/**
//	 * Benoetigte Panel werden hier instanziiert.
//	 */
//	private VerticalPanel mainPanel = new VerticalPanel();
//	private HorizontalPanel addPanel = new HorizontalPanel();
//	
//	/**
//	 * Button zum anzeigen des Reports
//	 */
//	private Button showReportButton = new Button("Report anzeigen");
//	
//	/**
//	 * DateBox-Widget zum auswaehlen des Startdatums
//	 */
//	private DateBox startDateBox = new DateBox();
//	
//	/**
//	 * DateBox-Widget zum auswaehlen des Startdatums
//	 */
//	private DateBox endDateBox = new DateBox();
//	
//	/**
//	 * Drop-Down-Liste zur Gruppenauswahl
//	 */
//	private ListBox groupSelector = new ListBox();
//	
//	/**
//	 * Speicher für das Startdate als SQL-Date
//	 */
//	private java.sql.Date sqlStartDate = null;
//	
//	/**
//	 * Speicher für das Enddate als SQL-
//	 */
//	private java.sql.Date sqlEndDate = null;
//	
//	/**
//	 * Speicher für die ausgewaehlte Gruppe
//	 */
//	private Group selectedGroup = null;
//	
//	/**
//	 * Speicher für alle Gruppen eines Users
//	 */
//	private ArrayList<Group> groupsOfCurrentUser = null;
//	
//	/**
//	 * Instanziierung des asynchronen Interfaces, um auf die Methoden der ShoppinglistAdministrationImpl zuzugreifen.
//	 */
//	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
//	
//	/**
//	 * Instanziierung des asynchronen Interfaces, um auf doe Methoden der ReportAdministrationImpl zuzugreifen.
//	 */
//	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
//	
//	/**
//	 * zwischenspeicher für den aktuellen User
//	 */
//	private User user = CurrentReportUser.getUser();
//	
//	public void onLoad() {
//		
//		/**
//		 *  Alle Gruppen des aktuellen Users werden zwischenespeichert.
//		 */
////		groupsOfCurrentUser = this.shoppinglistAdministration.getGroupsOf(user, new GetGroupsCallback());
//		
//		/**
//		 * Gruppennamen werden der Drop-Downliste hinzufügen.
//		 */
//		if(groupsOfCurrentUser != null) {
//			for(Group g : groupsOfCurrentUser) {
//				//Hinzufuegen der einzelnen Gruppen zur DropList
//				groupSelector.addItem(g.getName());		
//			}
//		}
//		
//		mainPanel.add(groupSelector);
//		
//		startDateBox.setValue(new Date());
//		mainPanel.add(startDateBox);
//		
//		endDateBox.setValue(new Date());
//		mainPanel.add(endDateBox);
//		
//		addPanel.add(showReportButton);
//		
//		
//		RootPanel.get("main").add(mainPanel);
//		RootPanel.get("aside").add(addPanel);
//		
//		
//		groupSelector.addChangeHandler(new ChangeHandler() {
//			@Override
//			public void onChange(ChangeEvent event) {
//				selectedGroup = groupsOfCurrentUser.get(groupSelector.getSelectedIndex());
//			}
//			
//		});
//		
//		showReportButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				//Eingegebenes Startdate festhalten
//				sqlStartDate = new java.sql.Date(startDateBox.getValue().getTime());
//				
//				//Eingegebenes Enddate festhalten
//				sqlEndDate = new java.sql.Date(endDateBox.getValue().getTime());
//				
//				//Ausführen der Report-Erstellung
//				reportGenerator.createAllListitemsOfGroupReport(selectedGroup, sqlStartDate, sqlEndDate, new CreateAllListitemsOfGroupReport());
//				
//			}
//		});
//	}
	
//	private class CreateAllListitemsOfGroupReport implements AsyncCallback<AllListitemsOfGroupReport> {
//
//		@Override
//		public void onFailure(Throwable caught) {
//			//Ausgeben einer Log-Message, wenn ein Fehler auftritt
//			ClientsideSettings.getLogger().severe("Erzeugen des Reports fehlgeschlagen");
//		}
//
//		@Override
//		public void onSuccess(AllListitemsOfGroupReport report) {
//			if(report != null) {
//				HTMLReportWriter writer = new HTMLReportWriter();
//				writer.process(report);
//				RootPanel.get("main").clear();
//				RootPanel.get("main").add(new HTML(writer.getReportText()));
//			}
//			
//		}
//		
//	}
}
