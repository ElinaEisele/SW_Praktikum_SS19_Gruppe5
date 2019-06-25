package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Diese Klasse dient zur Darstellung aller Einkaufslisten einer Gruppe in einem
 * <code>CellTable</code> Widget.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class GroupCellTable extends VerticalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();

	private GroupShoppinglistTreeViewModel gstvm = null;
	private GroupShowForm groupShowForm = null;
	private Group selectedGroup = null;

	private CellTable<Shoppinglist> table = new CellTable<Shoppinglist>();

	public GroupCellTable() {

		/**
		 * Spalte zur Darstellung des Namen einer <code>Shoppinglist</code>
		 * 
		 */
		Column<Shoppinglist, String> shoppinglistNameToDisplay = new Column<Shoppinglist, String>(new TextCell()) {
			@Override
			public String getValue(Shoppinglist object) {

				return object.getName();

			}
		};

		/**
		 * Spalte, die ein klickbares Bild enthält, das die Einkaufsliste bei Klick in
		 * einer neuen <code>ShoppinglistShowForm</code> darstellt.
		 * 
		 */
		Column<Shoppinglist, String> imageColumn = new Column<Shoppinglist, String>(new ClickableTextCell() {
			public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
			}

		})

		{
			@Override
			public String getValue(Shoppinglist object) {
				return "edit.png";
			}

			public void onBrowserEvent(Context context, Element elem, Shoppinglist object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {

					gstvm.setSelectedShoppinglist(object);
					gstvm.getSelectionModel().setSelected(object, true);

				}
			}
		};

		table.addColumn(shoppinglistNameToDisplay, "Einkaufsliste");
		table.addColumn(imageColumn, "Edit");

	}

	public void onLoad() {

		/**
		 * AsyncCallback der eine ArrayList mit <code>Shoppinglist</code>-Objekten der
		 * entsprechenden Gruppe zurückgeben soll.
		 * 
		 */
		shoppinglistAdministration.getShoppinglistsOf(groupShowForm.getSelected(), new GetShoppinglistsOfCallback());

		this.add(table);

	}

	public GroupShowForm getGroupShowForm() {
		return groupShowForm;
	}

	public void setGroupShowForm(GroupShowForm groupShowForm) {
		this.groupShowForm = groupShowForm;
	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}

	/**
	 * Sobald eine <code>Group</code> ausgewaehlt wird, wird das Label mit dem
	 * Gruppenname befuellt.
	 * 
	 * @param g das zu setzende <code>Group</code> Objekt.
	 */
	public void setSelected(Group g) {
		if (g != null) {
			selectedGroup = g;

		} else {
			this.clear();
		}
	}

	public Group getSelected() {
		return selectedGroup;
	}

	/**
	 * ***************************************************************************
	 * Abschnitt der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * AsyncCallback der eine ArrayList mit <code>Shoppinglist</code>-Objekten der
	 * entsprechenden Gruppe zurückgeben soll.
	 * 
	 */
	private class GetShoppinglistsOfCallback implements AsyncCallback<ArrayList<Shoppinglist>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(ArrayList<Shoppinglist> result) {

			/**
			 * Daten dem <code>CellTable</code> Widget hinzufuegen.
			 * 
			 */
			table.setRowCount(result.size(), true);
			table.setRowData(0, result);

		}

	}
}
