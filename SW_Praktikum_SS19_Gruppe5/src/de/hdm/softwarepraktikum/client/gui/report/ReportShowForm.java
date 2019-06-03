package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ShoppinglistReportEntry.CurrentReportUser;
import de.hdm.softwarepraktikum.shared.bo.User;

public class ReportShowForm extends VerticalPanel{

	Label reportLabel = new Label("Report Generator");
	private User user = CurrentReportUser.getUser();
	
	public void onLoad() {
		this.add(reportLabel);
	}
}
