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
		
		RootPanel.get("main").clear();
		RootPanel.get("aside").clear();

		RootPanel.get("main").setVisible(true);
		RootPanel.get("aside").setVisible(false);


		RootPanel.get("trailer").add(reportTrailer);
		RootPanel.get("main").add(reportShowForm);
		RootPanel.get("header").add(reportHeader);
	}
}
