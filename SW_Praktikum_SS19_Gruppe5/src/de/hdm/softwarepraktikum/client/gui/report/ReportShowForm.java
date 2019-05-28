package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReportShowForm extends VerticalPanel{

	Label reportLabel = new Label("Report Generator");
	
	public void onLoad() {
		this.add(reportLabel);
	}
}
