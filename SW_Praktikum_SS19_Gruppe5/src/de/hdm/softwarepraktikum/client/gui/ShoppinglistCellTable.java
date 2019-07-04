package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
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
 * Diese Klasse dient zur Darstellung aller Einträge einer Einkaufsliste in
 * einem <code>CellTable</code> Widget.
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class ShoppinglistCellTable extends VerticalPanel {

	/**
	 * Interface um den CellTable mit der <code>CellTable</code> CSS Datei zu
	 * verknüpfen
	 * 
	 */
	static interface TableRes extends CellTable.Resources {

		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private ShoppinglistShowForm shoppinglistShowForm = null;

	private Shoppinglist selectedShoppinglist = null;
	private Listitem selectedListitem = null;
	private Group selectedGroup = null;
	private int id = 0;
	
	private Button archive = null;

	private ArrayList<Listitem> checkedListitems = new ArrayList<Listitem>();
	private ArrayList<ArrayList<Object>> data = new ArrayList<>();

	private final MultiSelectionModel<ArrayList<Object>> selectionModel = new MultiSelectionModel<ArrayList<Object>>();
	private CellTable<ArrayList<Object>> table;

	public ShoppinglistCellTable() {

		// CellTable custom UI resource
		CellTable.Resources tableRes = GWT.create(TableRes.class);
		table = new CellTable<ArrayList<Object>>(50, tableRes);

		// SelectionModel um die klicks der Checkboxen zu regeln
		table.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<ArrayList<Object>>createCheckboxManager());

		archive = new Button("Markierte Einträge archivieren");
		archive.addClickHandler(new ArchiveClickHandler());

		archive.setStyleName("NavButton");

		/**
		 * Spalte zur Darstellung einer Checkbox
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

		/**
		 * Spalte zur Darstellung des Namen eines <code>Product</code>-Objekts
		 * 
		 */
		Column<ArrayList<Object>, String> productNameToDisplay = new Column<ArrayList<Object>, String>(new TextCell()) {

			@Override
			public String getValue(ArrayList<Object> object) {

				return object.get(1).toString();

			}

		};

		/**
		 * Spalte zur Darstellung der zu einkaufenden Menge
		 * 
		 */
		Column<ArrayList<Object>, String> amountToDisplay = new Column<ArrayList<Object>, String>(new TextCell()) {
			@Override
			public String getValue(ArrayList<Object> object) {

				return object.get(4).toString();

			}
		};

		/**
		 * Spalte zur Darstellung der Name der verwendeten Einheit
		 * 
		 */
		Column<ArrayList<Object>, String> unitNameToDisplay = new Column<ArrayList<Object>, String>(new TextCell()) {
			@Override
			public String getValue(ArrayList<Object> object) {

				return object.get(3).toString();

			}
		};

		/**
		 * Spalte zur Darstellung der Händlernamen
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
		 * Spalte, die ein klickbares Bild enthält, das die Klasse zur Bearbeitung des
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
					selectedListitem = (Listitem) object.get(0);

					ListitemShowForm lsf = new ListitemShowForm();
					lsf.setSelected(selectedListitem);
					lsf.setSelectedShoppinglist(selectedShoppinglist);
					lsf.setSelectedGroup(selectedGroup);

					RootPanel.get("main").add(lsf);
				}
			}
		};

		/**
		 * Spalte, die ein klickbares Bild enthält, das die Eigenschaft isStandard bei
		 * einem <code>Listitem</code>-Objekt setzt und enfernt.
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

				selectedListitem = (Listitem) object.get(0);
				if (selectedListitem.isStandard() == true) {
					return "like (1).png";
				} else {
					return "like.png";
				}

			}

			public void onBrowserEvent(Context context, Element elem, ArrayList<Object> object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {

					selectedListitem = (Listitem) object.get(0);

					if (selectedListitem.isStandard() == true) {
						shoppinglistAdministration.setStandardListitem(selectedListitem, selectedGroup, false,
								new UnselectStandardCallback());
					} else if (selectedListitem.isStandard() != true) {
						shoppinglistAdministration.setStandardListitem(selectedListitem, selectedGroup, true,
								new SetStandardCallback());
					}
				}
			}

		};

		/**
		 * Spalte, die ein Bild enthält, dass das zuletzt geänderte
		 * <code>Listitem</code>-Objekt deutlich macht.
		 * 
		 */
		Column<ArrayList<Object>, String> latestChangeColumn = new Column<ArrayList<Object>, String>(
				new ClickableTextCell() {
					public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
						sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
					}

				})

		{

			@Override
			public String getValue(ArrayList<Object> object) {
				selectedListitem = (Listitem) object.get(0);

				if (selectedListitem.getId() == id) {
					return "new.png";
					
				} else {
				return "transparent.png";
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
		table.addColumn(latestChangeColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));

	}

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt und der CellTable
	 * mit Daten befüllt.
	 * 
	 */
	public void onLoad() {
		/**
		 * Holen der Daten ohne Filter
		 */
		selectedShoppinglist = shoppinglistShowForm.getSelectedShoppinglist();
		id = shoppinglistShowForm.getSelectedShoppinglist().getLastestEdit();

		shoppinglistAdministration.getListitemData(shoppinglistShowForm.getSelectedShoppinglist(),
				new GetListitemDataCallback());

		this.add(table);
		this.add(archive);

	}

	public ShoppinglistShowForm getShoppinglistShowForm() {
		return shoppinglistShowForm;
	}

	public void setShoppinglistShowForm(ShoppinglistShowForm shoppinglistShowForm) {
		this.shoppinglistShowForm = shoppinglistShowForm;
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

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	/**
	 * Sobald eine <code>Shoppinglist</code> ausgewaehlt wird das Label mit den
	 * entsprechenden Informationen befüllt.
	 * 
	 * @param s, das zu setzende <code>Shoppinglist</code> Objekt.
	 */
	public void setSelectedShoppinglist(Shoppinglist s) {
		if (s != null) {
			selectedShoppinglist = s;

		} else {
			this.clear();
		}
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Archivieren der ausgewählten <code>Listitem</code>-Objekten.
	 */
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
				Notification.show("Keine Einträge ausgewählt.");
			} else {

				shoppinglistAdministration.archiveListitems(checkedListitems, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Notification.show(caught.toString());

					}

					@Override
					public void onSuccess(Void result) {

						RootPanel.get("main").clear();

						ShoppinglistShowForm ssf = new ShoppinglistShowForm();
						ssf.setSelected(selectedShoppinglist);
						ssf.setSelectedGroup(selectedGroup);

						checkedListitems.clear();
						selectionModel.clear();

						RootPanel.get("main").add(ssf);

					}

				});
			}

		}

	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Das selektierte <code>Listitem</code>-Objekt wird nicht Standard gesetzt und
	 * die <code>ShoppinglistShowForm</code> erneut aufgerufen.
	 */
	private class UnselectStandardCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("main").clear();

			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(selectedShoppinglist);
			ssf.setSelectedGroup(selectedGroup);

			RootPanel.get("main").add(ssf);

		}

	}

	/**
	 * Das selektierte <code>Listitem</code>-Objekt wird Standard gesetzt und die
	 * <code>ShoppinglistShowForm</code> erneut aufgerufen.
	 */
	private class SetStandardCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("main").clear();

			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(selectedShoppinglist);
			ssf.setSelectedGroup(selectedGroup);

			RootPanel.get("main").add(ssf);

		}

	}

	private class GetListitemDataCallback implements AsyncCallback<Map<Listitem, ArrayList<String>>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

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

				// Setzen des aktuelle Row Counts
				table.setRowCount(result.size(), true);
				// Widget mit der ArrayList befüllen
				table.setRowData(0, data);

			}
		}
	};

}