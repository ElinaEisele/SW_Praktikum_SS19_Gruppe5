package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Retailer;

/**
 * Klasse zur Darstellung einer Form in der alle Retailer angezeigt werden
 * können sowie Retailer hinzugefügt werden können.
 * 
 * @author ElinaEisele, JonasWagenknecht, LeoniFriedrich
 *
 */
public class RetailersForm extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;

	private Group selectedGroup = null;

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label infoLabel = new Label();
	private FlexTable retailersFlexTable = new FlexTable();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button cancelButton = new Button("Abbrechen");
	private Button saveButton = new Button("Speichern");
	private TextBox retailerTextBox = new TextBox();
	private ArrayList<String> retailers = new ArrayList<String>();

	public RetailersForm() {

		cancelButton.setStyleName("NavButton");
		cancelButton.addClickHandler(new CancelClickHandler());

		saveButton.setStyleName("NavButton");
		saveButton.addClickHandler(new SaveClickHandler());

		infoLabel.setText("Alle Händler im System");

		retailerTextBox.setText("Händlername eingeben...");
		retailerTextBox.setWidth("200px");
		
		retailerTextBox.addClickHandler(new ClearClickHandler());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(infoLabel);
		mainPanel.add(retailersFlexTable);
		mainPanel.add(retailerTextBox);
		mainPanel.add(buttonPanel);

	}

	/**
	 * In dieser Methode werden die Widgets dem entsprechenden div-Element
	 * hinzugefügt und alle Retailer des Systems geholt
	 * 
	 */
	public void onLoad() {

		infoLabel.setStyleName("Header");
		retailersFlexTable.setStyleName("FlexTable");
		
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

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Schließen der Form und öffnen der <code>GroupShowForm</code>
	 */

	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			GroupShowForm gsf = new GroupShowForm();
			gsf.setSelected(selectedGroup);
			gsf.setGstvm(gstvm);
		//	gstvm.setGroupShowForm(gsf);
			RootPanel.get("main").add(gsf);
		}

	}

	/**
	 * Bei Klick in das Textfeld wird der Text darin geleert, damit der Nutzer den
	 * Namen des neuen Retailers eingeben kann
	 */
	private class ClearClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			retailerTextBox.setText("");

		}

	}

	/**
	 * Prüfen der Korrektheit der Eingabe und speichern des Wertes
	 */
	private class SaveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (retailerTextBox.getValue() == "") {
				Notification.show("Ein Händler muss einen Namen haben.");
			} else if (retailers.contains(retailerTextBox.getValue())) {
				Notification.show("Dieser Händler existiert schon.");
			} else {
				shoppinglistAdministration.createRetailer(retailerTextBox.getValue(), new NewRetailerCallback());

			}

		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Füllen des Flextables mit den <code>Retailer</code>-Objekten
	 */
	private class ShowRetailersCallback implements AsyncCallback<ArrayList<Retailer>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Retailer> result) {

			retailersFlexTable.setText(0, 0, "");

			int i = 1;
			for (Retailer r : result) {
				retailers.add(r.getName());
				retailersFlexTable.setText(i, 0, r.getName());
				i++;
			}

		}

	}

	/**
	 * Füllen des Flextables mit den <code>Retailer</code>-Objekten einschließlich
	 * des neu angelegtem Objekts
	 */
	private class NewRetailerCallback implements AsyncCallback<Retailer> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

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
