package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * Diese Klasse stellt den Startpunkt des Report-Clients dar. Alle dazu
 * relevanten HTML-Elemente werden in dieser Klasse zusammgefuegt.
 * 
 * @author CarlaHofmann & LeoniFriedrich
 *
 */

public class ReportMain {

	private ReportHeader reportHeader = null;
	private ReportShowForm reportShowForm = null;
	private ReportTrailer reportTrailer = null;

	/**
	 * Methode, um die Startseite des Reports zu laden.
	 */
	public void loadForms() {
		
		reportHeader = new ReportHeader();
		reportShowForm = new ReportShowForm();
		reportTrailer = new ReportTrailer();
		
		RootPanel.get("reportMain").clear();

		RootPanel.get("reportHeader").setVisible(true);
		RootPanel.get("reportMain").setVisible(true);
		RootPanel.get("reportTrailer").setVisible(true);

		RootPanel.get("reportHeader").add(reportHeader);
		RootPanel.get("reportMain").add(reportShowForm);
		RootPanel.get("reportTrailer").add(reportTrailer);
		
	}
	
}
