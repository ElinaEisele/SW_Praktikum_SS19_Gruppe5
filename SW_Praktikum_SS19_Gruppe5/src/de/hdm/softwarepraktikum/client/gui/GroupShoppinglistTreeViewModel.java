package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.TreeViewModel;
import com.sun.javafx.collections.MappingChange.Map;

import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.dummydata.BusinessObject;
import de.hdm.softwarepraktikum.shared.dummydata.Group;
import de.hdm.softwarepraktikum.shared.dummydata.Shoppinglist;

/**
 * Diese Implementierung des TreeViewModels sorgt f�r die Verwaltung des Gruppen- 
 * und Shoppinglistenbaums.
 * 
 * @author ElinaEisele
 * 
 */

public class GroupShoppinglistTreeViewModel implements TreeViewModel{
	
	private GroupForm groupForm;
	private ShoppinglistForm shoppinglistForm;
	
	private Group selectedGroup = null;
	private Shoppinglist selectedShoppinglst = null;
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	private ListDataProvider<Group> groupDataProvider = null;
	
	/*
	 * In dieser Map werden die ListDataProviders fuer die Shoppinglisten
	 * der im Gruppen- und Shoppinglistbaum expandierten Gruppenknoten gemerkt.
	 */
	private Map<Group, ListDataProvider<Shoppinglist>> shoppinglistDataProviders = null;
	
	
	/**
	 * Diese Klasse bildet BusinessObjects auf eindeutige Zahlenobjekte ab, die als 
	 * Schl�ssel f�r Baumknoten dienen.
	 * 
	 * @author ElinaEisele
	 *
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject>{

		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			} else {
				return bo.getId();
			}
		}
		
	};
	
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
	

}
