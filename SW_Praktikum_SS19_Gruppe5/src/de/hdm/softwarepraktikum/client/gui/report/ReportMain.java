package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.RootPanel;

public class ReportMain {

	private ReportHeader reportHeader = null;
	private ReportShowForm reportShowForm = null;
	private ReportTrailer reportTrailer = null;

	public void loadForms() {
		
		reportHeader = new ReportHeader();
		reportShowForm = new ReportShowForm();
		reportTrailer = new ReportTrailer();
		
		RootPanel.get("reportMain").clear();
		RootPanel.get("reportAside").clear();

		RootPanel.get("reportMain").setVisible(true);
		RootPanel.get("reportAside").setVisible(false);


//		RootPanel.get("reportTrailer").add(reportTrailer);
		RootPanel.get("reportMain").add(reportShowForm);
//		RootPanel.get("reportHeader").add(reportHeader);
	}
	
}
