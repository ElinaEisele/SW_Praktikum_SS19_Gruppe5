package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.TreeViewModel.DefaultNodeInfo;
import com.google.gwt.view.client.TreeViewModel.NodeInfo;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.BusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Diese Implementierung des TreeViewModels sorgt für die Verwaltung des Gruppen-
 * und Einkaufslistenbaums.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */

public class GroupShoppinglistTreeViewModel implements TreeViewModel {
	
	private User u = CurrentUser.getUser();
	private GroupShowForm groupShowForm;
	private ShoppinglistShowForm shoppinglistShowForm;
	
	private ArrayList<Group> userGroups = new ArrayList<Group>();	
	private Group selectedGroup = null;
	private Shoppinglist selectedShoppinglist = null;
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	
	private ListDataProvider<Group> groupDataProvider = null;
	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;
	
	/*
	 * In dieser Map werden die ListDataProviders fuer die Einkaufslisten
	 * der im Gruppen- und Einkaufslistenbaum expandierten Gruppenknoten gemerkt.
	 * In einer Gruppe kann es mehrere Shoppinglisten geben.
	 */
	private Map<Group, ListDataProvider<Shoppinglist>> shoppinglistDataProviders = null;
	
	
	/**
	 * Innere Klasse, welche  BusinessObjects auf eindeutige Zahlenobjekte abbildet, 
	 * die als Schluessel fuer Baumknoten dienen.
	 *
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject>{

		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			} else {
				return new Integer(bo.getId()); // eindeutige IDs?
			}
		}
		
	};
	
	
	/**
	 * Innere Klasse fuer die Reaktion auf Selektionsereignisse. Als Folge
	 * einer Baumknotenauswahl wird je nach Typ des Business-Objects
	 * die "selectedGroup" bzw. die "selectedShoppinglist" gesetzt.
	 *
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Group) {
				setSelectedGroup((Group) selection);
			} else if (selection instanceof Shoppinglist){
				setSelectedShoppinglsit((Shoppinglist) selection);
			}
		}
		
	}
	
	/*
	 * Im Konstruktor werden die für die Gruppen- und Einkaufslistenbaum wichtigen lokalen
	 * Variaben initialisiert.
	 */
	public GroupShoppinglistTreeViewModel() {
		shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		shoppinglistDataProviders = new HashMap<Group, ListDataProvider<Shoppinglist>>();		
	}
	
	public ArrayList<Group> getUserGroups(){
		return userGroups;
	}
	
	public void setUserGroups(ArrayList<Group> userGroups) {
		this.userGroups = userGroups;
	}
	
	public void setGroupShowForm(GroupShowForm gsf) {
		groupShowForm = gsf;
	}
	
	void setShoppinglistForm(ShoppinglistShowForm ssf) {
		shoppinglistShowForm = ssf;
	}
	
	public SingleSelectionModel<BusinessObject> getSelectionModel(){
		return selectionModel;
	}
	
	Group getSelectedGroup() {
		return selectedGroup;
	}
	
	void setSelectedGroup(Group selection) {
		RootPanel.get("main").clear();
		selectedGroup = selection;
		groupShowForm.setSelected(selection);
		RootPanel.get("main").add(groupShowForm);
		selectedShoppinglist = null; // zuruecksetzen des Shoppinglist Objekts
		shoppinglistShowForm.setSelected(null);
	}
	
	Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}
	

	void setSelectedShoppinglsit(Shoppinglist s) {
		selectedShoppinglist = s;
		shoppinglistShowForm.setSelected(s);
		
		if (s != null) {
			shoppinglistAdministration.getGroupById(s.getGroupId(), 
					new AsyncCallback<Group>() {

						@Override
						public void onFailure(Throwable caught) {							
						}

						@Override
						public void onSuccess(Group group) {
							selectedGroup = group;
							groupShowForm.setSelected(group);
						}
				
			});
		}
	}
	
	public Map<Group, ListDataProvider<Shoppinglist>> getShoppinglistDataProviders(){
		return shoppinglistDataProviders;
	}
	/**
	 * Methode zum Aktualisieren des gesamten Baums.
	 */
	public void refresh() {
		groupDataProvider.getList().clear();
		this.shoppinglistAdministration.getAllGroups(u, new AsyncCallback<ArrayList<Group>>() {

			@Override
			public void onFailure(Throwable caught) {
//				Notification.show(caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<Group> result) {
				for (Group g : result) {
					groupDataProvider.getList().add(g);
				}
			}
			
		});
	}
	
	/*
	 * Wenn eine Gruppe neu erzeugt wurde, wird sie selektiert.
	 */
	void addGroup(Group group) {
		groupDataProvider.getList().add(group);
		selectionModel.setSelected(group,  true);
	}
	
	/**
	 * Diese Methode durchlaeuft eine Liste mit allen Gruppen. Hat eine Gruppe 
	 * in der Liste die gleiche Id wie die upzudatende Gruppe, wird die Gruppe
	 * an dieser Stelle neu gesetzt.
	 */
	void updateGroup(Group group) {
		List<Group> groupList = groupDataProvider.getList();
		int i = 0;
		for (Group g : groupList) {
			if (g.getId() == group.getId()) {
				groupList.set(i, group);
				break;
			} else {
				i++;
			}
		}
		groupDataProvider.refresh();
	}
	
	void removeGroup(Group g) {
		this.userGroups.remove(g);
		groupDataProvider.getList().remove(g);
		shoppinglistDataProviders.remove(g);
	}
	
	void addShoppinglistToGroup(Shoppinglist s, Group g) {
		if (!shoppinglistDataProviders.containsKey(g)) {
			return;
		}
		
		// Methode getGroupNameMapBy hier implementieren
		
		ListDataProvider<Shoppinglist> shoppinglistsProvider = shoppinglistDataProviders.get(g);
		
		if (!shoppinglistsProvider.getList().contains(s)) {
			shoppinglistsProvider.getList().add(s);
		}
		selectionModel.setSelected(s, true);
	}
	
	void removeShoppinglistOfGroup(Shoppinglist shoppinglist, Group group) {
		if (!shoppinglistDataProviders.containsKey(group)) {
			return;
		}
		
		// hier auch getGroupNameMapBy
		
		shoppinglistDataProviders.get(group).getList().remove(shoppinglist);
		selectionModel.setSelected(group, true);
	}
	
	/**
	 * Eine Shoppinglist in der Baumstruktur soll ersetzt werden durch eine
	 * Shoppinglist mit der selben Id, wenn sich die Eigenschaften einer 
	 * Shoppinglist geaendert haben und in der Baumstruktur noch ein veraltetes
	 * Shoppinglistobjekt vorhanden ist.
	 */
	void updateShoppinglist(Shoppinglist s) {
		shoppinglistAdministration.getGroupById(s.getId(),
				new UpdateShoppinglistCallback(s));
	}
	
	public void removeDeletedShoppinglistFromGroup(Shoppinglist s) {
		// hier fehlt Implementierung
	}
	
	public void updateShoppinglistToGroup(Shoppinglist s) {
		// hier Implementierung
	}
	
	// Search Implementierung fehlt noch!
	
	private class UpdateShoppinglistCallback implements AsyncCallback<Group>{
		
		Shoppinglist shoppinglist = null;
		
		UpdateShoppinglistCallback(Shoppinglist s){
			shoppinglist = s;
		}

		@Override
		public void onFailure(Throwable t) {			
		}

		@Override
		public void onSuccess(Group group) {	
			List<Shoppinglist> shoppinglistList = shoppinglistDataProviders.get(group)
					.getList();
			for (int i=0; i<shoppinglistList.size(); i++) {
				if (shoppinglist.getId() == shoppinglistList.get(i).getId()) {
					shoppinglistList.set(i, shoppinglist);
					break;
				}
			}
		}
		
		
	}
	
	
	/**
	 * Zurueckgeben der NodeInfo, die die Kinder des jeweiligen Werts enthaelt.
	 */
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value.equals("Root")) {
			groupDataProvider = new ListDataProvider<Group>();
			shoppinglistAdministration.getAllGroups(u, new AsyncCallback<ArrayList<Group>>() {

				@Override
				public void onFailure(Throwable t) {					
				}

				@Override
				public void onSuccess(ArrayList<Group> groups) {
					for (Group g : groups) {
						groupDataProvider.getList().add(g);
					}
				}
				
			});
			
			return new DefaultNodeInfo<Group>(groupDataProvider, new NavigatorGroupCell(), selectionModel, null);	
		}
		
		if (value instanceof Group) {
			// Erzeugen eines ListDataProviders fuer Shoppinglist-Daten
			final ListDataProvider<Shoppinglist> shoppinglistsProvider = new ListDataProvider<Shoppinglist>();
			shoppinglistDataProviders.put((Group) value, shoppinglistsProvider);
			
			shoppinglistAdministration.getShoppinglistsOf((Group) value, new AsyncCallback<ArrayList<Shoppinglist>>(){

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(ArrayList<Shoppinglist> shoppinglists) {
					for (Shoppinglist s : shoppinglists) {
						shoppinglistsProvider.getList().add(s);
					}
				}
			});
			// Zurueckgeben der Node Info, die die Daten in einer Zelle gruppiert.
			return new DefaultNodeInfo<Shoppinglist>(shoppinglistsProvider, new NavigatorShoppinglistCell(), selectionModel, null);
			}
		return null;
		}
	
	/**
	 * Es wird geprueft, ob der Wert einen Blattknoten repraesentiert. Ein
	 * Blattknoten kann nicht geoeffnet werden.
	 */
	@Override
	public boolean isLeaf(Object value) {
		return (value instanceof Shoppinglist);
	}
	
	
//	private User u = CurrentUser.getUser();
//	
//	private ArrayList<Group> userGroup = new ArrayList<Group>();
//
//	public ArrayList<Group> getUserGroup() {
//		return userGroup;
//	}
//
//	public void setUserGroup(ArrayList<Group> userGroup) {
//		this.userGroup = userGroup;
//	}
//
//	/**
//	 * An dieser Stelle erfolgt die Implementierung des CellTrees.
//	 */
//
//	public List<GroupObject> getGroupObjects() {
//		return groupObjects;
//	}
//
//	/**
//	 * The model that defines the nodes in the tree.
//	 */
//
//	private final List<GroupObject> groupObjects;
//
//	/**
//	 * This selection model is shared across all leaf nodes. A selection model can
//	 * also be shared across all nodes in the tree, or each set of child nodes can
//	 * have its own instance. This gives you flexibility to determine how nodes are
//	 * selected.
//	 */
//	private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
//
//	public GroupShoppinglistTreeViewModel() {
//		// Create a database of information.
//		groupObjects = new ArrayList<GroupObject>();
//
//		{
//			GroupObject g1 = new GroupObject("Erste Gruppe");
//			g1.addShoppinglistObject("Erste Einkaufsliste");
//			groupObjects.add(g1);
//
//		}
//
//	}
//
//	/**
//	 * Get the {@link NodeInfo} that provides the children of the specified value.
//	 */
//	public <T> NodeInfo<?> getNodeInfo(T value) {
//		if (value == null) {
//			// LEVEL 0.
//			// We passed null as the root value. Return the composers.
//
//			// Create a data provider that contains the list of ShoppinglistObjects.
//			ListDataProvider<GroupObject> dataProvider = new ListDataProvider<GroupObject>(groupObjects);
//
//			// Create a cell to display a ShoppinglistObject.
//			Cell<GroupObject> cell = new AbstractCell<GroupObject>() {
//				@Override
//				public void render(Context context, GroupObject value, SafeHtmlBuilder sb) {
//					if (value != null) {
//						sb.appendEscaped(value.getname());
//					}
//				}
//			};
//
//			// Return a node info that pairs the data provider and the cell.
//			return new DefaultNodeInfo<GroupObject>(dataProvider, cell);
//
//		} else if (value instanceof GroupObject) {
//			// LEVEL 1 - LEAF.
//			// We want the children of the ShoppinglistObject. return the shoppinglists.
//			ListDataProvider<String> dataProvider = new ListDataProvider<String>(
//					((GroupObject) value).getShoppinglistObjects());
//
//			// Use the shared selection model.
//			return new DefaultNodeInfo<String>(dataProvider, new TextCell(), selectionModel, null);
//			
//		}
//			
//			return null;
//	}
//
//	/**
//	 * Check if the specified value represents a leaf node. Leaf nodes cannot be
//	 * opened.
//	 */
//	public boolean isLeaf(Object value) {
//		// The leaf nodes are shoppinglists.
//		if (value instanceof String) {
//			return true;
//		}
//		return false;
//	}
	

}
