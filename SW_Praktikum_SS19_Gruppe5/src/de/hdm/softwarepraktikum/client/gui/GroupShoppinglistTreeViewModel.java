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
import de.hdm.softwarepraktikum.shared.bo.BusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

import de.hdm.softwarepraktikum.*;

/**
 * Diese Implementierung des TreeViewModels sorgt f�r die Verwaltung des Gruppen- 
 * und Shoppinglistenbaums.
 * 
 * @author ElinaEisele, JonasWagenknecht
 * 
 */

public class GroupShoppinglistTreeViewModel implements TreeViewModel{

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return false;
	}
	
//	private GroupShowForm groupForm;
//	private ShoppinglistShowForm shoppinglistForm;
//	
//	private Group selectedGroup = null;
//	private Shoppinglist selectedShoppinglist = null;
//	
//	private ShoppinglistAdministrationAsync shoppinglistAdministration = null;
//	private ListDataProvider<Group> groupDataProvider = null;
//	
//	/*
//	 * In dieser Map werden die ListDataProviders fuer die Shoppinglisten
//	 * der im Gruppen- und Shoppinglistbaum expandierten Gruppenknoten gemerkt.
//	 * In einer Gruppe kann es mehrere Shoppinglisten geben.
//	 */
//	private Map<Group, ListDataProvider<Shoppinglist>> shoppinglistDataProviders = null;
//	
//	
//	/**
//	 * Nested Class, welche  BusinessObjects auf eindeutige Zahlenobjekte abbildet, 
//	 * die als Schl�ssel f�r Baumknoten dienen.
//	 *
//	 */
//	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject>{
//
//		@Override
//		public Integer getKey(BusinessObject bo) {
//			if (bo == null) {
//				return null;
//			} else {
//				return bo.getId();
//			}
//		}
//		
//	};
//	
//	private BusinessObjectKeyProvider boKeyProvider = null;
//	private SingleSelectionModel<BusinessObject> selectionModel = null;
//	
//	/**
//	 * Nested Class f�r die Reaktion auf Selektionsereignisse. Als Folge
//	 * einer Baumknotenauswahl wird je nach Typ des Business-Objects
//	 * die "selectedGroup" bzw. die "selectedShoppinglist" gesetzt.
//	 *
//	 */
//	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler{
//
//		@Override
//		public void onSelectionChange(SelectionChangeEvent event) {
//			BusinessObject selection = selectionModel.getSelectedObject();
//			if (selection instanceof Group) {
//				setSelectedGroup((Group) selection);
//			} else if (selection instanceof Shoppinglist){
//				setSelectedShoppinglsit((Shoppinglist) selection);
//			}
//		}
//		
//	}
//	
//	public GroupShoppinglistTreeViewModel() {
//		shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
//		boKeyProvider = new BusinessObjectKeyProvider();
//		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
//		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
//		shoppinglistDataProviders = new HashMap<Group, ListDataProvider<Shoppinglist>>();		
//	}
//	
//	void setGroupForm(GroupShowForm gf) {
//		groupForm = gf;
//	}
//	
//	void setShoppinglistForm(ShoppinglistShowForm sf) {
//		shoppinglistForm = sf;
//	}
//	
//	Group getSelectedGroup() {
//		return selectedGroup;
//	}
//	
//	void setSelectedGroup(Group selection) {
//		selectedGroup = selection;
//		groupForm.setSelected(selection);
////		selectedShoppinglist = null;
////		shoppinglistForm.setSelected(null);
//	}
//	
//	Shoppinglist getSelectedShoppinglist() {
//		return selectedShoppinglist;
//	}
//	
//
//	void setSelectedShoppinglsit(Shoppinglist s) {
//		selectedShoppinglist = s;
//		shoppinglistForm.setSelected(s);
//	}
//	
//	void addGroup(Group group) {
//		groupDataProvider.getList().add(group);
//		selectionModel.setSelected(group,  true);
//	}
//	
//	/**
//	 * Diese Methode durchlaeuft eine Liste mit allen Gruppen. Hat eine Gruppe 
//	 * in der Liste die gleiche Id wie die upzudatende Gruppe, wird die Gruppe
//	 * an dieser Stelle neu gesetzt.
//	 */
//	void updateGroup(Group group) {
//		List<Group> groupList = groupDataProvider.getList(); // oder ArrayList?
//		int i = 0;
//		for (Group g : groupList) {
//			if (g.getId() == group.getId()) {
//				groupList.set(i, group);
//				break;
//			} else {
//				i++;
//			}
//		}
//		groupDataProvider.refresh();
//	}
//	
//	void removeGroup(Group group) {
//		groupDataProvider.getList().remove(group);
//		shoppinglistDataProviders.remove(group);
//	}
//	
//	void addShoppinglistOfGroup(Shoppinglist shoppinglist, Group group) {
//		if (!shoppinglistDataProviders.containsKey(group)) {
//			return;
//		}
//		ListDataProvider<Shoppinglist> shoppinglistsProvider = shoppinglistDataProviders.get(group);
//		
//		if (!shoppinglistsProvider.getList().contains(shoppinglist)) {
//			shoppinglistsProvider.getList().add(shoppinglist);
//		}
//		selectionModel.setSelected(shoppinglist, true);
//	}
//	
//	void removeShoppinglistOfGroup(Shoppinglist shoppinglist, Group group) {
//		if (!shoppinglistDataProviders.containsKey(group)) {
//			return;
//		}
//		shoppinglistDataProviders.get(group).getList().remove(shoppinglist);
//		selectionModel.setSelected(group, true);
//	}
//	
//	/**
//	 * Eine Shoppinglist in der Baumstruktur soll ersetzt werden durch eine
//	 * Shoppinglist mit der selben Id, wenn sich die Eigenschaften einer 
//	 * Shoppinglist ge�ndert haben und in der Baumstruktur noch ein veraltetes
//	 * Shoppinglistobjekt vorhanden ist.
//	 */
//	void updateShoppinglist(Shoppinglist s) {
//		shoppinglistAdministration.getGroupById(s.getId(),
//				new UpdateShoppinglistCallback(s));
//	}
//	
//	private class UpdateShoppinglistCallback implements AsyncCallback<Group>{
//		
//		Shoppinglist shoppinglist = null;
//		
//		UpdateShoppinglistCallback(Shoppinglist s){
//			shoppinglist = s;
//		}
//
//		@Override
//		public void onFailure(Throwable t) {			
//		}
//
//		@Override
//		public void onSuccess(Group group) {	
//			List<Shoppinglist> shoppinglistList = shoppinglistDataProviders.get(group)
//					.getList();
//			for (int i=0; i<shoppinglistList.size(); i++) {
//				if (shoppinglist.getId() == shoppinglistList.get(i).getId()) {
//					shoppinglistList.set(i, shoppinglist);
//					break;
//				}
//			}
//		}
//		
//		
//	}
//	
//	
//	/**
//	 * Zurueckgeben der NodeInfo, die die Kinder des jeweiligen Werts enthaelt.
//	 */
//	@Override
//	public <T> NodeInfo<?> getNodeInfo(T value) {
//		if (value.equals("Root")) {
//			groupDataProvider = new ListDataProvider<Group>();
//			shoppinglistAdministration.getAllGroups(new AsyncCallback<ArrayList<Group>>() {
//
//				@Override
//				public void onFailure(Throwable t) {					
//				}
//
//				@Override
//				public void onSuccess(ArrayList<Group> groups) {
//					for (Group g : groups) {
//						groupDataProvider.getList().add(g);
//					}
//				}
//				
//			});
//			
//			return new DefaultNodeInfo<Group>(groupDataProvider, new GroupCell(), selectionModel, null);	
//		}
//		
//		if (value instanceof Group) {
//			// Erzeugen eines ListDataProviders f�r Shoppinglist-Daten
//			final ListDataProvider<Shoppinglist> shoppinglistsProvider = new ListDataProvider<Shoppinglist>();
//			shoppinglistDataProviders.put((Group) value, shoppinglistsProvider);
//			
//			shoppinglistAdministration.getShoppinglistsOf((Group) value, new AsyncCallback<ArrayList<Shoppinglist>>(){
//
//				@Override
//				public void onFailure(Throwable caught) {
//				}
//
//				@Override
//				public void onSuccess(ArrayList<Shoppinglist> shoppinglists) {
//					for (Shoppinglist s : shoppinglists) {
//						shoppinglistsProvider.getList().add(s);
//					}
//				}
//			});
//			// Zurueckgeben der Node Info, die die Daten in einer Zelle gruppiert.
//			return new DefaultNodeInfo<Shoppinglist>(shoppinglistsProvider, new ShoppinglistCell(), selectionModel, null);
//			}
//		return null;
//		}
//	
//	/**
//	 * Es wird geprueft, ob der Wert einen Blattknoten repraesentiert. Ein
//	 * Blattknoten kann nicht geoeffnet werden.
//	 */
//	@Override
//	public boolean isLeaf(Object value) {
//		return (value instanceof Shoppinglist);
//	}
	

}
