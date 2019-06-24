package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
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
	private Listitem listitemToDisplay = null;
	private Group selectedGroup = null;
	private CellTable<ArrayList<Object>> table = new CellTable<ArrayList<Object>>();

	private ArrayList<Listitem> checkedListitems = new ArrayList<Listitem>();
	private ArrayList<ArrayList<Object>> data = new ArrayList<>();

	private Button archive;
	private final MultiSelectionModel<ArrayList<Object>> selectionModel = new MultiSelectionModel<ArrayList<Object>>();

	public ShoppinglistCellTable() {

		// Add a selection model so we can select cells.
		table.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<ArrayList<Object>>createCheckboxManager());

		archive = new Button("Markierte Eintraege archivieren");
		archive.addClickHandler(new ArchiveClickHandler());

		/**
		 * Spalte zur Darstellung einer Checkbox.
		 * 
		 */
		Column<ArrayList<Object>, Boolean> checkColumn = new Column<ArrayList<Object>, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(ArrayList<Object> object) {

				return selectionModel.isSelected(object);

			}
		};

		checkColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		checkColumn.setCellStyleNames("columncheck");
		// table.setColumnWidth(checkColumn, 40, Unit.PX);

		/**
		 * Spalte zur Darstellung des Namen eines <code>Product</code>
		 * 
		 */
		Column<ArrayList<Object>, String> productNameToDisplay = new Column<ArrayList<Object>, String>(new TextCell()) {

			@Override
			public String getValue(ArrayList<Object> object) {

				return object.get(1).toString();

			}

		};

		/**
		 * Column containing the amount of a product
		 * 
		 */
		Column<ArrayList<Object>, String> amountToDisplay = new Column<ArrayList<Object>, String>(new TextCell()) {
			@Override
			public String getValue(ArrayList<Object> object) {

				return object.get(4).toString();

			}
		};

		/**
		 * Column containing the unit name of a listitem
		 * 
		 */
		Column<ArrayList<Object>, String> unitNameToDisplay = new Column<ArrayList<Object>, String>(new TextCell()) {
			@Override
			public String getValue(ArrayList<Object> object) {

				return object.get(3).toString();

			}
		};

		/**
		 * Column containing the retailer name of a listitem
		 * 
		 */
		Column<ArrayList<Object>, String> retailerNameToDisplay = new Column<ArrayList<Object>, String>(
				new TextCell()) {
			@Override
			public String getValue(ArrayList<Object> object) {

				return object.get(2).toString();

			}
		};

		/**
		 * Spalte, die ein klickbares Bild enth�lt, das die Klasse zur Bearbeitung des
		 * Eintrags bei Klick in einer neuen <code>ListitemShowForm</code> darstellt.
		 * 
		 */
		Column<ArrayList<Object>, String> imageColumn = new Column<ArrayList<Object>, String>(new ClickableTextCell() {
			public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
			}

		})

		{

			@Override
			public String getValue(ArrayList<Object> object) {
				return "edit.png";
			}

			public void onBrowserEvent(Context context, Element elem, ArrayList<Object> object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);

				if ("click".equals(event.getType())) {

					RootPanel.get("main").clear();
					listitemToDisplay = (Listitem) object.get(0);

					ListitemShowForm lsf = new ListitemShowForm();
					lsf.setSelected(listitemToDisplay);
					lsf.setSelectedShoppinglist(shoppinglistToDisplay);
					lsf.setSelectedGroup(selectedGroup);

					RootPanel.get("main").add(lsf);
				}
			}
		};

		/**
		 * Spalte, die ein klickbares Bild enth�lt, das die Klasse zur Bearbeitung des
		 * Eintrags bei Klick in einer neuen <code>ListitemShowForm</code> darstellt.
		 * 
		 */
		Column<ArrayList<Object>, String> standardColumn = new Column<ArrayList<Object>, String>(
				new ClickableTextCell() {
					public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
						sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
					}

				})

		{

			@Override
			public String getValue(ArrayList<Object> object) {

				listitemToDisplay = (Listitem) object.get(0);
				if (listitemToDisplay.isStandard() == true) {
					return "like (1).png";
				} else {
					return "like.png";
				}

			}

			public void onBrowserEvent(Context context, Element elem, ArrayList<Object> object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {

					listitemToDisplay = (Listitem) object.get(0);

					if (listitemToDisplay.isStandard() == true) {
						shoppinglistAdministration.setStandardListitem(listitemToDisplay, selectedGroup, false,
								new UnselectStandardCallback());
					} else if (listitemToDisplay.isStandard() != true) {
						shoppinglistAdministration.setStandardListitem(listitemToDisplay, selectedGroup, true,
								new SetStandardCallback());
					}
				}
			}

		};

		/**
		 * Add Columns to CellTable
		 * 
		 */
		table.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		table.addColumn(productNameToDisplay, "Produkt");
		table.addColumn(amountToDisplay, "Menge");
		table.addColumn(unitNameToDisplay, "Einheit");
		table.addColumn(retailerNameToDisplay, "Haendler");
		table.addColumn(imageColumn, "Edit");
		table.addColumn(standardColumn, "Standard");

	}

	public void onLoad() {

		/**
		 * Get all Listitems of the Shoppinglist to display and get their data in the on
		 * success method
		 * 
		 */

		shoppinglistAdministration.getListitemData(shoppinglistShowForm.getSelectedShoppinglist(),
				new AsyncCallback<Map<Listitem, ArrayList<String>>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<Listitem, ArrayList<String>> result) {

						data.clear();
						if (data.size() == 0) {

							for (Listitem key : result.keySet()) {
								ArrayList<Object> listitems = new ArrayList<>();

								listitems.add(key);
								listitems.add(result.get(key).get(0));
								listitems.add(result.get(key).get(1));
								listitems.add(result.get(key).get(2));
								listitems.add(result.get(key).get(3));

								data.add(listitems);
							}

							// Set the total row count
							table.setRowCount(result.size(), true);
							// Push the data into the widget.
							table.setRowData(0, data);

						}
					}
				});

		this.add(table);
		this.add(archive);

	}

	public ShoppinglistShowForm getShoppinglistShowForm() {
		return shoppinglistShowForm;
	}

	public void setShoppinglistShowForm(ShoppinglistShowForm shoppinglistShowForm) {
		this.shoppinglistShowForm = shoppinglistShowForm;
	}

	public Listitem getListitemToDisplay() {
		return listitemToDisplay;
	}

	public void setListitemToDisplay(Listitem listitemToDisplay) {
		this.listitemToDisplay = listitemToDisplay;
	}

	public Shoppinglist getShoppinglistToDisplay() {
		return shoppinglistToDisplay;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
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

	private class UnselectStandardCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("main").clear();

			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(shoppinglistToDisplay);
			ssf.setSelectedGroup(selectedGroup);

			RootPanel.get("main").add(ssf);

		}

	}

	private class SetStandardCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("main").clear();

			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(shoppinglistToDisplay);
			ssf.setSelectedGroup(selectedGroup);

			RootPanel.get("main").add(ssf);

		}

	}

	private class ArchiveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Set<ArrayList<Object>> s = selectionModel.getSelectedSet();

			ArrayList<ArrayList<Object>> nm = new ArrayList<ArrayList<Object>>(s);
		
			for (int i = 0; i < nm.size(); i++) {
				Listitem l = new Listitem();
				l = (Listitem) nm.get(i).get(0);
				checkedListitems.add(l);
			}

			if (checkedListitems.isEmpty() == true) {
				Window.alert("Keine Eintraege ausgewaehlt");
			} else {

				shoppinglistAdministration.archiveListitems(checkedListitems, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {

						RootPanel.get("main").clear();

						ShoppinglistShowForm ssf = new ShoppinglistShowForm();
						ssf.setSelected(shoppinglistToDisplay);
						ssf.setSelectedGroup(selectedGroup);
//						ssf.setGstvm(gstvm);
//						gstvm.setSelectedGroup(null);
//						gstvm.setSelectedShoppinglist(object);

						checkedListitems.clear();
						selectionModel.clear();

						RootPanel.get("main").add(ssf);

					}

				});
			}

		}

	}

}