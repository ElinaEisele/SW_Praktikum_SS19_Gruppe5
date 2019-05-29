package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.report.ReportHeader;
import de.hdm.softwarepraktikum.client.gui.report.ReportShowForm;
import de.hdm.softwarepraktikum.client.gui.report.ReportTrailer;

/**
 * Entry-Point Klasse des Projekts <b>MaulTasche</b> fuer den Report Generator.
 * 
 * @author LeoniFriedrich
 */

public class ShoppinglistReportEntry implements EntryPoint {
	
	private ReportHeader reportHeader = null;
	private ReportShowForm reportShowForm = null;
	private ReportTrailer reportTrailer = null;

	public void onModuleLoad() {

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