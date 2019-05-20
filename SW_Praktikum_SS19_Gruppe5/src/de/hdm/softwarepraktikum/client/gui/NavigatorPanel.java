package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

public class NavigatorPanel extends VerticalPanel{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private User user = CurrentUser.getUser();
	private Group selectedGroup = null;
	private Shoppinglist selectedShoppinglist = null;
	private GroupShowForm gsf = new GroupShowForm();
	private ShoppinglistShowForm slsf = new ShoppinglistShowForm();
	
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();
	private HorizontalPanel cellTreePanel = new HorizontalPanel();
	
	private CellTree cellTree = new CellTree(gstvm, "Root");
	private Label refreshInfoLabel = new Label();
		
	public void onLoad() {
		
		final Timer timer = new Timer() {

			@Override
			public void run() {
				NavigatorPanel.this.refreshInfo();
				
			}
			
		};
		
	}
	
	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}
	
	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}
	
	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	public void setSelectedShoppinglist(Shoppinglist selectedShoppinglist) {
		this.selectedShoppinglist = selectedShoppinglist;
	}

	public void refreshInfo() {
		shoppinglistAdministration.refreshData(this.getGstvm().getUserGroups(), user, new RefreshDataCallback());
	}
	
	private class RefreshDataCallback implements AsyncCallback<Boolean>{

		@Override
		public void onFailure(Throwable caught) {
//			Notification.show(caught.toString());	
		}

		@Override
		public void onSuccess(Boolean result) {
			if(result == true) {
				refreshInfoLabel.setText("Es gibt Änderungen Ihrer Shoppingliste, bitte Seite aktualisieren!");
				refreshInfoLabel.setStyleName("refreshInfoLabel");
				RootPanel.get("header").add(refreshInfoLabel);
		
		} else {
			refreshInfoLabel.setText("");
			}
		}
}
	
}
