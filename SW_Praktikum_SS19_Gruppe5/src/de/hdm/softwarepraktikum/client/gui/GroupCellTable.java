package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

///**
// * Diese Klasse dient zur Darstellung aller Listen einer Gruppe.
// * 
// * @author ElinaEisele, JonasWagenknecht
// *
// */
public class GroupCellTable extends VerticalPanel {
//	
//	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
//	private GroupShoppinglistTreeViewModel gstvm;
//	private GroupShowForm groupShowForm;
//	private ListDataProvider<ShoppinglistHelpClass> listDataProvider;
//	
//	private Group groupToDisplay = null;
//	
//	private class ShoppinglistHelpClass{
//		private String shoppinglistName = new String("Fehler beim Laden.");
//		
//		public ShoppinglistHelpClass(String shoppinglistName) {
//			this.shoppinglistName = shoppinglistName;
//		}
//
//		public String getShoppinglistName() {
//			return shoppinglistName;
//		}
//
//		public void setShoppinglistName(String shoppinglistName) {
//			this.shoppinglistName = shoppinglistName;
//		}
//		
//		
//	}
//	
//	private ArrayList<ShoppinglistHelpClass> shoppinglistsHelpClass = new ArrayList<ShoppinglistHelpClass>();
//	private ArrayList<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();
//	
//	private CellTable<ShoppinglistHelpClass> table = new CellTable<ShoppinglistHelpClass>();
//	private ShoppinglistHelpClass shoppinglistHelpClass = new ShoppinglistHelpClass("");
//	
//	public GroupCellTable() {
//		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
//		listDataProvider = (ListDataProvider<ShoppinglistHelpClass>) table.getKeyProvider();
//		
//		Column<ShoppinglistHelpClass, String> shoppinglistNameToDisplay = new Column<ShoppinglistHelpClass, String>(new TextCell()){
//
//			@Override
//			public String getValue(ShoppinglistHelpClass object) {
//				return object.getShoppinglistName();
//			}
//			
//		};
//		
//		
//		shoppinglistAdministration.getShoppinglistsOf(groupToDisplay, new ShoppinglistCallback());
//
//		
//		table.addColumn(shoppinglistNameToDisplay, "Einkaufsliste");
//		table.setRowCount(shoppinglists.size(), true);
//		table.setRowData(0, shoppinglistsHelpClass);
//		
//		
//	
//	}
//	
//	private class ShoppinglistCallback implements AsyncCallback<ArrayList<Shoppinglist>>{
//
//		@Override
//		public void onFailure(Throwable caught) {
//			Notification.show("Shoppinglisten konnten nicht geladen werden.");
//		}
//
//		@Override
//		public void onSuccess(ArrayList<Shoppinglist> result) {
//			int i = 0;
//			for (Shoppinglist s : result) {
//				shoppinglistHelpClass.setShoppinglistName(s.getName());
//				
//				shoppinglistsHelpClass.add(i, shoppinglistHelpClass);
//				shoppinglistHelpClass.setShoppinglistName(null);
//				i++;
//			}
//		}
//		
//	}
//	
//	public void onLoad() {
//		this.add(table);
//	}
//
//	public GroupShowForm getGroupShowForm() {
//		return groupShowForm;
//	}
//
//	public void setGroupShowForm(GroupShowForm groupShowForm) {
//		this.groupShowForm = groupShowForm;
//	}
//
//	public Group getGroupToDisplay() {
//		return groupToDisplay;
//	}
//
//	public void setGroupToDisplay(Group groupToDisplay) {
//		this.groupToDisplay = groupToDisplay;
//	}
//	
//	
//
}

