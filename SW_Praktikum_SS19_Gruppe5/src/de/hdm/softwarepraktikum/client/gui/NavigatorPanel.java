package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

public class NavigatorPanel extends VerticalPanel {

	static interface ShoppinglistTreeResources extends CellTree.Resources {
		
		@Source("selectedObjectBackground.png")
	    ImageResource selectedObjectBackground();
	    @Override
		@Source("ShoppinglistCellTree.css")
	    CellTree.Style cellTreeStyle(); 
	}

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private User u = CurrentUser.getUser();
	private Group selectedGroup = null;
	private Shoppinglist selectedShoppinglist = null;
	private GroupShowForm gsf;
	private ShoppinglistShowForm ssf;

	private GroupShoppinglistTreeViewModel gstvm;
	private VerticalPanel mainPanel = new VerticalPanel();
	private Button newGroupButton = new Button("Neue Gruppe erstellen");

	private CellTree cellTree;
	private Label refreshInfoLabel = new Label();

	public void onLoad() {

		final Timer timer = new Timer() {

			@Override
			public void run() {
				NavigatorPanel.this.refreshInfo();
				schedule(10000);
			}

		};

		timer.schedule(10000);

		gsf = new GroupShowForm();
		ssf = new ShoppinglistShowForm();
		gstvm = new GroupShoppinglistTreeViewModel();
		
		CellTree.Resources shoppinglistTreeResource = GWT.create(ShoppinglistTreeResources.class);
		cellTree = new CellTree(gstvm, "Root", shoppinglistTreeResource);
		cellTree.setAnimationEnabled(true);

		gstvm.setGroupShowForm(gsf);
		gsf.setGstvm(gstvm);

		gstvm.setShoppinglistShowForm(ssf);
		ssf.setGstvm(gstvm);

		newGroupButton.addClickHandler(new NewGroupClickHandler());
		newGroupButton.setStyleName("NavButton");
		
		cellTree.setStyleName("cellTree");
		
		mainPanel.add(newGroupButton);
		mainPanel.add(cellTree);

		this.add(mainPanel);

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

	public GroupShowForm getGsf() {
		return gsf;
	}

	public void setGsf(GroupShowForm gsf) {
		this.gsf = gsf;
	}

	public void refreshInfo() {
		shoppinglistAdministration.refreshData(this.getGstvm().getUserGroups(), u, new RefreshDataCallback());
	}

	private class RefreshDataCallback implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
//			Notification.show(caught.toString());	
		}

		@Override
		public void onSuccess(Boolean result) {
			if (result == true) {
//				refreshInfoLabel.setText("Es gibt Änderungen Ihrer Shoppingliste, bitte Seite aktualisieren!");
				refreshInfoLabel.setStyleName("refreshInfoLabel");
				RootPanel.get("header").add(refreshInfoLabel);

			} else {
				refreshInfoLabel.setText("");
			}
		}
	}

	private class NewGroupClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (u != null) {
				selectedGroup = gstvm.getSelectedGroup();

				NewGroupForm ngf = new NewGroupForm();
				if (selectedGroup != null) {
					ngf.setOldSelectedGroup(selectedGroup);
				}
				GroupShowForm gsf = new GroupShowForm(ngf);
				if (selectedGroup != null) {
					gsf.setSelected(selectedGroup);
}


			}
		}

	}


}
