package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Diese Klasse dient zur Darstellung aller Eintraege einer Einkaufsliste in
 * einem <code>CellTable</code> Widget.
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class ShoppinglistCellTable extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	private GroupShoppinglistTreeViewModel gstvm;
	private ShoppinglistShowForm shoppinglistShowForm;

	private Shoppinglist shoppinglistToDisplay = null;
	private CellTable<Listitem> table = new CellTable<Listitem>();

	private Label productName = new Label("");
	private Label unitName = new Label("");
	private Label retailerName = new Label("");

	private Button archive;

	public ShoppinglistCellTable() {

		archive = new Button("Abhaken");
		archive.addClickHandler(new ArchiveClickHandler());

		/**
		 * Spalte zur Darstellung einer Checkbox.
		 * 
		 */
		Column<Listitem, Boolean> checkColumn = new Column<Listitem, Boolean>(new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Listitem object) {
				return null;

			}
		};
		checkColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		checkColumn.setCellStyleNames("columncheck");
		table.setColumnWidth(checkColumn, 40, Unit.PX);

		/**
		 * Spalte zur Darstellung des Namen eines <code>Product</code>
		 * 
		 */
		Column<Listitem, String> productNameToDisplay = new Column<Listitem, String>(new TextCell()) {
			@Override
			public String getValue(Listitem object) {

				shoppinglistAdministration.getProductnameOf(object, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(String result) {
						productName.setText(result);

					}

				});
				return productName.getText();
			}
		};

		/**
		 * Column containing the amount of a product
		 * 
		 */
		NumberCell amountCell = new NumberCell();
		Column<Listitem, Number> amountToDisplay = new Column<Listitem, Number>(amountCell) {
			@Override
			public Number getValue(Listitem object) {
				return object.getAmount();
			}
		};

		/**
		 * Column containing the unit name of a listitem
		 * 
		 */
		Column<Listitem, String> unitNameToDisplay = new Column<Listitem, String>(new TextCell()) {
			@Override
			public String getValue(Listitem object) {
				shoppinglistAdministration.getListitemUnitOf(object, new AsyncCallback<ListitemUnit>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ListitemUnit result) {
						unitName.setText(result.getName());

					}

				});
				return unitName.getText();

			}
		};

		/**
		 * Column containing the retailer name of a listitem
		 * 
		 */
		Column<Listitem, String> retailerNameToDisplay = new Column<Listitem, String>(new TextCell()) {
			@Override
			public String getValue(Listitem object) {
				shoppinglistAdministration.getRetailerOf(object, new AsyncCallback<Retailer>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Retailer result) {
						retailerName.setText(result.getName());

					}

				});
				return retailerName.getText();

			}
		};

		/**
		 * Spalte, die ein klickbares Bild enth�lt, das die Klasse zur Bearbeitung des
		 * Eintrags bei Klick in einer neuen <code>ListitemShowForm</code> darstellt.
		 * 
		 */
		Column<Listitem, String> imageColumn = new Column<Listitem, String>(new ClickableTextCell() {
			public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
			}

		})

		{
			@Override
			public String getValue(Listitem object) {
				return "edit.png";
			}

			public void onBrowserEvent(Context context, Element elem, Listitem object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {

					RootPanel.get("main").clear();

					ListitemShowForm lsf = new ListitemShowForm();
//					ssf.setGstvm(gstvm);
//					gstvm.setSelectedGroup(null);
//					gstvm.setSelectedShoppinglist(object);

					lsf.setSelected(object);

					RootPanel.get("main").add(lsf);
				}
			}
		};

		/**
		 * Add Columns to CellTable
		 * 
		 */
		table.addColumn(checkColumn, "Check");
		table.addColumn(productNameToDisplay, "Produkt");
		table.addColumn(amountToDisplay, "Menge");
		table.addColumn(unitNameToDisplay, "Einheit");
		table.addColumn(retailerNameToDisplay, "Haendler");
		table.addColumn(imageColumn, "Edit");

	}

	public void onLoad() {

		/**
		 * Get all Listitems of the Shoppinglist to display and get their data in the on
		 * success method
		 * 
		 */
		Window.alert("SCT: " + shoppinglistShowForm.getSelectedShoppinglist().getName());
		shoppinglistAdministration.getListitemsOf(shoppinglistShowForm.getSelectedShoppinglist(),
				new AsyncCallback<ArrayList<Listitem>>() {

					@Override
					public void onFailure(Throwable caught) {
						Notification.show("Das Laden der Eintraege ist fehlgeschlagen");

					}

					@Override
					public void onSuccess(ArrayList<Listitem> result) {
						if (result == null) {
							Window.alert("result == null --> Keine Eintraege");
						} else {
							Window.alert(String.valueOf(result.get(0).getAmount()));
							
							// Set the total row count
							table.setRowCount(result.size(), true);
							// Push the data into the widget.
							table.setRowData(0, result);
							
						}
					}
				});

		this.add(archive);
		this.add(table);

	}

	public ShoppinglistShowForm getShoppinglistShowForm() {
		return shoppinglistShowForm;
	}

	public void setShoppinglistShowForm(ShoppinglistShowForm shoppinglistShowForm) {
		this.shoppinglistShowForm = shoppinglistShowForm;
	}

	/**
	 * Sobald eine <code>Shoppinglist</code> ausgewaehlt wird das Label mit den
	 * entsprechenden Informationen bef�llt.
	 * 
	 * @param s, das zu setzende <code>Shoppinglist</code> Objekt.
	 */
	public void setShoppinglistToDisplay(Shoppinglist s) {
		if (s != null) {
			shoppinglistToDisplay = s;

		} else {
			this.clear();
		}
	}

	private class ArchiveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

		}

	}
}