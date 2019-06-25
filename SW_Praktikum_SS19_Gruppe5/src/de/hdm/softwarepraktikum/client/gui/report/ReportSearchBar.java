//package de.hdm.softwarepraktikum.client.gui.report;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//import com.google.gwt.event.dom.client.KeyCodes;
//import com.google.gwt.event.dom.client.KeyDownEvent;
//import com.google.gwt.event.dom.client.KeyDownHandler;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Grid;
//import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.SuggestBox;
//import com.google.gwt.user.client.ui.VerticalPanel;
//
//import de.hdm.softwarepraktikum.client.ClientsideSettings;
//import de.hdm.softwarepraktikum.client.gui.FilteredShoppinglistCellTable;
//import de.hdm.softwarepraktikum.client.gui.ShoppinglistShowForm;
//import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
//import de.hdm.softwarepraktikum.shared.bo.Group;
//import de.hdm.softwarepraktikum.shared.bo.Listitem;
//import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;
//
//public class ReportSearchBar extends VerticalPanel{
//	
//	private Grid searchGrid = new Grid(1, 3);
//	private MultiWordSuggestOracle searchbar = new MultiWordSuggestOracle();
//	private SuggestBox searchSuggestBox = new SuggestBox(searchbar);
//	private Label searchLabel = new Label("Suche");
//	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
//	
//	private Group selectedGroup = null;
//	private ArrayList<Listitem> archivedListitemsOfGroup = null;
//	
//	public Group getSelectedGroup() {
//		return selectedGroup;
//	}
//
//	public void setSelectedGroup(Group selectedGroup) {
//		this.selectedGroup = selectedGroup;
//	}
//
//	public void onLoad() {
//		
//		searchSuggestBox.addKeyDownHandler(new EnterKeyDownHandler());
////		searchSuggestBox.getValueBox().addClickHandler(new RefreshClickHandler());
//		
//		searchSuggestBox.setSize("450px",  "30px");
//		searchSuggestBox.getElement().setPropertyString("default", "Suchbegriff eingeben...");
//		
//		searchGrid.setWidget(0, 0, searchLabel);
//		searchGrid.setWidget(0, 1, searchSuggestBox);
//		
//	//	shoppinglistAdministration.getListitemsNameMapBy(selectedShoppinglist, new AllListitemsCallback());
//		
//		this.add(searchGrid);
//	}
//	
//	/**
//	 * Implementierung des KeyDownHandler Events. In diesem wird nach dem Betätigen der ENTER Taste 
//	 * der Suchvorgang gestartet.
//	 */
//	private class EnterKeyDownHandler implements KeyDownHandler{
//
//		@Override
//		public void onKeyDown(KeyDownEvent event) {
//			
//			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//				
//				if (searchSuggestBox.getValue().isEmpty()==false) {
//					shoppinglistAdministration.getArchivedListitemsOf(selectedGroup, new ListitemsCallback());
//					
//				}
//			}
//		}
//		
//	}
//	
//	private class ListitemsCallback implements AsyncCallback<ArrayList<Listitem>>{
//
//		@Override
//		public void onFailure(Throwable caught) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onSuccess(ArrayList<Listitem> result) {
//		
//
//
//		}
//		
//	}
//
//
//	
//}
