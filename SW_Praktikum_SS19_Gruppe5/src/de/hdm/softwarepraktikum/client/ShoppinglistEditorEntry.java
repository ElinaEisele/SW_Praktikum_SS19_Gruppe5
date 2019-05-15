package de.hdm.softwarepraktikum.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.gui.NavigatorPanel;
import de.hdm.softwarepraktikum.client.gui.RegistrationForm;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
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

	

	@Override
	public void onModuleLoad() {

		// Create a model for the tree.
				TreeViewModel model = new NavigatorPanel();

				/*
				 * Create the tree using the model. We use <code>null</code> as the default
				 * value of the root node. The default value will be passed to
				 * CustomTreeModel#getNodeInfo();
				 */
				CellTree tree = new CellTree(model, null);
				tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

				// Open the first playlist by default.
				TreeNode rootNode = tree.getRootTreeNode();
//				TreeNode firstGruppe = rootNode.setChildOpen(0, true);
//				firstGruppe.setChildOpen(0, false);

				// Add the tree to the root layout panel.
				RootPanel.get("aside").add(tree);
		
		
		
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

		
		RootPanel.get("main").add(shoppinglistShowForm);
		RootPanel.get("trailer").add(trailer);

	}

}