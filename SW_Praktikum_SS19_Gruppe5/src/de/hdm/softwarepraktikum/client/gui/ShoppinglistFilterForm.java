package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class ShoppinglistFilterForm extends VerticalPanel{
	
	ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private ShoppinglistHeader shoppinglistHeader = null;
	private Shoppinglist selectedShoppinglist = null;
	private Retailer selectedRetailer = null;
	private User selectedUser = null;
	private Group selectedGroup = null;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel filterPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Label infoLabel = new Label("Filtern deine Schoppingliste!");
	private ListBox filterOptionsListBox = new ListBox();
	private ListBox filterDetailsListBox = new ListBox();
	private Button saveButton = new Button("Fitlern");
	private Button backButton = new Button("Abbrechen");
	
	public ShoppinglistFilterForm() {
		
		filterPanel.add(filterOptionsListBox);
		filterPanel.add(filterDetailsListBox);
		
		buttonPanel.add(saveButton);
		buttonPanel.add(backButton);
		
		mainPanel.add(infoLabel);
		mainPanel.add(filterPanel);
		mainPanel.add(buttonPanel);
		
	}

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
	}

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
	public void onLoad() {
	
		shoppinglistAdministration.getRetailersOf(selectedShoppinglist, new RetailersCallback());
		
		this.add(mainPanel);

	}
	
	private class RetailersCallback implements AsyncCallback<ArrayList<Retailer>>{

		@Override
		public void onFailure(Throwable caught) {
			//
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {
			for (Retailer r : result) {
				
			}
		}
		
	}
	
	
	
	
	

}
