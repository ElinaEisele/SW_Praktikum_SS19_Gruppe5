package de.hdm.softwarepraktikum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.gui.GroupShoppinglistTreeViewModel;
import de.hdm.softwarepraktikum.client.gui.GroupShowForm;
import de.hdm.softwarepraktikum.client.gui.Header;
import de.hdm.softwarepraktikum.client.gui.ListitemShowForm;
import de.hdm.softwarepraktikum.client.gui.NavigatorPanel;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistShowForm;
import de.hdm.softwarepraktikum.client.gui.Trailer;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;

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
	private GroupShoppinglistTreeViewModel shoppinglistNavigator = null;
	
	private ShoppinglistShowForm shoppinglistShowForm = null;
	private GroupShowForm groupShowForm = null;
	private ListitemShowForm listitemShowForm = null;
	
	private NavigatorPanel navigatorPanel = null;
	
	private Trailer trailer = null;
	
	@Override
	public void onModuleLoad() {
		

		// Create a model for the tree.
//				GroupShoppinglistTreeViewModel model = new GroupShoppinglistTreeViewModel();

				/*
				 * Create the tree using the model. We use <code>null</code> as the default
				 * value of the root node. The default value will be passed to
				 * CustomTreeModel#getNodeInfo();
				 */
//				CellTree tree = new CellTree(model, null);
//				tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

				// Open the first playlist by default.
				//TreeNode rootNode = tree.getRootTreeNode();
//				TreeNode firstGruppe = rootNode.setChildOpen(0, true);
//				firstGruppe.setChildOpen(0, false);

				// Add the tree to the root layout panel.
				
		
		
		
		header = new Header();
		navigatorPanel = new NavigatorPanel();
		shoppinglistShowForm = new ShoppinglistShowForm();
		groupShowForm = new GroupShowForm();
		listitemShowForm = new ListitemShowForm();
		trailer = new Trailer();

		RootPanel.get("header").add(header);	
		RootPanel.get("aside").add(navigatorPanel);
		RootPanel.get("main").add(groupShowForm);
		RootPanel.get("trailer").add(trailer);

	}

}