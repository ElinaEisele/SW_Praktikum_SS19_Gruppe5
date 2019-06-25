package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistCellTable.TableRes;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistCellTable.TableRes.TableStyle;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Diese Klasse dient zur Darstellung aller Einträge einer Einkaufsliste in
 * einem <code>CellTable</code> Widget.
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class FilteredShoppinglistCellTable extends VerticalPanel {

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
	private ShoppinglistHeader shoppinglistHeader = null;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Shoppinglist selectedShoppinglist = null;
	private Listitem selectedListitem = null;
	private Group selectedGroup = null;
	private Retailer selectedRetailer = null;
	private User selectedUser = null;

	private ArrayList<Listitem> checkedListitems = new ArrayList<Listitem>();
	private ArrayList<ArrayList<Object>> data = new ArrayList<>();
	private Map<Listitem, ArrayList<String>> listitemData = null;

	private Label contentLabel = new Label();
	private Button backButton = null;
	private Button archiveButton = null;

	private final MultiSelectionModel<ArrayList<Object>> selectionModel = new MultiSelectionModel<ArrayList<Object>>();
	private CellTable<ArrayList<Object>> table;

	public FilteredShoppinglistCellTable() {

		// CellTable custom UI resource
		CellTable.Resources tableRes = GWT.create(TableRes.class);
		table = new CellTable<ArrayList<Object>>(50, tableRes);

		// SelectionModel um die klicks der Checkboxen zu regeln
		table.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<ArrayList<Object>>createCheckboxManager());

		backButton = new Button();
		Image backButtonImg = new Image();
		backButtonImg.setUrl("images/left-arrow.png");
		backButtonImg.setSize("16px", "16px");
		backButton.getElement().appendChild(backButtonImg.getElement());
		backButton.setStyleName("ShoppinglistHeaderButton");
		backButton.addClickHandler(new BackClickHandler());
		archiveButton = new Button("Markierte Eintraege archivieren");
		archiveButton.addClickHandler(new ArchiveClickHandler());

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(archiveButton);
		buttonPanel.add(backButton);

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

					if (listitemData == null) {

						if (selectedShoppinglist != null && selectedUser != null) {
							ListitemShowForm lsf = new ListitemShowForm();
							lsf.setSelected(selectedListitem);
							lsf.setSelectedShoppinglist(selectedShoppinglist);
							lsf.setSelectedGroup(selectedGroup);
							lsf.setSelectedUser(selectedUser);

							RootPanel.get("main").add(lsf);

						} else if (selectedShoppinglist != null && selectedRetailer != null) {
							ListitemShowForm lsf = new ListitemShowForm();
							lsf.setSelected(selectedListitem);
							lsf.setSelectedShoppinglist(selectedShoppinglist);
							lsf.setSelectedGroup(selectedGroup);
							lsf.setSelectedRetailer(selectedRetailer);

							RootPanel.get("main").add(lsf);

						}

					} else {
						Notification.show("Hoppla, hier ist etwas schief gelaufen. Bitte später erneut versuchen");

					}
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
		 * Add Columns to CellTable
		 * 
		 */
		table.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		table.addColumn(productNameToDisplay, "Produkt");
		table.addColumn(amountToDisplay, "Menge");
		table.addColumn(unitNameToDisplay, "Einheit");
		table.addColumn(retailerNameToDisplay, "Händler");
		table.addColumn(imageColumn, "Edit");
		table.addColumn(standardColumn, "Standard");

		mainPanel.add(contentLabel);
		mainPanel.add(table);
		mainPanel.add(buttonPanel);

	}

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt und der CellTable
	 * mit Daten befüllt.
	 * 
	 */
	public void onLoad() {

		if (listitemData == null) {

			/**
			 * Holen der Daten bei Filtern nach User
			 */
			if (selectedListitem != null && selectedUser != null) {
				contentLabel.setText("Nach Nutzer Filtern");
				shoppinglistAdministration.filterShoppinglistsByUser(selectedShoppinglist, selectedUser,
						new FilterShoppinglistByUserCallback());

				/**
				 * Holen der Daten bei Filtern nach Retailer
				 */
			} else if (selectedShoppinglist != null && selectedRetailer != null) {
				contentLabel.setText("Nach Händler Filtern");

				shoppinglistAdministration.filterShoppinglistsByRetailer(selectedShoppinglist, selectedRetailer,
						new FilterShoppinglistsByRetailerCallback());
			}

			this.add(mainPanel);

		} else {
			/**
			 * Holen der Daten ohne Filter
			 */
			data.clear();
			if (data.size() == 0) {
				for (Listitem key : listitemData.keySet()) {
					ArrayList<Object> listitems = new ArrayList<>();

					listitems.add(key);
					listitems.add(listitemData.get(key).get(0));
					listitems.add(listitemData.get(key).get(1));
					listitems.add(listitemData.get(key).get(2));
					listitems.add(listitemData.get(key).get(3));

					data.add(listitems);

				}

				// Setzen des aktuelle Row Counts
				table.setRowCount(listitemData.size(), true);
				// Widget mit der ArrayList befüllen
				table.setRowData(0, data);

			}
		}
		this.add(mainPanel);

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

	public ShoppinglistHeader getShoppinglistHeader() {
		return shoppinglistHeader;
	}

	public void setShoppinglistHeader(ShoppinglistHeader shoppinglistHeader) {
		this.shoppinglistHeader = shoppinglistHeader;
	}

	public Retailer getSelectedRetailer() {
		return selectedRetailer;
	}

	public void setSelectedRetailer(Retailer selectedRetailer) {
		this.selectedRetailer = selectedRetailer;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * Sobald eine <code>Shoppinglist</code> ausgewählt wird, wird das Label mit den
	 * entsprechenden Informationen bef�llt.
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

	public void setListitemData(Map<Listitem, ArrayList<String>> listitemData) {
		this.listitemData = listitemData;
		for (Listitem key : listitemData.keySet()) {
			ArrayList<Object> listitems = new ArrayList<>();

			listitems.add(key);
			listitems.add(listitemData.get(key).get(0));
			listitems.add(listitemData.get(key).get(1));
			listitems.add(listitemData.get(key).get(2));
			listitems.add(listitemData.get(key).get(3));

			data.add(listitems);
		}

		// Setzen des aktuelle Row Counts
		table.setRowCount(listitemData.size(), true);
		// Widget mit der ArrayList befüllen
		table.setRowData(0, data);
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Das Formular wird geschlossen und die aktuell ausgewählte Einkaufsliste
	 * erneut geöffnet.
	 * 
	 */
	private class BackClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			ShoppinglistShowForm ssf = new ShoppinglistShowForm();
			ssf.setSelected(selectedShoppinglist);
			ssf.setSelectedGroup(selectedGroup);
			RootPanel.get("main").add(ssf);

		}

	}

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
						ssf.setSelectedRetailer(selectedRetailer);

						ssf.setFilteredshoppinglistCellTable(FilteredShoppinglistCellTable.this);

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

			if (listitemData == null) {

				if (selectedShoppinglist != null && selectedUser != null) {

					ShoppinglistShowForm ssf = new ShoppinglistShowForm();
					ssf.setSelected(selectedShoppinglist);
					ssf.setSelectedGroup(selectedGroup);
					ssf.setSelectedUser(selectedUser);
					ssf.setFilteredshoppinglistCellTable(FilteredShoppinglistCellTable.this);

					RootPanel.get("main").add(ssf);

				} else if (selectedShoppinglist != null && selectedRetailer != null) {

					ShoppinglistShowForm ssf = new ShoppinglistShowForm();
					ssf.setSelected(selectedShoppinglist);
					ssf.setSelectedGroup(selectedGroup);
					ssf.setSelectedRetailer(selectedRetailer);
					ssf.setFilteredshoppinglistCellTable(FilteredShoppinglistCellTable.this);

					RootPanel.get("main").add(ssf);

				}

			} else {
				Notification.show("Hoppla, hier ist etwas schief gelaufen. Bitte spaeter erneut versuchen");

			}
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

			if (listitemData == null) {

				if (selectedShoppinglist != null && selectedUser != null) {

					ShoppinglistShowForm ssf = new ShoppinglistShowForm();
					ssf.setSelected(selectedShoppinglist);
					ssf.setSelectedGroup(selectedGroup);
					ssf.setSelectedUser(selectedUser);
					ssf.setFilteredshoppinglistCellTable(FilteredShoppinglistCellTable.this);

					RootPanel.get("main").add(ssf);

				} else if (selectedShoppinglist != null && selectedRetailer != null) {

					RootPanel.get("main").clear();

					ShoppinglistShowForm ssf = new ShoppinglistShowForm();
					ssf.setSelected(selectedShoppinglist);
					ssf.setSelectedGroup(selectedGroup);
					ssf.setSelectedRetailer(selectedRetailer);
					ssf.setFilteredshoppinglistCellTable(FilteredShoppinglistCellTable.this);

					RootPanel.get("main").add(ssf);

				}

			} else {
				Notification.show("Hoppla, hier ist etwas schief gelaufen. Bitte spaeter erneut versuchen");
			}

		}

	}

	/**
	 * Der CellTable wird mit den gefilterten Daten befüllt
	 */
	private class FilterShoppinglistsByRetailerCallback implements AsyncCallback<Map<Listitem, ArrayList<String>>> {

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

	/**
	 * Der CellTable wird mit den gefilterten Daten befüllt
	 */
	private class FilterShoppinglistByUserCallback implements AsyncCallback<Map<Listitem, ArrayList<String>>> {

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

				Collections.sort(data, new Comparator<List<Object>>() {

					@Override
					public int compare(List<Object> o1, List<Object> o2) {
						return ((String) o1.get(1)).compareTo((String) o2.get(1));
					}
				});

				// Setzen des aktuelle Row Counts
				table.setRowCount(result.size(), true);
				// Widget mit der ArrayList befüllen
				table.setRowData(0, data);
			}
		}

	};

}