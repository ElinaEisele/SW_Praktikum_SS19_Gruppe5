package de.hdm.softwarepraktikum.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.gui.NavigatorPanel;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;
import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;
import de.hdm.softwarepraktikum.shared.dummydata.UserDD;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistShowForm;
import de.hdm.softwarepraktikum.client.gui.Trailer;
import de.hdm.softwarepraktikum.client.gui.Editor;
import de.hdm.softwarepraktikum.client.gui.GroupShoppinglistTreeViewModel;
import de.hdm.softwarepraktikum.client.gui.GroupShowForm;
import de.hdm.softwarepraktikum.client.gui.Header;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistCellTable;

/**
 * Entry-Point Klasse des Projekts <b>SW_Praktikum_SS19_Gruppe5</b> für den
 * Editor.
 * 
 * @author ElinaEisele , JonasWagenknecht
 * 
 */
public class ShoppinglistEditorEntry implements EntryPoint {

	ShoppinglistAdministrationAsync shoppinglistAdministration = null;

	private Header header = null;
	private NavigatorPanel shoppinglistNavigator = null;
	
	private ShoppinglistShowForm shoppinglistShowForm = null;
	private GroupShowForm groupShowForm = null;
	
	private Trailer trailer = null;

	/**
	 * The model that defines the nodes in the tree.
	 */
	private static class CustomTreeModel implements TreeViewModel {

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified value.
		 */
		public <T> NodeInfo<?> getNodeInfo(T value) {
			/*
			 * Create some data in a data provider. Use the parent value as a prefix for the
			 * next level.
			 */
			ListDataProvider<String> dataProvider = new ListDataProvider<String>();
			for (int i = 0; i < 10; i++) {
				dataProvider.getList().add(value + "." + String.valueOf(i));
			}

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<String>(dataProvider, new TextCell());
		}

		/**
		 * Check if the specified value represents a leaf node. Leaf nodes cannot be
		 * opened.
		 */
		public boolean isLeaf(Object value) {
			// The maximum length of a value is ten characters.
			return value.toString().length() > 10;
		}
	}

	@Override
	public void onModuleLoad() {

		TreeViewModel model = new CustomTreeModel();

		CellTree cellTree = new CellTree(model, "Gruppe 1");

		header = new Header();

		shoppinglistShowForm = new ShoppinglistShowForm();
		groupShowForm = new GroupShowForm();
		trailer = new Trailer();

		
		Button b1 = new Button("Editor");
		Button b2 = new Button("Report");
		Button b3 = new Button("Abmelden");
		
		b1.setStyleName("HeaderButton");
		b2.setStyleName("HeaderButton");
		b3.setStyleName("HeaderButton");

		RootPanel.get("Editor").add(b1);
		RootPanel.get("Report").add(b2);
		RootPanel.get("Logout").add(b3);

		RootPanel.get("aside").add(cellTree);
		RootPanel.get("main").add(groupShowForm);
		RootPanel.get("footer").add(trailer);

	}

}