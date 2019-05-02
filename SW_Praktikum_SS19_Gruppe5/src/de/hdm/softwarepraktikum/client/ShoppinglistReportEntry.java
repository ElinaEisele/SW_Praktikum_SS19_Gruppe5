package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry-Point Klasse des Projekts <b>MaulTasche</b> fuer den Report Generator.
 * 
 * @author ElinaEisele
 */

public class ShoppinglistReportEntry implements EntryPoint{
	
	Label reportLabel = new Label("Report Generator");



	@Override
	public void onModuleLoad() {
		
		RootPanel.get().add(reportLabel);
	}
	

}
