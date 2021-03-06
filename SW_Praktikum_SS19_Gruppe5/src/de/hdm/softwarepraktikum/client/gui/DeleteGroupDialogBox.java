package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;

/**
 * Diese Klasse dient dazu, eine Gruppe zu loeschen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class DeleteGroupDialogBox extends DialogBox{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();	
	private Group selectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmationLabel = new Label("Möchtest du diese Gruppe wirklich löschen?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Bestätigen");
	private Button cancelButton = new Button("Abbrechen");
	
	
	public DeleteGroupDialogBox() {
		
		this.setGlassEnabled(true);
		
		cancelButton.setStylePrimaryName("NavButton");
		confirmButton.setStylePrimaryName("NavButton");
		buttonPanel.setStyleName("ButtonPanel");
		confirmationLabel.setStyleName("Header");
		
		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());

		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(confirmationLabel);
		mainPanel.add(buttonPanel);
		
		this.add(mainPanel);
		this.center();
	}
	
	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
	private class CancelClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DeleteGroupDialogBox.this.hide();
			
		}
		
	}
	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der Click-/EventHandler
	 * ***************************************************************************
	 */
	
	/**
	 * Bei Bestätigen der Speichern-Schaltfläche wird das <code>Group</code>-Objekt gelöscht.
	 */
	private class ConfirmClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (selectedGroup != null) {
				shoppinglistAdministration.delete(selectedGroup, new DeleteGroupCallback(selectedGroup));
				RootPanel.get("aside").clear();
				RootPanel.get("main").clear();

				NavigatorPanel np = new NavigatorPanel();
				RootPanel.get("aside").add(np);
				DeleteGroupDialogBox.this.hide();
			} else {
				Notification.show("Es wurde keine Gruppe ausgewählt.");
			}
		}
		
	}
	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */
	
	/**
	 * Zum löschen der Gruppe im <code>CellTree</code>.
	 */
	private class DeleteGroupCallback implements AsyncCallback<Void>{
		
		Group group = null;
		
		DeleteGroupCallback(Group g){
			this.group = g;
		}
		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			if (group != null) {
				setSelectedGroup(null);
				gstvm.removeGroup(group);
				RootPanel.get("main").clear();
				RootPanel.get("aside").clear();
				NavigatorPanel np = new NavigatorPanel();
				RootPanel.get("aside").add(np);
		
			}
		}

	}
	
	

}
