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
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Diese Klasse dient zur Darstellung aller Listen einer Gruppe.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class GroupCellTable extends HorizontalPanel {

	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
			.getShoppinglistAdministration();
	
	private ShoppinglistShowForm shoppinglistShowForm;
	private ListDataProvider<Shoppinglist> listDataProvider;
	private GroupShowForm groupShowForm = null;
	private Shoppinglist shoppinglistToDisplay = null;
	private Group groupToDisplay = null;
	private Label label = new Label("Huiuiui");

	VerticalPanel vPanel = new VerticalPanel();
	
private ArrayList <Shoppinglist>shoppinglists = new ArrayList<>();
	private CellTable<Shoppinglist> table = new CellTable<Shoppinglist>();

	public GroupCellTable() {		

		table.setStyleName("shoppinglist-CellTable");
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		listDataProvider = (ListDataProvider<Shoppinglist>) table.getKeyProvider();
		
		/**
		 * Column containing the product name of a listitem
		 * 
		 */
		Column<Shoppinglist, String> shoppinglistNameToDisplay = new Column<Shoppinglist, String>(new TextCell()) {
			@Override
			public String getValue(Shoppinglist object) {

				return object.getName();

			}
		};

		
		
		/**
		 * Clickable edit button containing an image
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

					Window.alert("clicked");
				}
			}
		};
		
		table.addColumn(shoppinglistNameToDisplay, "Einkaufsliste");
		table.addColumn(imageColumn, "Edit");
		
	}

	public void onLoad() {	
		this.clear();
		vPanel.clear();
		shoppinglistAdministration.getShoppinglistsOf(groupToDisplay, new AsyncCallback<ArrayList<Shoppinglist>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				Notification.show("Das Laden der Einkaufslisten ist fehlgeschlagen");

			}

			@Override
			public void onSuccess(ArrayList<Shoppinglist> result) {					
							

//				// Set the total row count
				table.setRowCount(result.size(), true);
//				// Push the data into the widget.
				table.setRowData(0, result);
//				Window.alert("uff"+ result.get(0).getName());
//				
//				Window.alert(groupToDisplay.getName());	
				
				vPanel.add(label);
				vPanel.add(table);
				

			}
			
		});
		label.setText(groupToDisplay.getName());
//		this.add(label);
//		this.add(table);
		this.add(vPanel);
	}

	
	
	
	public GroupShowForm getGroupShowForm() {
		return groupShowForm;
	}

	public void setGroupShowForm(GroupShowForm groupShowForm) {
		this.groupShowForm = groupShowForm;
	}

	/**
	 * Sobald eine <code>Group</code> ausgewaehlt wird, wird das Label mit dem
	 * Gruppenname befuellt.
	 * 
	 * @param g das zu setzende <code>Group</code> Objekt.
	 */
	public void setSelected(Group g) {
		if (g != null) {
			groupToDisplay = g;

		} else {
			this.clear();
		}
	}

	public Group getSelected() {
		return groupToDisplay;
	}
}
