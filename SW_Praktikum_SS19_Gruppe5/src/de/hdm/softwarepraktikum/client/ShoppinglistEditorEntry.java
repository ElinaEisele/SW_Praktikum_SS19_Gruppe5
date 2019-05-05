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
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.dummydata.Group;
import de.hdm.softwarepraktikum.shared.dummydata.Shoppinglist;
import de.hdm.softwarepraktikum.shared.dummydata.User;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistShowForm;
import de.hdm.softwarepraktikum.client.gui.Trailer;
import de.hdm.softwarepraktikum.client.gui.Editor;
import de.hdm.softwarepraktikum.client.gui.GroupShoppinglistTreeViewModel;
import de.hdm.softwarepraktikum.client.gui.GroupShowForm;
import de.hdm.softwarepraktikum.client.gui.Header;
import de.hdm.softwarepraktikum.client.gui.ShoppinglistCellTable;

/**
 * Entry-Point Klasse des Projekts <b>SW_Praktikum_SS19_Gruppe5</b> für den Editor.
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
	
	private static class UserNavigator{
		private final List<Group> groups = new ArrayList<Group>();
		
		public void addGroup(Group g) {
			groups.add(g);
		}
		
		public List<Group> getGroups(){
			return groups;
		}
		
	}
	
	private static class GroupNavigator{
		private final List<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();
		
		public Shoppinglist addShoppinglist(Shoppinglist s){
			shoppinglists.add(s);
			return s;
		}
		
		public List<Shoppinglist> getShoppinglists(){
			return shoppinglists;
		}
		
	}
	
	/**
	   * The model that defines the nodes in the tree.
	   */
	  private static class CustomTreeModel implements TreeViewModel {
		  
		  private List<Group> groups1 = null;
		  
		  private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();		  
		  
		  public CustomTreeModel() {
			  groups1 = new ArrayList<Group>();
			  
			  Group g1 = new Group("Gruppe 1", 0, new Date());
			  Group g2 = new Group("Gruppe 2", 3, new Date());
			  groups1.add(g1);
			  groups1.add(g2);
			  
			  Shoppinglist s1 = new Shoppinglist("Einkaufsliste 1", 1, new Date());
			  Shoppinglist s2 = new Shoppinglist("Einkaufsliste 2", 2, new Date());
			  Shoppinglist s3 = new Shoppinglist("Einkaufsliste 1", 4, new Date());
			  
			  g1.addShoppinglist(s1);
			  g1.addShoppinglist(s2);
			  g2.addShoppinglist(s3);
			  
		  }

	    /**
	     * Get the {@link NodeInfo} that provides the children of the specified
	     * value.
	     */
	    public <T> NodeInfo<?> getNodeInfo(T value) {
	    	if (value == null) {
	    		/*
	  	       * Create some data in a data provider. Use the parent value as a prefix
	  	       * for the next level.
	  	       */
	  	      ListDataProvider<UserNavigator> dataProvider = new ListDataProvider<ShoppinglistEditorEntry.UserNavigator>(groups1);

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
	
	
	/**
	 * Da diese Klasse die Implementierung des Interrface <code>EntryPoint</code>
	 * sicherstellt, wird die Methode <code>public void onModuleLoad()</code> benoetigt.
	 * Diese ist wie die <code>main()</code>-Methode in Java-Applikationen
	 * fuer GWT zu verstehen.
	 */
	@Override
	public void onModuleLoad() {
		
		
		
	    TreeViewModel model = new CustomTreeModel();
	    
	    CellTree cellTree = new CellTree(model, "Gruppe ");
				
		header = new Header();
		shoppinglistShowForm = new ShoppinglistShowForm();
		groupShowForm = new GroupShowForm();
		trailer = new Trailer();
		
						
		RootPanel.get("Header").add(header);
		RootPanel.get("Navigator").add(cellTree);
		RootPanel.get("Details").add(shoppinglistShowForm);
		RootPanel.get("Trailer").add(trailer);
	
	
		
	}
	

}
