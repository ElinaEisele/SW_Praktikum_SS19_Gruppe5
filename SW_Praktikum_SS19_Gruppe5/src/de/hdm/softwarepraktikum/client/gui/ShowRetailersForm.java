package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
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
public class ShowRetailersForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label();
	private FlexTable retailersFlexTable = new FlexTable();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button cancelButton = new Button("Schließen");
	private Button saveButton = new Button("Speichern");
	private TextBox retailerTextBox = new TextBox();
	
	private Group selectedGroup = null;
	private ArrayList<String> retailers = new ArrayList<String>();

	public ShowRetailersForm() {
				
		cancelButton.setStylePrimaryName("cancelButton");
		cancelButton.addClickHandler(new CancelClickHandler());
		saveButton.addClickHandler(new SaveClickHandler());

		infoLabel.setText("Alle Händler im System");
		
		buttonPanel.add(retailerTextBox);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(infoLabel);
		mainPanel.add(retailersFlexTable);
		mainPanel.add(buttonPanel);

	}
	
	public void onLoad() {
		
		shoppinglistAdministration.getAllRetailers(new ShowRetailersCallback());

		RootPanel.get("main").add(mainPanel);
		
	}
	
	
	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}



	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			GroupShowForm gsf = new GroupShowForm();
			gsf.setSelected(selectedGroup);
			gsf.setGstvm(gstvm);
			gstvm.setGroupShowForm(gsf);
			RootPanel.get("main").add(gsf);
		}

	}
		private class ShowRetailersCallback implements AsyncCallback<ArrayList<Retailer>>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Folgender Fehler ist aufgetreten: \n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			
			retailersFlexTable.setText(0, 0, "Händler");
			
			int i = 1;
			for (Retailer r : result) { 
				retailers.add(r.getName());
				retailersFlexTable.setText(i, 0, r.getName());
				i++;
			}

		}
		
	}
		
	private class SaveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (retailerTextBox.getValue() == "") {
				Notification.show("Ein Händler muss einen Namen haben.");
			} else if (retailers.contains(retailerTextBox.getValue())){
				Notification.show("Dieser Händler existiert schon.");
			} else {
				shoppinglistAdministration.createRetailer(retailerTextBox.getValue(), new NewRetailerCallback());

			}
			

		}
		
	}
	
	private class NewRetailerCallback implements AsyncCallback<Retailer>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Retailer result) {
			int row = retailersFlexTable.getRowCount();
			retailersFlexTable.setText(row, 0, retailerTextBox.getValue());
			retailers.add(result.getName());
			
			retailerTextBox.setText("");

		}
		
	}
}
