package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.TreeViewModel.DefaultNodeInfo;
import com.google.gwt.view.client.TreeViewModel.NodeInfo;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse, um die Navigation in Gruppen und Einkaufslisten darzustellen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class NavigatorPanel implements TreeViewModel {

	GroupShoppinglistTreeViewModel gstvm = null;

	VerticalPanel navigatorPlatzhalter = null;
	Label navigatorLabel = null;

	/**
	 * An dieser Stelle erfolgt die Implementierung des CellTrees.
	 */

	/**
	 * The model that defines the nodes in the tree.
	 */

	private final List<Group> blatter;
	ArrayList<Shoppinglist> shoppinglists = new ArrayList<>();
	/**
	 * This selection model is shared across all leaf nodes. A selection model can
	 * also be shared across all nodes in the tree, or each set of child nodes can
	 * have its own instance. This gives you flexibility to determine how nodes are
	 * selected.
	 */
	private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

	public NavigatorPanel() {
		// Create a database of information.
		blatter = new ArrayList<Group>();

		{
			Group gruppe1 = new Group("Tims einkaeufe");
			gruppe1.setShoppinglists(shoppinglists);
			Shoppinglist s1 = new Shoppinglist("Uff");
			shoppinglists.add(s1);
		}

	}

	/**
	 * Get the {@link NodeInfo} that provides the children of the specified value.
	 */
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// LEVEL 0.
			// We passed null as the root value. Return the composers.

			// Create a data provider that contains the list of groups.
			ListDataProvider<Group> dataProvider = new ListDataProvider<Group>(blatter);

			// Create a cell to display a group.
			Cell<Group> cell = new AbstractCell<Group>() {
				@Override
				public void render(Context context, Group value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value.getName());
					}
				}
			};

			// Return a node info that pairs the data provider and the cell.
			return new DefaultNodeInfo<Group>(dataProvider, cell);

		} else if (value instanceof Group) {
			// LEVEL 2 - LEAF.
			// We want the children of the group. return the shoppinglists.
			ListDataProvider<String> dataProvider = new ListDataProvider<String>();

			// Use the shared selection model.
			return new DefaultNodeInfo<String>(dataProvider, new TextCell(), selectionModel, null);
		}

		return null;
	}

	/**
	 * Check if the specified value represents a leaf node. Leaf nodes cannot be
	 * opened.
	 */
	public boolean isLeaf(Object value) {
		// The leaf nodes are shoppinglists.
		if (value instanceof String) {
			return true;
		}
		return false;
	}

}
