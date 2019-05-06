package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.dummydata.BusinessObjectDD;
import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;
import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;

/**
 * Diese Implementierung des TreeViewModels sorgt f�r die Verwaltung des Gruppen- 
 * und Shoppinglistenbaums.
 * 
 * @author ElinaEisele, JonasWagenknecht
 * 
 */

public class GroupShoppinglistTreeViewModel implements TreeViewModel{
	
	private GroupShowForm groupForm;
	private ShoppinglistShowForm shoppinglistForm;
	
	private GroupDD selectedGroup = null;
	private ShoppinglistDD selectedShoppinglist = null;
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	private ListDataProvider<GroupDD> groupDataProvider = null;
	
	/*
	 * In dieser Map werden die ListDataProviders fuer die Shoppinglisten
	 * der im Gruppen- und Shoppinglistbaum expandierten Gruppenknoten gemerkt.
	 * In einer Gruppe kann es mehrere Shoppinglisten geben.
	 */
	private Map<GroupDD, ListDataProvider<ShoppinglistDD>> shoppinglistDataProviders = null;
	
	
	/**
	 * Nested Class, welche  BusinessObjects auf eindeutige Zahlenobjekte abbildet, 
	 * die als Schl�ssel f�r Baumknoten dienen.
	 *
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObjectDD>{

		@Override
		public Integer getKey(BusinessObjectDD bo) {
			if (bo == null) {
				return null;
			} else {
				return bo.getId();
			}
		}
		
	};
	
	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObjectDD> selectionModel = null;
	
	/**
	 * Nested Class f�r die Reaktion auf Selektionsereignisse. Als Folge
	 * einer Baumknotenauswahl wird je nach Typ des Business-Objects
	 * die "selectedGroup" bzw. die "selectedShoppinglist" gesetzt.
	 *
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObjectDD selection = selectionModel.getSelectedObject();
			if (selection instanceof GroupDD) {
				setSelectedGroup((GroupDD) selection);
			} else if (selection instanceof ShoppinglistDD){
				setSelectedShoppinglsit((ShoppinglistDD) selection);
			}
		}
		
	}
	
	public GroupShoppinglistTreeViewModel() {
		shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObjectDD>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		shoppinglistDataProviders = new HashMap<GroupDD, ListDataProvider<ShoppinglistDD>>();		
	}
	
	void setGroupForm(GroupShowForm gf) {
		groupForm = gf;
	}
	
	void setShoppinglistForm(ShoppinglistShowForm sf) {
		shoppinglistForm = sf;
	}
	
	GroupDD getSelectedGroup() {
		return selectedGroup;
	}
	
	void setSelectedGroup(GroupDD g) {
		selectedGroup = g;
		groupForm.setSelected(g);
//		selectedShoppinglist = null;
//		shoppinglistForm.setSelected(null);
	}
	
	ShoppinglistDD getSelectedShoppinglist() {
		return selectedShoppinglist;
	}
	

	void setSelectedShoppinglsit(ShoppinglistDD s) {
		selectedShoppinglist = s;
		shoppinglistForm.setSelected(s);
	}
	
	void addGroup(GroupDD group) {
		groupDataProvider.getList().add(group);
		selectionModel.setSelected(group,  true);
	}
	
	/**
	 * Diese Methode durchlaeuft eine Liste mit allen Gruppen. Hat eine Gruppe 
	 * in der Liste die gleiche Id wie die upzudatende Gruppe, wird die Gruppe
	 * an dieser Stelle neu gesetzt.
	 */
	void updateGroup(GroupDD group) {
		List<GroupDD> groupList = groupDataProvider.getList(); // oder ArrayList?
		int i = 0;
		for (GroupDD g : groupList) {
			if (g.getId() == group.getId()) {
				groupList.set(i, group);
				break;
			} else {
				i++;
			}
		}
		groupDataProvider.refresh();
	}
	
	void removeGroup(GroupDD group) {
		groupDataProvider.getList().remove(group);
		shoppinglistDataProviders.remove(group);
	}
	
	void addShoppinglistOfGroup(ShoppinglistDD shoppinglist, GroupDD group) {
		if (!shoppinglistDataProviders.containsKey(group)) {
			return;
		}
		ListDataProvider<ShoppinglistDD> shoppinglistsProvider = shoppinglistDataProviders.get(group);
		
		if (!shoppinglistsProvider.getList().contains(shoppinglist)) {
			shoppinglistsProvider.getList().add(shoppinglist);
		}
		selectionModel.setSelected(shoppinglist, true);
	}
	
	void removeShoppinglistOfGroup(ShoppinglistDD shoppinglist, GroupDD group) {
		if (!shoppinglistDataProviders.containsKey(group)) {
			return;
		}
		shoppinglistDataProviders.get(group).getList().remove(shoppinglist);
		selectionModel.setSelected(group, true);
	}
	
	/**
	 * Eine Shoppinglist in der Baumstruktur soll ersetzt werden durch eine
	 * Shoppinglist mit der selben Id, wenn sich die Eigenschaften einer 
	 * Shoppinglist ge�ndert haben und in der Baumstruktur noch ein veraltetes
	 * Shoppinglistobjekt vorhanden ist.
	 */
	void updateShoppinglist(ShoppinglistDD s) {
		shoppinglistAdministration.getGroupById(s.getId(),
				new UpdateShoppinglistCallback(s));
	}
	
	private class UpdateShoppinglistCallback implements AsyncCallback<GroupDD>{
		
		ShoppinglistDD shoppinglist = null;
		
		UpdateShoppinglistCallback(ShoppinglistDD s){
			shoppinglist = s;
		}

		@Override
		public void onFailure(Throwable t) {			
		}

		@Override
		public void onSuccess(GroupDD group) {	
			List<ShoppinglistDD> shoppinglistList = shoppinglistDataProviders.get(group)
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
			groupDataProvider = new ListDataProvider<GroupDD>();
			shoppinglistAdministration.getAllGroups(new AsyncCallback<ArrayList<GroupDD>>() {

				@Override
				public void onFailure(Throwable t) {					
				}

				@Override
				public void onSuccess(ArrayList<GroupDD> groups) {
					for (GroupDD g : groups) {
						groupDataProvider.getList().add(g);
					}
				}
				
			});
			
			return new DefaultNodeInfo<GroupDD>(groupDataProvider, new GroupCell(), selectionModel, null);	
		}
		
		if (value instanceof GroupDD) {
			// Erzeugen eines ListDataProviders f�r Shoppinglist-Daten
			final ListDataProvider<ShoppinglistDD> shoppinglistsProvider = new ListDataProvider<ShoppinglistDD>();
			shoppinglistDataProviders.put((GroupDD) value, shoppinglistsProvider);
			
			shoppinglistAdministration.getShoppinglistsOf((GroupDD) value, new AsyncCallback<ArrayList<ShoppinglistDD>>(){

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(ArrayList<ShoppinglistDD> shoppinglists) {
					for (ShoppinglistDD s : shoppinglists) {
						shoppinglistsProvider.getList().add(s);
					}
				}
			});
			// Zurueckgeben der Node Info, die die Daten in einer Zelle gruppiert.
			return new DefaultNodeInfo<ShoppinglistDD>(shoppinglistsProvider, new ShoppinglistCell(), selectionModel, null);
			}
		return null;
		}
	
	/**
	 * Es wird geprueft, ob der Wert einen Blattknoten repraesentiert. Ein
	 * Blattknoten kann nicht geoeffnet werden.
	 */
	@Override
	public boolean isLeaf(Object value) {
		return (value instanceof ShoppinglistDD);
	}
	

}
