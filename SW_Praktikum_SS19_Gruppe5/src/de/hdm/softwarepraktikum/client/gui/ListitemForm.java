package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Formular zur Darstellung des zu ändernden Listitem
 */

public class ListitemForm extends VerticalPanel {
	ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	Listitem listitemToDisplay = null;

	ShoppinglistCellTable shoppinglistCellTable;
	GroupShoppinglistTreeViewModel gstvm = null;

	NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	TextBox amountTextBox = new TextBox();
	TextBox unitNameTextBox = new TextBox();
	TextBox retailerNameTextBox = new TextBox();

	Button newButton = new Button("Neuen Eintrag hinzufügen");
	Button saveButton = new Button("Speichern");
	Button discardButton = new Button("verwerfen und zurueck");

	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
	 * angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets
	 * bestimmt.
	 */

	public void onLoad() {
		super.onLoad();

		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		Grid shoppinglistGrid = new Grid(5, 3);
		this.add(shoppinglistGrid);

		shoppinglistGrid.setWidget(0, 1, newButton);
		newButton.addClickHandler(new NewListitemClickHandler());
		newButton.setEnabled(true);
		
		Label amountLabel = new Label("Menge");
		shoppinglistGrid.setWidget(1, 0, amountLabel);
		shoppinglistGrid.setWidget(1, 1, amountTextBox);

		Label unitNameLabel = new Label("Einheit");
		shoppinglistGrid.setWidget(2, 0, unitNameLabel);
		shoppinglistGrid.setWidget(2, 1, unitNameTextBox);

		Label retailerNameLabel = new Label("Händler");
		shoppinglistGrid.setWidget(3, 0, retailerNameLabel);
		shoppinglistGrid.setWidget(3, 1, retailerNameTextBox);

		HorizontalPanel actionButtonsPanel = new HorizontalPanel();
		shoppinglistGrid.setWidget(4, 1, actionButtonsPanel);

		saveButton.addClickHandler(new SaveClickHandler());
		saveButton.setEnabled(true);
		actionButtonsPanel.add(saveButton);

		discardButton.addClickHandler(new DiscardClickhandler());
		discardButton.setEnabled(true);
		actionButtonsPanel.add(discardButton);

	}

	/**
	 * Clickhandler zum verwerfen der Eingaben und zur Rückkehr zum Shoppinglist CellTable
	 * 
	 */
	private class DiscardClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
		listitemToDisplay = null;
			//tbd: Ansicht schließen und zurück zu Shoppinglist CellTable
		}
		
	}
	
	
	
	/**
	 * Clickhandler zum ändern der Listitem Eigenschaften. Es erfolgt der Aufruf der
	 * Service-Methode "save".
	 * 
	 */
	private class SaveClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (listitemToDisplay != null) {
				listitemToDisplay.setAmount(amountTextBox.getText());
				listitemToDisplay.setListitemUnit(unitNameTextBox.getText());
				listitemToDisplay.setRetailer(retailerNameTextBox.getText());

				shoppinglistAdministration.save(listitemToDisplay, new SaveCallback());
			} else {
				Window.alert("kein Kunde ausgewählt");
			}
		}
	}

	private class SaveCallback implements AsyncCallback<Void> {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Aenderung ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {
			// Die Änderung wird dem Listitem weitergegeben
			gstvm.updateListitem(listitemToDisplay);
		}
	}

	
	
	/**
	 * Clickhandler zum ertsellen eines Listitem Objekts
	 * 
	 */
	private class NewListitemClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Float amount = amountTextBox.getText();			
			String unitName = unitNameTextBox.getText();
			String retailerName = retailerNameTextBox.getText();
			shoppinglistAdministration.createListitem(amount, unitName, retailerName ,new CreateListitemCallback());
		}
	}

	class CreateListitemCallback implements AsyncCallback<Listitem> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Das Anlegen eines neuen Eintrags ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Listitem result) {
			if (result != null) {
				// Das erfolgreiche Hinzufügen eines Eintrags wird an den DOM-Tree propagiert.
				gstvm.addListitem(result);
			}
		}
	}

	// catvm setter
	void setCatvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

//	private class SaveClickHandler implements ClickHandler {
//		@Override
//		public void onClick(ClickEvent event) {
//			float amount = 0.0F;
//			try {
//				amount = (float) decimalFormatter
//						.parse(amountTextBox.getText());
//			} catch (NumberFormatException nfe) {
//				Window.alert("ungültiger Wert!");
//				return;
//			}
//
//			if (listitemToDisplay == null) {
//				Window.alert("kein Konto ausgewählt!");
//				return;
//			}
//			shoppinglistAdministration
//			shoppinglistAdministration.createListitem(null, null, amount, null, new CreateListitemCallback());
//			
//		}
//	}
//
//	private class CreateListitemCallback implements AsyncCallback<Listitem> {
//		@Override
//		public void onFailure(Throwable caught) {
//		}
//
//		@Override
//		public void onSuccess(Listitem result) {
//			if (result != null) {
//				// Von der Transaktion erhalten wird die Kontonummer des Kontos,
//				// das auf der Anzeige aktualisiert werden soll.
//				
//				bankVerwaltung.getAccountById(trans.getTargetAccountID(),
//						new updateAccountByIdCallback());
//			}
//		}
//
//		@Override
//		public void onSuccess(Listitem result) {
//			if (result != null) {
//				shoppinglistAdministration.getShoppinglistById(result.getShoppinglistID(), new updateShoppinglistByIdCallback());
//			}
//			
//		}
//	}
//	
//
//	private class updateShoppinglistByIdCallback implements AsyncCallback<Shoppinglist> {
//		@Override
//		public void onFailure(Throwable caught) {
//		}
//
//		@Override
//		public void onSuccess(Shoppinglist s) {
//			if (s != null) {
//				setSelected(s);
//				// Das Konto wird im Kunden- und Kontobaum ebenfalls
//				// aktualisiert.
//				gstvm.updateShoppinglist(s);
//			}
//			
//		}
//	}
//	

	/**
	 * Die Änderung eines Kunden bezieht sich auf seinen Vor- und/oder Nachnamen. Es
	 * erfolgt der Aufruf der Service-Methode "save".
	 * 
	 */

	void setSelected(Listitem l) {
		if (l != null) {
			listitemToDisplay = l;
			discardButton.setEnabled(true);

			shoppinglistAdministration.getAmountOf(l, new GetAmountCallback());
			shoppinglistAdministration.getListitemUnitOf(l, new GetListitemUnitCallback());
			shoppinglistAdministration.getRetailerOf(l, new GetRetailerCallback());

		} else {
			listitemToDisplay = null;
			discardButton.setEnabled(false);

			this.amountTextBox.setText("");
			this.unitNameTextBox.setText("");
			this.retailerNameTextBox.setText("");

		}
	}

	private class GetAmountCallback implements AsyncCallback<Float> {
		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Float result) {
			if (result != null) {
				amountTextBox.setText(decimalFormatter.format(result));
			}
		}
	}

	private class GetListitemUnitCallback implements AsyncCallback<ListitemUnit> {
		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(ListitemUnit result) {
			if (result != null) {
				unitNameTextBox.setText(result.getName());
			}
		}
	}

	private class GetRetailerCallback implements AsyncCallback<Retailer> {
		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Retailer result) {
			if (result != null) {
				retailerNameTextBox.setText(result.getName());
			}
		}
	}
}
