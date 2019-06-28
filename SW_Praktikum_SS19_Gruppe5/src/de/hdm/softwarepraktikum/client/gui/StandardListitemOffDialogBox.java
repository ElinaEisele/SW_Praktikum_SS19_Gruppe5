package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse zur Darstellung einer Dialogbox, wenn ein User ein Listitem als
 * Standardlistitem entfernen will.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class StandardListitemOffDialogBox extends DialogBox {
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private Listitem selectedListitem = null;
	private Group selectedGroup = null;
	private Shoppinglist selectedShoppinglist = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label confirmLabel = new Label("Dieses Listitem als Standardlistitem entfernen?");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button confirmButton = new Button("Bestätigen");
	private Button cancelButton = new Button("Abbrechen");

	public StandardListitemOffDialogBox() {

		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		cancelButton.setStylePrimaryName("NavButton");
		confirmButton.setStylePrimaryName("NavButton");
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(confirmLabel);
		mainPanel.add(buttonPanel);

	}

	/**
	 * In dieser Methode werden die Widgets der Dialog Box hinzugefügt
	 * 
	 */
	public void onLoad() {
		this.setGlassEnabled(true);
		this.add(mainPanel);
		this.center();
	}

	public Listitem getSelectedListitem() {
		return selectedListitem;
	}

	public void setSelectedListitem(Listitem selectedListitem) {
		this.selectedListitem = selectedListitem;
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

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Schließen der Dialog Box.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			StandardListitemOffDialogBox.this.hide();

		}

	}

	/**
	 * Schließen der Dialog Box und nicht Standard setzen des ausgewählten
	 * <code>Listitem</code>-Objekts.
	 */
	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (selectedListitem != null) {
				shoppinglistAdministration.setStandardListitem(selectedListitem, selectedGroup, false,
						new RemoveStandardCallback());
				RootPanel.get("main").clear();

				ListitemShowForm lsf = new ListitemShowForm();
				selectedListitem.setStandard(false);
				lsf.setSelected(selectedListitem);
				lsf.setSelectedShoppinglist(selectedShoppinglist);
				lsf.setSelectedGroup(selectedGroup);

				RootPanel.get("main").add(lsf);

				StandardListitemOffDialogBox.this.hide();
			} else {
				Notification.show("Es wurde kein Eintrag ausgewaehlt.");
			}
		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Keine Rückgabe bei erfolgreichem entfernen von der Eigenschaft Standard.
	 */
	private class RemoveStandardCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

		}
	}
}
