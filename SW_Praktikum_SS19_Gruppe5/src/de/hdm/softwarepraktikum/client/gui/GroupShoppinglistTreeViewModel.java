package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Klasse, um die Navigation in Gruppen und Einkaufslisten darzustellen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class GroupShoppinglistTreeViewModel implements TreeViewModel {
	
	private User u = CurrentUser.getUser();
	
	private ArrayList<Group> userGroup = new ArrayList<Group>();

	public ArrayList<Group> getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(ArrayList<Group> userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * An dieser Stelle erfolgt die Implementierung des CellTrees.
	 */

	public List<GroupObject> getGroupObjects() {
		return groupObjects;
	}

	/**
	 * The model that defines the nodes in the tree.
	 */

	private final List<GroupObject> groupObjects;

	/**
	 * This selection model is shared across all leaf nodes. A selection model can
	 * also be shared across all nodes in the tree, or each set of child nodes can
	 * have its own instance. This gives you flexibility to determine how nodes are
	 * selected.
	 */
	private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

	public GroupShoppinglistTreeViewModel() {
		// Create a database of information.
		groupObjects = new ArrayList<GroupObject>();

		{
			GroupObject g1 = new GroupObject("Erste Gruppe");
			g1.addShoppinglistObject("Erste Einkaufsliste");
			groupObjects.add(g1);

		}

	}

	/**
	 * Get the {@link NodeInfo} that provides the children of the specified value.
	 */
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// LEVEL 0.
			// We passed null as the root value. Return the composers.

			// Create a data provider that contains the list of ShoppinglistObjects.
			ListDataProvider<GroupObject> dataProvider = new ListDataProvider<GroupObject>(groupObjects);

			// Create a cell to display a ShoppinglistObject.
			Cell<GroupObject> cell = new AbstractCell<GroupObject>() {
				@Override
				public void render(Context context, GroupObject value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value.getname());
					}
				}
			};

			// Return a node info that pairs the data provider and the cell.
			return new DefaultNodeInfo<GroupObject>(dataProvider, cell);

		} else if (value instanceof GroupObject) {
			// LEVEL 1 - LEAF.
			// We want the children of the ShoppinglistObject. return the shoppinglists.
			ListDataProvider<String> dataProvider = new ListDataProvider<String>(
					((GroupObject) value).getShoppinglistObjects());

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
