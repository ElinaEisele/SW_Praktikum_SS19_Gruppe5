package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.NumberCell;
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
	private Listitem listitemToDisplay = null;
	private Group selectedGroup = null;
	private CellTable<Listitem> table = new CellTable<Listitem>();

	private String productName = null;
	private String unitName = null;
	private String retailerName = null;

	private ArrayList<Listitem> checkedListitems = new ArrayList<>();
	private ArrayList<Listitem> listitems = new ArrayList<>();

	private Button archive;
	private final MultiSelectionModel<Listitem> selectionModel = new MultiSelectionModel<Listitem>();

	public ShoppinglistCellTable() {

		// Add a selection model so we can select cells.
		table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Listitem>createCheckboxManager());

		archive = new Button("Markierte Eintraege archivieren");
		archive.addClickHandler(new ArchiveClickHandler());

		/**
		 * Spalte zur Darstellung einer Checkbox.
		 * 
		 */
		Column<Listitem, Boolean> checkColumn = new Column<Listitem, Boolean>(new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Listitem object) {

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
							productName = result;

						}

					});
					return productName;
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
						unitName = result.getName();

					}

				});
				return unitName;

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
						retailerName = result.getName();

					}

				});
				return retailerName;

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
			listitemToDisplay = object;

			ListitemShowForm lsf = new ListitemShowForm();
			lsf.setSelected(object);
			lsf.setSelectedShoppinglist(shoppinglistToDisplay);
			lsf.setSelectedGroup(selectedGroup);

			RootPanel.get("main").add(lsf);
		}
	}};

	/**
	 * Spalte, die ein klickbares Bild enth�lt, das die Klasse zur Bearbeitung des
	 * Eintrags bei Klick in einer neuen <code>ListitemShowForm</code> darstellt.
	 * 
	 */
	Column<Listitem, String> standardColumn=new Column<Listitem,String>(new ClickableTextCell(){public void render(Context context,SafeHtml value,SafeHtmlBuilder sb){sb.appendHtmlConstant("<img width=\"20\" src=\"images/"+value.asString()+"\">");}

	})

	{

	@Override
	public String getValue(Listitem object) {
Window.alert(""+object.isStandard());
		if (object.isStandard() == true) {
			return "like (1).png";
		} else {
			return "like.png";
		}

	}
	

	public void onBrowserEvent(Context context, Element elem, Listitem object, NativeEvent event) {
		super.onBrowserEvent(context, elem, object, event);
		if ("click".equals(event.getType())) {

			if (object.isStandard() == true) {
				shoppinglistAdministration.setStandardListitem(object, selectedGroup, false,
						new UnselectStandardCallback());
			} else if (object.isStandard() != true) {
				shoppinglistAdministration.setStandardListitem(object, selectedGroup, true, new SetStandardCallback());
			}
		}
	}

	};

	/**
	 * Add Columns to CellTable
	 * 
	 */
	table.addColumn(checkColumn,SafeHtmlUtils.fromSafeConstant("<br/>"));table.addColumn(productNameToDisplay,"Produkt");table.addColumn(amountToDisplay,"Menge");table.addColumn(unitNameToDisplay,"Einheit");table.addColumn(retailerNameToDisplay,"Haendler");table.addColumn(imageColumn,"Edit");table.addColumn(standardColumn,"Standard");

	}

	public void onLoad() {

		/**
		 * Get all Listitems of the Shoppinglist to display and get their data in the on
		 * success method
		 * 
		 */
		shoppinglistAdministration.getListitemsOf(shoppinglistShowForm.getSelectedShoppinglist(),
				new AsyncCallback<ArrayList<Listitem>>() {

					@Override
					public void onFailure(Throwable caught) {
						Notification.show("Das Laden der Eintraege ist fehlgeschlagen");

					}

					@Override
					public void onSuccess(ArrayList<Listitem> result) {
						
						// Set the total row count
						table.setRowCount(result.size(), true);
						// Push the data into the widget.
						table.setRowData(0, result);
//						result.clear();
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
			Window.alert("Nicht mehr standard");
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
			Window.alert("standard");
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

			Set<Listitem> s = selectionModel.getSelectedSet();

			int n = s.size();
			Listitem arr[] = new Listitem[n];

			int i = 0;
			for (Listitem x : s) {
				arr[i++] = x;
				checkedListitems.add(x);
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