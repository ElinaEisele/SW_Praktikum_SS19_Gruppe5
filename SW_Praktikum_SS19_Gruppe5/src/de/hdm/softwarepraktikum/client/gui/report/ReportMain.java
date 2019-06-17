package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author CarlaHofmann & LeoniFriedrich
 *
 */

public class ReportMain {

	private ReportHeader reportHeader = null;
	private ReportShowForm reportShowForm = null;
	private ReportTrailer reportTrailer = null;

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
