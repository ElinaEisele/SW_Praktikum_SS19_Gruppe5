package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.RootPanel;

public class Report {
	
	private ReportHeader reportHeader = null;
	private ReportShowForm reportShowForm = null;
	private ReportTrailer reportTrailer = null;

	public void loadForms() {
		
		reportHeader = new ReportHeader();
		reportShowForm = new ReportShowForm();
		reportTrailer = new ReportTrailer();

		RootPanel.get("ReportMain").clear();
		RootPanel.get("ReportMain").setVisible(true);

		RootPanel.get("ReportTrailer").add(reportTrailer);
		RootPanel.get("ReportMain").add(reportShowForm);
		RootPanel.get("ReportHeader").add(reportHeader);
	}
}
