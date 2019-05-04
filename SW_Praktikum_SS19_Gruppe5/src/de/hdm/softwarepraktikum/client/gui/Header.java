package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;


/**
 * Klasse zur Darstellung des Headers der Applikation. Dieses umfasst
 * ein Button fuer den Editor, einen fuer den ReportGenerator, 
 * einen fuer den Logout und den Namen der Anwendung.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Header extends HorizontalPanel{
	
	Button editor = null;
	Button reportGenerator = null;
	Image logo = null;
	Label applicationLabel = null;
	Button logout = null;
	
	public void onLoad() {
		super.onLoad();
		
		editor = new Button("Editor");
		reportGenerator = new Button("Report");
		
		logo = new Image();
		logo.setUrl("images/Maultasche.png");
		logo.setPixelSize(100, 70);
		
		applicationLabel = new Label("Maul Tasche");
		logout = new Button("Logout");
		logout.setStylePrimaryName("logout-Button");
		
		this.add(editor);
		this.add(reportGenerator);
		this.add(logo);
		this.add(applicationLabel);
		this.add(logout);
	}

}
