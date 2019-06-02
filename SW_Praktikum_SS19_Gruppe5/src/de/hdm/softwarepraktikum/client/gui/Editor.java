package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.TreeViewModel;


import de.hdm.softwarepraktikum.client.gui.report.ReportHeader;
import de.hdm.softwarepraktikum.client.gui.report.ReportShowForm;
import de.hdm.softwarepraktikum.client.gui.report.ReportTrailer;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;


/**
 * Diese Klasse stellt den Startpunkt des Editor-Clients dar. Alle dazu
 * relevanten HTML-Elemente werden in dieser Klasse zusammgefügt.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Editor {
	
	private String s;
	
	private User u = CurrentUser.getUser();
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();

	private Header header = null;
	private NavigatorPanel navigatorPanel = null;
	private GroupShowForm groupShowForm = null;
//	private ShoppinglistShowForm shoppinglistShowForm = null;
	private Trailer trailer = null;
	
	public void loadForms() {
				
//		shoppinglistAdministration.save(u, new AsyncCallback<Void>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Notification.show("Fail");
//			}
//
//			@Override
//			public void onSuccess(Void result) {
//				Notification.show("Success");
//			}
//			
//		});
		
//		TreeViewModel model = new GroupShoppinglistTreeViewModel();
//		CellTree tree = new CellTree(model, null);

		header = new Header();
		navigatorPanel = new NavigatorPanel();
//		groupShowForm = new GroupShowForm();
		trailer = new Trailer();
		
		RootPanel.get("main").clear();
		RootPanel.get("aside").clear();

		RootPanel.get("main").setVisible(true);
		RootPanel.get("aside").setVisible(true);


		RootPanel.get("trailer").add(trailer);
		RootPanel.get("aside").add(navigatorPanel);

	//	RootPanel.get("main").add(shoppinglistShowForm);
//		RootPanel.get("main").add(groupShowForm);
		RootPanel.get("header").add(header);
		

				
		
	}

}
