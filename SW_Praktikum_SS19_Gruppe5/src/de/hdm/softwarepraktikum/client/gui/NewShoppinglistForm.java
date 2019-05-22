package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse zur Darstellung eines Formulars, um eine neue <code>Soppinglist</code> anzulegen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class NewShoppinglistForm extends VerticalPanel{
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private GroupShoppinglistTreeViewModel gstvm = null;
	private Group selectedGroup = null;
	private GroupHeader groupHeader = null;
	private ShoppinglistShowForm shoppinglistShowForm = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label("Neue Einkaufsliste erstellen");
	private Grid grid = new Grid(1, 2);
	private Label nameLabel = new Label("Name");
	private TextBox nameTextBox = new TextBox();
	
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");
	
	public NewShoppinglistForm(Group g) {
		
		this.selectedGroup = g;
		
		grid.setWidget(0, 0, nameLabel);
		grid.setWidget(0, 1, nameTextBox);
		
		saveButton.addClickHandler(new SaveClickHandler());
		cancelButton.addClickHandler(new CancelClickHancler());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(infoLabel);
		mainPanel.add(grid);
		mainPanel.add(buttonPanel);
		
	}
	
	public void onLoad() {
		
		RootPanel.get("main").add(mainPanel);
		
	}
	
	
	public GroupHeader getGroupHeader() {
		return groupHeader;
	}

	public void setGroupHeader(GroupHeader groupHeader) {
		this.groupHeader = groupHeader;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}
	
	private class SaveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
//			if (selectedGroup != null) {
				shoppinglistAdministration.createShoppinglistFor(selectedGroup, nameTextBox.getValue(), new NewShoppinglistAsyncCallback());
				RootPanel.get("main").clear();
				ShoppinglistShowForm ssf = new ShoppinglistShowForm();
				// die shoppinglistShowForm enthält schon die neu erstellte Shoppinglist (siehe Callback)
				RootPanel.get("main").add(shoppinglistShowForm);

//			}
		}
		
	}
	
	private class CancelClickHancler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
//			if (selectedGroup != null) {
				RootPanel.get("main").clear();
				GroupShowForm gsf = new GroupShowForm();
				RootPanel.get("main").add(gsf);
//			}
		}
		
	}
	
	private class NewShoppinglistAsyncCallback implements AsyncCallback<Shoppinglist>{

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Einkaufslistenerstellung ist fehlgeschlagen.");
		}

		@Override
		public void onSuccess(Shoppinglist result) {
			gstvm.addShoppinglistOfGroup(result, selectedGroup);
			// die neu erstellte Shoppinglist wird in der ShoppinglistShowForm gesetzt
			shoppinglistShowForm.setSelected(result);
		}
		
	}

}
