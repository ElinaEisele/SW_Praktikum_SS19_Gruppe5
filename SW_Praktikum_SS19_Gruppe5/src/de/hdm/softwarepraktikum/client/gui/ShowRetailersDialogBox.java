package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Retailer;

/**
 * Klasse zur Darstellung einer Dialogbox, wenn alle Retailer angezeigt werden
 * sollen
 * 
 * @author ElinaEisele, JonasWagenknecht, LeoniFriedrich
 *
 */
public class ShowRetailersDialogBox extends DialogBox {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();


	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label();
	private FlexTable retailersFlexTable = new FlexTable();
	private Button cancelButton = new Button("Schlieﬂen");

	public ShowRetailersDialogBox() {

		this.setGlassEnabled(true);

		cancelButton.setStylePrimaryName("cancelButton");
		cancelButton.addClickHandler(new CancelClickHandler());

		infoLabel.setText("Platzhalter Gruppenname");


		mainPanel.add(infoLabel);
		mainPanel.add(cancelButton);

		this.add(mainPanel);
		this.center();
	}


	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ShowRetailersDialogBox.this.hide();
		}

	}
		private class ShowRetailersCallback implements AsyncCallback<ArrayList<Retailer>>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: \n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			
			retailersFlexTable.setText(0, 0, "H‰ndler");
			
			int i = 1;
			
			for (Retailer r : result) { 
				retailersFlexTable.setText(i, 0, r.getName());
				i++;
			}
		}
		
	}
}
